package org.path.fortification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = { "org.path.*" })
@EnableAsync
public class FortificationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FortificationApplication.class, args);
	}

}
