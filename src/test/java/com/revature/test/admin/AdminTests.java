package com.revature.test.admin;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.revature.models.Admin;

public class AdminTests {

	@Test
	public void adminTestsConstructor() {
		Admin admin = new Admin("test", "test", "test@revature.com", "1234");

		assertEquals("test", admin.getFirstname());
		assertEquals("test", admin.getLastname());
		assertEquals("test@revature.com", admin.getUsername());
		assertEquals("1234", admin.getPassword());

	}

	@Test
	public void adminTestsSetters() {
		Admin admin = new Admin();

		admin.setFirstname("test");
		admin.setLastname("test");
		admin.setUsername("test@revature.com");
		admin.setPassword("1234");

		assertEquals("test", admin.getFirstname());
		assertEquals("test", admin.getLastname());
		assertEquals("test@revature.com", admin.getUsername());
		assertEquals("1234", admin.getPassword());

	}

	@Test
	public void adminToStringTest() {
		Admin admin = new Admin("test", "test", "test@revature.com", "1234");
		admin.setAdminId(1);

		assertEquals("Admin [adminId=1" + ", firstname=test" + ", lastname=test" + ", username=test@revature.com"
				+ ", password=1234" + "]", admin.toString());
	}

}
