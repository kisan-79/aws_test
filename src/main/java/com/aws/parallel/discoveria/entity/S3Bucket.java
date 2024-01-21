package com.aws.parallel.discoveria.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
public class S3Bucket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bucket_id")
	private int bucketId;

	@Column(name = "bucket_name")
	private String bucketName;

	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;

	@Column(name = "time_stamp")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime timeStamp;

	public S3Bucket(String bucketName, Job job, LocalDateTime timeStamp) {
		super();
		this.bucketName = bucketName;
		this.job = job;
		this.timeStamp = timeStamp;
	}

	public S3Bucket() {
		super();
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
}
