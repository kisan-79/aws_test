This Spring Boot project provides REST APIs for asynchronous discovery of AWS EC2 instances and S3 buckets. Users can perform various operations related to service discovery and retrieval of results.

API Endpoints
Discover Service
/api/discover-services (POST)
Input: List of services (EC2, S3)
Output: JobId 

Get Job Result
/api/job-result?jobId= (GET)
Input: JobId 
Output: Success, In Progress, or Failed

Get Discovery Result
/api/discovery-result?service=s3 (GET)
Input: Service Name (EC2 or S3)
Output: For S3 - List of S3 Buckets; For EC2 - List of Instance IDs

Get S3 Bucket Objects
/api/s3-objects?bucketName=bkt1 (GET)
Input: S3 bucket name
Output: JobId 

Get S3 Bucket Object Count
api/s3-objects-count?bucketName= (GET)
Input: S3 bucket name
Output: Count of S3 bucket objects

Get S3 Bucket Object Like
api/s3-objects (Post)
Input: Bucketname, pattern as request body
Output: List of file names matching the pattern


Technologies
Java
Spring Boot
AWS SDK for Java
MySQL
Jpa
