package com.aws.parallel.discoveria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id")
	private int jobId;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private JobStatus jobStatus;

	@Column(name = "job_name")
	private String jobName;

	public Job() {
		super();
	}

	public Job(int jobId, JobStatus jobStatus, String jobName) {
		super();
		this.jobId = jobId;
		this.jobStatus = jobStatus;
		this.jobName = jobName;
	}

	public Job(JobStatus jobStatus, String jobName) {
		super();
		this.jobStatus = jobStatus;
		this.jobName = jobName;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	
}
