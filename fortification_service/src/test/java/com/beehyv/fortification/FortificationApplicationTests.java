package com.beehyv.fortification;

import com.beehyv.fortification.dto.requestDto.DSLDto;
import com.beehyv.fortification.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class FortificationApplicationTests {

	@Autowired
	private AdminService adminService;

	@Test
    public void contextLoads() {
	}
}
