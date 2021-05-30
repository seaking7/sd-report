package com.uplus.sdreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SdReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdReportApplication.class, args);
	}

}
