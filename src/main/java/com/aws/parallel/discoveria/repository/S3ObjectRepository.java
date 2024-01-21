package com.aws.parallel.discoveria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aws.parallel.discoveria.entity.S3Object;


@Repository
public interface S3ObjectRepository extends JpaRepository<S3Object, Integer> {
	
	S3Object findByBucketName(String bucketName);
}
