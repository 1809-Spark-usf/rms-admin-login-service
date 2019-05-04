package com.revature.test.admin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.junit.Test;

import com.revature.models.Admin;
import com.revature.models.Role;


/**
 * Tests for LoginController
 * 
 * @author Quinton Cook
 *
 */

public class AdminTests {

	@Test
	public void adminTestsConstructor() {
		Admin admin = new Admin("test", "test", "test@revature.com", "1234");

		assertEquals("test", admin.getFirstname());
		assertEquals("test", admin.getLastname());
		assertEquals("test@revature.com", admin.getUsername());
		assertEquals("1234", admin.getPassword());
		assertEquals(Role.ROLE_ADMIN, admin.getRoles().get(0));

	}

	@Test
	public void adminTestsSetters() {
		Admin admin = new Admin();

		admin.setFirstname("test");
		admin.setLastname("test");
		admin.setUsername("test@revature.com");
		admin.setPassword("1234");
		ArrayList<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.ROLE_ADMIN);
		admin.setRoles(roleList);

		assertEquals("test", admin.getFirstname());
		assertEquals("test", admin.getLastname());
		assertEquals("test@revature.com", admin.getUsername());
		assertEquals("1234", admin.getPassword());
		assertEquals(Role.ROLE_ADMIN, admin.getRoles().get(0));
	}

	@Test
	public void adminToStringTest() {
		Admin admin = new Admin("test", "test", "test@revature.com", "1234");
		admin.setAdminId(1);

		assertEquals("Admin [adminId=1" + ", roles=[ROLE_ADMIN]" + ", firstname=test" + ", lastname=test"
				+ ", username=test@revature.com" + ", password=1234" + "]", admin.toString());
	}

}
