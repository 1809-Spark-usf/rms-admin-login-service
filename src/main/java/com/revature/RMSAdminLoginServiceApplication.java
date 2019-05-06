package com.revature;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.revature.models.Admin;
import com.revature.models.Role;
import com.revature.repositories.AdminRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class RMSAdminLoginServiceApplication implements CommandLineRunner{

	@Autowired
	AdminRepository adminRepository;

	public static void main(String[] args) {
		SpringApplication.run(RMSAdminLoginServiceApplication.class, args);
	}

	@Override
	public void run(String... params) throws Exception {
		Admin admin = new Admin();
		admin.setFirstname("First");
		admin.setLastname("Last");
		admin.setUsername("test@revature.com");
		admin.setPassword("password");

		ArrayList<Role> tmp = new ArrayList<Role>();
		admin.setRoles(tmp);

		adminRepository.save(admin);
	}
}
