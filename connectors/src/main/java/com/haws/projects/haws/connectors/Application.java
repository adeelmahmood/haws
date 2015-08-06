package com.haws.projects.haws.connectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.haws.projects.haws.connectors.services.BucketService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@PropertySource("classpath:aws-credentials.properties")
@ImportResource({ "classpath:aws.xml", "classpath:s3flows/file.xml" })
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(false).logStartupInfo(false).run(args);
	}

	@Autowired
	BucketService service;

	@Value("${s3.bucket}")
	String bucket;

	@PostConstruct
	public void init() {
		service.createBucket(bucket);
	}
}
