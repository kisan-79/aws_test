package com.aws.parallel.discoveria.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aws.parallel.discoveria.constant.ApplicationConstants;
import com.aws.parallel.discoveria.entity.EC2;
import com.aws.parallel.discoveria.entity.Job;
import com.aws.parallel.discoveria.entity.JobStatus;
import com.aws.parallel.discoveria.entity.S3Bucket;
import com.aws.parallel.discoveria.entity.S3Object;
import com.aws.parallel.discoveria.model.JobResponse;
import com.aws.parallel.discoveria.model.ObjectSearchRequest;
import com.aws.parallel.discoveria.repository.Ec2Repository;
import com.aws.parallel.discoveria.repository.JobRepository;
import com.aws.parallel.discoveria.repository.S3BucketRepository;
import com.aws.parallel.discoveria.repository.S3ObjectRepository;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

@Service

public class AwsService {

	private static Logger log = LoggerFactory.getLogger(AwsService.class);

	@Autowired
	private S3Client s3Client;

	@Autowired
	private Ec2Client ec2Client;

	@Autowired
	private Ec2Repository ec2Repository;

	@Autowired
	private S3ObjectRepository s3ObjectRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private S3BucketRepository s3BucketRepository;
	
	
	public JobResponse fetchAndSaveService(List<String> services) {
		log.info("Inside method - fetchAndSaveService");
		JobResponse response = new JobResponse();
		Job job = new Job();
		try {
			// Create new job with default status as in progress to fetch save in dB.
			job = jobRepository.save(new Job(JobStatus.IN_PROCESS, ApplicationConstants.Persist_EC2_S3));
			Job j1 = job;
			CompletableFuture.runAsync(() -> {
				for (String service : services) {
					if (service.equalsIgnoreCase(ApplicationConstants.EC2)) {
						CompletableFuture.runAsync(() -> fetchEc2Instances(j1));
					} else if (service.equalsIgnoreCase(ApplicationConstants.S3)) {
						CompletableFuture.runAsync(() -> fetchS3Buckets(j1));
					}

				}
			});
			response.setJobId(job.getJobId());

		} catch (Exception e) {
			log.error("Facing issue while saving Job - ", e.getMessage());
			job.setJobStatus(JobStatus.FAILED);
			jobRepository.save(job);
			response.setStatus("Error");
			response.setStatusDesc("Facing issue while saving Job");
			return response;
		}
		return response;
	}

	private void fetchEc2Instances(Job job) {
		List<EC2> dbInstances = new ArrayList<>();
		try {
			job.setJobStatus(JobStatus.SUCCESS);

			DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances();
			List<Instance> instances = describeInstancesResponse.reservations().stream()
					.flatMap(reservation -> reservation.instances().stream()).toList();

			for (Instance instance : instances) {
				EC2 ints = new EC2(instance.instanceId(), instance.state().nameAsString(), job, LocalDateTime.now());
				dbInstances.add(ints);
				log.info("InstanceId: " + instance.instanceId() + ", State: " + instance.state().name());
			}

			if (!dbInstances.isEmpty()) {
				ec2Repository.saveAll(dbInstances);
			}
			jobRepository.save(job);

		} catch (Exception e) {
			log.error("Error while fetching/persisting Ec2 instances - " + e.getMessage());
			job.setJobStatus(JobStatus.FAILED);
			jobRepository.save(job);
		}

	}

	private void fetchS3Buckets(Job job) {

		List<S3Bucket> s3Buckets = new ArrayList<>();

		try {
			List<Bucket> buckets = s3Client.listBuckets().buckets();

			for (Bucket bucket : buckets) {
				S3Bucket s3Bucket = new S3Bucket(bucket.name(), job, LocalDateTime.now());
				s3Buckets.add(s3Bucket);
			}
			if (!s3Buckets.isEmpty()) {
				s3BucketRepository.saveAll(s3Buckets);
			}

		} catch (Exception e) {
			log.error("Error while fetching/persisting S3 buckets - " + e.getMessage());
			job.setJobStatus(JobStatus.FAILED);
			jobRepository.save(job);
		}

	}

