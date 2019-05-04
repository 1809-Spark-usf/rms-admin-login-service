package com.revature.test.repository;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.models.Admin;
import com.revature.repositories.AdminRepository;

/**
 * 
 * @author Alim Wooden
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminRepositoryTest {

	@Autowired
	AdminRepository adminRepository;

	@Test
	public void testGetAdminByUsernameValid() {
		Admin admin = new Admin();
		admin.setAdminId(1);
		admin.setFirstname("Jimmy");
		admin.setLastname("Dean");
		admin.setUsername("JimmyD");
		admin.setPassword("1234");
		
		adminRepository.save(admin);
		
		Optional<Admin> adminObj = adminRepository.getAdminByUsername("JimmyD");
		assertEquals(true,adminObj.isPresent());
		//assertNotNull(adminObj);
	}
	
	@Test
	public void testGetAdminByUsernameInvalid() {
		
		Admin admin = new Admin();
		admin.setAdminId(1);
		admin.setFirstname("Jimmy");
		admin.setLastname("Dean");
		admin.setUsername("JimmyD");
		admin.setPassword("1234");
		
		Optional<Admin> adminObj = adminRepository.getAdminByUsername("James");
		assertEquals(false,adminObj.isPresent());
		//assertNull(adminObj);
	}

}
