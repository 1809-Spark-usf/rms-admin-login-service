package com.revature.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Admin;
import com.revature.services.AdminService;
import com.revature.services.AuthService;

/**
 * Tests for the AuthService
 * 
 * @author Kyne Liu
 *
 */
@RunWith(SpringRunner.class)
public class AuthServiceTest {

	@Mock
	AdminService mockAdminService;
	
	@InjectMocks
	AuthService testService;
	
	/*----------------------Tests for validateAdmin(String, String) method----------------------*/
	
	@Test
	public void successfulValidateAdmin() {
		String targetUsername = "Username";
		String targetPassword = "password";
		Admin returned = new Admin();
		returned.setUsername(targetUsername);
		returned.setPassword(targetPassword);
		
		Mockito.when(mockAdminService.getAdminByUsername(targetUsername)).thenReturn(returned);
		
		Admin result = testService.validateAdmin(targetUsername, targetPassword);
		
		assertNotNull("Method should never return a null value.", result);
		assertThat("Should return the Admin with given username on successful auth.", result, is(returned));
	}
	
	//wrong password entered
	@Test
	public void badPasswordForValidateAdmin() {
		String targetUsername = "Username";
		String badPassword = "badPassword";
		String password = "password";
		Admin returned = new Admin();
		returned.setUsername(targetUsername);
		returned.setPassword(password);
		
		Mockito.when(mockAdminService.getAdminByUsername(targetUsername)).thenReturn(returned);
		
		Admin result = null;
		HttpClientErrorException returnedException = null;
		
		try {
			result = testService.validateAdmin(targetUsername, badPassword);
			fail("Method should throw an exception.");
		} catch (HttpClientErrorException e) { 
			returnedException = e;
		}
		
		assertNull("Method should not get to the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
		assertThat("Http status should be 400 bad request", returnedException.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}
	
	//Admin with given username not found
	@Test
	public void badUsernameForValidateAdmin() {
		String badUsername = "badUsername";
		String password = "password";
		
		Mockito.when(mockAdminService.getAdminByUsername(badUsername)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		Admin result = null;
		HttpClientErrorException returnedException = null;
		
		try {
			result = testService.validateAdmin(badUsername, password);
			fail("Method should throw an exception.");
		} catch (HttpClientErrorException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get to the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
		assertThat("Method should throw http 400 bad request.", returnedException.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}
	
	//Something goes wrong in the database while trying to get the admin
	@Test
	public void databaseErrorWhileGettingAdminForValidateAdmin() {
		String username = "Username";
		String password = "password";
		
		Mockito.when(mockAdminService.getAdminByUsername(username)).thenThrow(new DataAccessResourceFailureException(null));
		
		Admin result = null;
		HttpClientErrorException returnedException = null;
		
		try {
			result = testService.validateAdmin(username, password);
			fail("Method should throw an exception.");
		} catch (HttpClientErrorException e) {
			returnedException = e;
		}
		
		assertNull("Method shoud not get to the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
		assertThat("Method should throw http 400 bad request", returnedException.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
	}
}
