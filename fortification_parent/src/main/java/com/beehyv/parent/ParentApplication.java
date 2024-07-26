package com.beehyv.parent;

import javax.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParentApplication {
//	@Resource
//	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ParentApplication.class, args);
	}

}
