package com.beehyv.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = { "com.beehyv.*" })
@EnableAsync
public class LabApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LabApplication.class, args);
	}

}
