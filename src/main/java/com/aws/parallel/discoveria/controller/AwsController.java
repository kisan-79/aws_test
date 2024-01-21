package com.aws.parallel.discoveria.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aws.parallel.discoveria.constant.ApplicationConstants;
import com.aws.parallel.discoveria.model.BaseResponse;
import com.aws.parallel.discoveria.model.DiscoverServiceRequest;
import com.aws.parallel.discoveria.model.JobResponse;
import com.aws.parallel.discoveria.model.ObjectSearchRequest;
import com.aws.parallel.discoveria.service.AwsService;

@RestController
@RequestMapping(path = "api/")
public class AwsController {

	private static Logger log = LoggerFactory.getLogger(AwsController.class);

	@Autowired
	private AwsService awsService;

	@PostMapping("discover-service")
	public ResponseEntity<JobResponse> DiscoverServices(@RequestBody DiscoverServiceRequest request) {
		log.info("inside DiscoverServices");
		JobResponse response = new JobResponse();
		if (request.getServices().isEmpty()) {
			log.error("Provided invalid service");
			response.setStatus("Please provide valid service");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response = awsService.fetchAndSaveService(request.getServices());
		return ResponseEntity.ok(response);
	}

	@GetMapping("job-result")
	public ResponseEntity<JobResponse> getJobDetails(@RequestParam Integer jobId) {
		return ResponseEntity.ok(awsService.getJobDetails(jobId));
	}
	
	@GetMapping("discovery-result")
	public ResponseEntity<BaseResponse> getServiceDetails(@RequestParam String service) {
		BaseResponse response = new BaseResponse();
		if(ApplicationConstants.EC2.equalsIgnoreCase(service)) {
			List<String> res = awsService.getAllEc2Instances();
			response.setData(res);
		}else if(ApplicationConstants.S3.equalsIgnoreCase(service)) {
			List<String> res = awsService.getAllS3Buckets();
			response.setData(res);
		}else {
			response.setStatus(ApplicationConstants.ERROR);
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		response.setStatus(ApplicationConstants.SUCCESS);
		return ResponseEntity.ok(response);
	}

	
	@GetMapping("s3-objects")
	public ResponseEntity<JobResponse> s3Objects(@RequestParam("bucketName") String bucketName){
		JobResponse response = awsService.fetchObjects(bucketName);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("s3-objects-count")	
	public ResponseEntity<Long> s3ObjectsCount(@RequestParam("bucketName") String bucketName){
		Long count = awsService.fetchObjectsCount(bucketName);
		return ResponseEntity.ok(count);
	}
	
	@PostMapping("s3-objects")	
	public ResponseEntity<List<String>> s3ObjectsLike(@RequestBody ObjectSearchRequest request){
		List<String> files = awsService.getObjectsLike(request);
		return ResponseEntity.ok(files);
	}
	

}
