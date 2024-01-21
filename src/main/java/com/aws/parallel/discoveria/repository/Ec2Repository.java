package com.aws.parallel.discoveria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aws.parallel.discoveria.entity.EC2;

@Repository
public interface Ec2Repository extends JpaRepository<EC2, Integer> {

}
