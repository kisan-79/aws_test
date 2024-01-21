package com.aws.parallel.discoveria.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JobResponse {

	private String status;
	private String statusDesc;
	private Integer jobId;

	
	public String getStatusDesc() {
		return statusDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public JobResponse(String status, Integer jobId) {
		super();
		this.status = status;
		this.jobId = jobId;
	}

	public JobResponse() {
		super();
	}

	public void setStatusDesc(String desc) {
		this.statusDesc = desc;

	}

}
