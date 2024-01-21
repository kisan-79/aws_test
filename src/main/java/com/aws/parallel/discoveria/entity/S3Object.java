package com.aws.parallel.discoveria.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class S3Object {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "object_id")
	private int objectId;

	@Column(name = "object_name")
	private String objectName;
	
	@Column(name = "bucket_name")
	private String bucketName;

	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public S3Object(String bucketName, String objectName, Job job) {
		super();
		this.objectName = objectName;
		this.job = job;
		this.bucketName = bucketName;
	}

	public S3Object() {
		super();
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	

}
