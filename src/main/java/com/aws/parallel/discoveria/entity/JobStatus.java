package com.aws.parallel.discoveria.entity;

public enum JobStatus {
	SUCCESS("Success"), FAILED("Failed"), IN_PROCESS("In progess");

	private final String value;

	JobStatus(String status) {
		this.value = status;
	}

	public String getValue() {
		return value;
	}

	
}
