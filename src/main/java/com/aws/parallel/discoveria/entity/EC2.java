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
public class EC2 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ec2_id")
	private int ec2Id;

	@Column(name = "instance_id")
	private String instanceId;

	@Column(name = "instance_status")
	private String instanceStatus;

	

	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;
	
	@Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timeStamp;
	
	public EC2(String instanceId, String instanceStatus, Job job, LocalDateTime timeStamp) {
		super();
		this.instanceId = instanceId;
		this.instanceStatus = instanceStatus;
		this.job = job;
		this.timeStamp = timeStamp;
	}
	public int getEc2Id() {
		return ec2Id;
	}

	public void setEc2Id(int ec2Id) {
		this.ec2Id = ec2Id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(String instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public EC2(int ec2Id, String instanceId, String instanceStatus, Job job,LocalDateTime timeStamp) {
		super();
		this.ec2Id = ec2Id;
		this.instanceId = instanceId;
		this.instanceStatus = instanceStatus;
		this.job = job;
		this.timeStamp = timeStamp;
	}

	public EC2() {
		super();
	}

}
