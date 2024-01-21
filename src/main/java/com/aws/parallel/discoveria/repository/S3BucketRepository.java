package com.aws.parallel.discoveria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aws.parallel.discoveria.entity.S3Bucket;
@Repository
public interface S3BucketRepository extends JpaRepository<S3Bucket, Integer>{


}
