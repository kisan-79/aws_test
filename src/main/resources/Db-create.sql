CREATE DATABASE aws_test;

USE aws_test;

CREATE TABLE job(
job_id int PRIMARY KEY,
job_name VARCHAR(25) NOT NULL,
job_status enum('SUCCESS','FAILED','IN_PROGRESS') NOT NULL);


CREATE TABLE ec2(
ec2_id int PRIMARY KEY,
instance_id VARCHAR(40) NOT NULL,
instance_status VARCHAR(20) NOT NULL,
time_stamp DATETIME,
job_id int,
FOREIGN KEY (job_id) REFERENCES job(job_id));


CREATE TABLE s3Bucket(
bucket_id int PRIMARY KEY,
bucket_name VARCHAR(40) NOT NULL,
time_stamp DATETIME,
job_id int,
FOREIGN KEY (job_id) REFERENCES job(job_id));



CREATE TABLE s3Object(
object_id int PRIMARY KEY,
bucket_name VARCHAR(40) NOT NULL,
object_name VARCHAR(40) NOT NULL,
job_id int,
FOREIGN KEY (job_id) REFERENCES job(job_id));

