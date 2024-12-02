package org.path.Immudb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"org.path.*"})
public class LogServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LogServiceApplication.class, args);
	}

}
