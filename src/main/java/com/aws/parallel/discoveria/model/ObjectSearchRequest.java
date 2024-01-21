package com.aws.parallel.discoveria.model;

public class ObjectSearchRequest {

	private String bucketName;

	private String Pattern;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getPattern() {
		return Pattern;
	}

	public void setPattern(String pattern) {
		Pattern = pattern;
	}

}