	public JobResponse getJobDetails(Integer jobId) {
		JobResponse response = new JobResponse();
		try {
			Optional<Job> job = jobRepository.findById(jobId);
			if (job.isEmpty()) {
				log.error("job with given is does not exist.");
				response.setJobId(jobId);
				response.setStatus(null);
				response.setStatusDesc("job with given is does not exist. Please Check and try again..!");
				return response;
			}
			response.setJobId(jobId);
			response.setStatus(job.get().getJobStatus().getValue());
			return response;
		} catch (Exception e) {
			log.error("Error while fetching job details - " + e.getMessage());
			response.setJobId(jobId);
			response.setStatus(null);
			response.setStatusDesc("We are facing issue while fetching job details. Please try after some time..!");
			return response;

		}
	}

	public List<String> getAllEc2Instances() {
		try {
			List<EC2> instances = ec2Repository.findAll();
			return instances.stream().map(EC2::getInstanceId).distinct().toList();
		} catch (Exception e) {
			log.error("Error while fetching instance details - " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public List<String> getAllS3Buckets() {
		try {
			List<S3Bucket> buckets = s3BucketRepository.findAll();
			return buckets.stream().map(S3Bucket::getBucketName).distinct().toList();
		} catch (Exception e) {
			log.error("Error while fetching bucket details - " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public JobResponse fetchObjects(String bucketName) {
		JobResponse response = new JobResponse();
		Job job = new Job();
		try {
			job = jobRepository.save(new Job(JobStatus.IN_PROCESS, ApplicationConstants.Persist_EC2_S3));
			response.setJobId(job.getJobId());
			Job j1 = job;
			CompletableFuture.runAsync(() -> fetchBucketObjects(j1, bucketName));

		} catch (Exception e) {
			log.error("Facing issue while saving Job - ", e.getMessage());
			job.setJobStatus(JobStatus.FAILED);
			jobRepository.save(job);
		}
		return response;
	}

	private void fetchBucketObjects(Job j1, String bucketName) {
		try {
			Optional<Bucket> bucket = s3Client.listBuckets().buckets().stream()
					.filter(b -> b.name().equalsIgnoreCase(bucketName)).findFirst();
			if (bucket.isPresent()) {
				List<String> existingObjects = s3ObjectRepository.findAll().stream().map(b -> b.getObjectName())
						.toList();
				List<S3Object> newObjects = s3Client.listObjectsV2(builder -> builder.bucket(bucket.get().name()))
						.contents().stream().map(bkt -> new S3Object(bucketName, bkt.key(), j1)).toList();

				if (!existingObjects.isEmpty()) {
					newObjects = newObjects.stream().filter(bkt -> !existingObjects.contains(bkt.getObjectName()))
							.toList();
				}

				if (!newObjects.isEmpty()) {
					s3ObjectRepository.saveAll(newObjects);
				}
				j1.setJobStatus(JobStatus.SUCCESS);
				jobRepository.save(j1);
			}
		} catch (Exception e) {
			log.error("Facing issue while saving Job - ", e.getMessage());
			j1.setJobStatus(JobStatus.FAILED);
			jobRepository.save(j1);
		}

	}

	public Long fetchObjectsCount(String bucketName) {
		Long count = 0L;
		try {
			count = s3ObjectRepository.findAll().stream()
					.filter(o -> o.getBucketName().equalsIgnoreCase(bucketName))
					.count();

		} catch (Exception e) {
			log.error("Facing issue while fetching buckets - ", e.getMessage());
		}
		return count;
	}

	public List<String> getObjectsLike(ObjectSearchRequest request) {
		List<String> res = new ArrayList<>();
		try {
			res = s3ObjectRepository.findAll().stream()
					.filter(o -> o.getBucketName().equalsIgnoreCase(request.getBucketName()) && o.getObjectName().contains(request.getPattern()))
					.map(S3Object::getObjectName).toList();

		} catch (Exception e) {
			log.error("Facing issue while fetching buckets - ", e.getMessage());
		}
		return res;
	}
}
