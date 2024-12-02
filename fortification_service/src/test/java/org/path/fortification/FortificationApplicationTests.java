package org.path.fortification;

import org.path.fortification.service.AdminService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FortificationApplicationTests {

	@Autowired
	private AdminService adminService;

	@Test
    public void contextLoads() {
	}
}
