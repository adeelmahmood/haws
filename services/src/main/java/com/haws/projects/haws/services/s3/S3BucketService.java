package com.haws.projects.haws.services.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.haws.projects.haws.services.exceptions.AWSException;

@Service
public class S3BucketService implements BucketService {

	private static final Logger log = LoggerFactory.getLogger(S3BucketService.class);

	private final AmazonS3 client;

	@Autowired
	public S3BucketService(AmazonS3 client) {
		this.client = client;
	}

	@Override
	public String createBucket(String bucketName) throws AWSException {
		try {
			if (!client.doesBucketExist(bucketName)) {
				client.createBucket(bucketName);
			}
			return client.getBucketLocation(bucketName);
		} catch (AmazonClientException e) {
			log.error("error in creating bucket " + bucketName, e);
			throw new AWSException("error in creating bucket " + bucketName + " => " + e.getMessage(), e);
		}
	}
}
