package com.revature.test.admindto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.junit.Test;

import com.revature.dtos.AdminDto;
import com.revature.models.Role;

/**
 * Tests for LoginController
 * 
 * @author Alim Wooden
 *
 */

public class AdminDtoTests {
	

	@Test
	public void testAdminDto() {
		AdminDto adminDto = new AdminDto();
		adminDto.setAdminId(20);
		adminDto.setFirstname("Jimmy");
		adminDto.setLastname("Dean");
		adminDto.setUsername("JimmyD");
		adminDto.setPassword("1234");
		
		ArrayList<Role> tmp = new ArrayList<Role>();
		tmp.add(Role.ROLE_ADMIN);
		adminDto.setRoles(tmp);
		
		assertEquals(20,adminDto.getAdminId());
		assertEquals("Jimmy",adminDto.getFirstname());
		assertEquals("Dean",adminDto.getLastname());
		assertEquals("JimmyD",adminDto.getUsername());
		assertEquals("1234",adminDto.getPassword());
		assertEquals(Role.ROLE_ADMIN, adminDto.getRoles().get(0));
	}
	
	@Test
	public void testAdminDtoConstructor() {
		ArrayList<Role> tmp = new ArrayList<Role>();
		tmp.add(Role.ROLE_ADMIN);
		AdminDto adminDto2 = new AdminDto(21,"Jimmy","Dean2","JimmyD2","1234",tmp);
		
		assertEquals(21,adminDto2.getAdminId());
		assertEquals("Jimmy",adminDto2.getFirstname());
		assertEquals("Dean2",adminDto2.getLastname());
		assertEquals("JimmyD2",adminDto2.getUsername());
		assertEquals("1234",adminDto2.getPassword());
		assertEquals(Role.ROLE_ADMIN, adminDto2.getRoles().get(0));
	}
	
	@Test
	public void testAdminDtoToString() {
		ArrayList<Role> tmp = new ArrayList<Role>();
		tmp.add(Role.ROLE_ADMIN);
		AdminDto adminDto3 = new AdminDto(21,"Jimmy","Dean3","JimmyD3","1234",tmp);
		String string = adminDto3.toString();
		
		assertEquals(("AdminDto [adminId=21" + ", roles=[ROLE_ADMIN]" + ", firstname=Jimmy" + ", lastname=Dean3"
				+ ", username=JimmyD3" + ", password=1234" + "]"), string);
	}
}
