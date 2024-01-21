package com.aws.parallel.discoveria.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {
	@Value("${aws.accessKey}")
	private String accessKey;

	@Value("${aws.secretKey}")
	private String secretKey;

	@Value("${aws.region}")
	private String region;
	
	private static Ec2Client ec2Client;
	
	private static S3Client s3Client;

	@Bean
	public S3Client s3Client() {
		if(s3Client == null) {
			s3Client = S3Client.builder().region(Region.of(region)).credentialsProvider(getCredentialsProvider()).build();
		}
		return s3Client;
	}

	@Bean
	public Ec2Client ec2Client() {
		if(ec2Client == null) {
			ec2Client = Ec2Client.builder().region(Region.of(region)).credentialsProvider(getCredentialsProvider()).build();
		}
		return ec2Client;

	}

	private AwsCredentialsProvider getCredentialsProvider() {
		return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
	}
	
	@PreDestroy
	public void closeClients() {
		if(ec2Client != null) ec2Client.close();
		if(s3Client != null) s3Client.close();
	}

}
