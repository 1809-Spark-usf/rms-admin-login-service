package com.revature.test.role;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.revature.models.Role;

public class RoleTest {

	@Test
	public void getAuthorityTest() {
		assertEquals("ROLE_ADMIN",Role.ROLE_ADMIN.getAuthority());
	}
}
