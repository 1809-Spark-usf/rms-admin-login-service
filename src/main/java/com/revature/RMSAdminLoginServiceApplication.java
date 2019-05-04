package com.revature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.revature.models.Admin;
import com.revature.repositories.AdminRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class RMSAdminLoginServiceApplication {

	@Autowired
	AdminRepository adminRepository;

	public static void main(String[] args) {
		SpringApplication.run(RMSAdminLoginServiceApplication.class, args);
	}

	public void run(String... params) throws Exception {
		Admin admin = new Admin("First", "Last", "test@revature.com", "password");
		adminRepository.save(admin);
	}

}
