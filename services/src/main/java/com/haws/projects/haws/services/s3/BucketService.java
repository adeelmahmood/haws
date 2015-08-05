package com.haws.projects.haws.services.s3;

import com.haws.projects.haws.services.exceptions.AWSException;

public interface BucketService {

	String createBucket(String bucketName) throws AWSException;
}
