package com.aws.parallel.discoveria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aws.parallel.discoveria.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
	
}
