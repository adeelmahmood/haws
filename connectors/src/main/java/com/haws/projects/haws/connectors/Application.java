package com.haws.projects.haws.connectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@PropertySource("aws-credentials.properties")
//@ImportResource("aws.xml")
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(false).logStartupInfo(false).run(args);
	}

	@Autowired
	BucketService bucketService;

	@Value("${s3.bucket}")
	String bucket;

	@PostConstruct
	public void init() {
		bucketService.createBucket(bucket);
	}
}
