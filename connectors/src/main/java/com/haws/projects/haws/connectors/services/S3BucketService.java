package com.haws.projects.haws.connectors.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3BucketService implements BucketService {

	private static final Logger log = LoggerFactory.getLogger(S3BucketService.class);

	private final AmazonS3 client;

	@Autowired
	public S3BucketService(AmazonS3 client) {
		this.client = client;
	}

	@Override
	public String createBucket(String bucketName) {
		if (!client.doesBucketExist(bucketName)) {
			log.debug("creating bucket " + bucketName);
			client.createBucket(bucketName);
		}
		return client.getBucketLocation(bucketName);
	}
}
