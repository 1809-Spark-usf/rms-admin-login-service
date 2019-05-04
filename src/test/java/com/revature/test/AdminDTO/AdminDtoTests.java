package com.revature.test.AdminDTO;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.revature.dtos.AdminDto;

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
		assertEquals(20,adminDto.getAdminId());
		assertEquals("Jimmy",adminDto.getFirstname());
		assertEquals("Dean",adminDto.getLastname());
		assertEquals("JimmyD",adminDto.getUsername());
		assertEquals("1234",adminDto.getPassword());
	}
	
	@Test
	public void testAdminDtoConstructor() {
		AdminDto adminDto2 = new AdminDto(21,"Jimmy","Dean2","JimmyD2","1234");
		assertEquals(21,adminDto2.getAdminId());
		assertEquals("Jimmy",adminDto2.getFirstname());
		assertEquals("Dean2",adminDto2.getLastname());
		assertEquals("JimmyD2",adminDto2.getUsername());
		assertEquals("1234",adminDto2.getPassword());
	}
	
	@Test
	public void testAdminDtoToString() {
		AdminDto adminDto3 = new AdminDto(21,"Jimmy","Dean3","JimmyD3","1234");
		String string = adminDto3.toString();
		assertEquals(("Admin [firstname=" + adminDto3.getFirstname() + ", lastname=" + adminDto3.getLastname() + ", username="
				+ adminDto3.getUsername() + ", password=" + adminDto3.getPassword() + "]"), string);
	}
	

}
