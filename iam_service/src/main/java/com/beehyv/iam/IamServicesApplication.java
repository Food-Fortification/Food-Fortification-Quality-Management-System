package com.beehyv.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = { "com.beehyv.*" })
public class IamServicesApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IamServicesApplication.class, args);
	}
}
