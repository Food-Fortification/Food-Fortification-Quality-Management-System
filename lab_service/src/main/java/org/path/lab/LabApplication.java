package org.path.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = { "org.path.*" })
@EnableAsync
public class LabApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LabApplication.class, args);
	}

}
