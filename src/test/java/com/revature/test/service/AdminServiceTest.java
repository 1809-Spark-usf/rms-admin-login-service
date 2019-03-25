package com.revature.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Admin;
import com.revature.repositories.AdminRepository;
import com.revature.services.AdminService;

/**
 * Test cases for AdminService
 * 
 * @author Kyne Liu
 *
 */
@RunWith(SpringRunner.class)
public class AdminServiceTest {

	@Mock
	AdminRepository mockAdminRepo;
	
	@InjectMocks
	AdminService testService;
	
	/*---------------------Tests for createAdmin(Admin) method-----------------------*/
	
	@Test
	public void successfulCreateAdmin() {
		int newId  = 10;
		Admin input = new Admin();
		Admin returned = new Admin();
		returned.setAdminId(newId);
		
		Mockito.when(mockAdminRepo.save(input)).thenReturn(returned);
		
		Admin result = testService.createAdmin(input);
		
		assertNotNull("Method should never return null.", result);
		assertThat("Method should return the saved admin with the id.", result, is(returned));
	}
	
	@Test
	public void adminRepoErrorForCreateAdmin() {
		Admin input = new Admin();
		
		Mockito.when(mockAdminRepo.save(input)).thenThrow(new DataAccessResourceFailureException(null));
		
		Admin result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.createAdmin(input);
			fail("Method should throw an error.");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should never get to the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
	
	/*---------------------Tests for getAdmin(int) method-----------------------*/
	
	@Test
	public void successfulGetAdmin() {
		int targetId = 10;
		Admin returned = new Admin();
		returned.setAdminId(targetId);
		
		Mockito.when(mockAdminRepo.findById(targetId)).thenReturn(Optional.ofNullable(returned));
		
		Admin result = testService.getAdmin(targetId);
		
		assertNotNull("Method should never return null.", result);
		assertThat("Method should return the Admin held in the Optional.", result, is(returned));
	}
	
	//No Admin found with given id
	@Test
	public void idNotFoundForGetAdmin() {
		int badId = 3;
		
		Mockito.when(mockAdminRepo.findById(badId)).thenReturn(Optional.empty());
		
		Admin result = null;
		HttpClientErrorException returnedException = null;
		
		try {
			result = testService.getAdmin(badId);
			fail("Method should throw an excpeiton.");
		} catch(HttpClientErrorException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get to the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
		assertThat("Should throw http 404 exception.", returnedException.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}
	
	//Something went wrong while accessing the database
	@Test
	public void adminRepoErrorForGetAdmin() {
		int targetId = 10;
		
		Mockito.when(mockAdminRepo.findById(targetId)).thenThrow(new DataAccessResourceFailureException(null));
		
		Admin result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.getAdmin(targetId);
			fail("Method should throw an exception.");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get to the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
	
	/*---------------------Tests for getAll() method-----------------------*/
	
	@Test
	public void successfulGetAll() {
		List<Admin> returned = new ArrayList<>();
		Admin first = new Admin();
		first.setAdminId(10);
		returned.add(first);
		Admin second = new Admin();
		second.setAdminId(20);
		returned.add(second);
		
		Mockito.when(mockAdminRepo.findAll()).thenReturn(returned);
		
		List<Admin> result = testService.getAll();
		
		assertNotNull("Method should never return a null value.", result);
		assertThat("Method should return the list returned by the repo.", result, is(returned));
	}
	
	//No results found so repo returns empty list
	@Test
	public void emptyListForGetAll() {
		List<Admin> returned = new ArrayList<>();
		
		Mockito.when(mockAdminRepo.findAll()).thenReturn(returned);
		
		List<Admin> result = testService.getAll();
		
		assertNotNull("Method should never return a null value.", result);
		assertThat("Method should return an empty list if no results are found.", result, is(returned));
	}
	
	//Something goes wrong while accessing the database
	@Test
	public void adminRepoErrorForGetAll() {
		Mockito.when(mockAdminRepo.findAll()).thenThrow(new DataAccessResourceFailureException(null));
		
		List<Admin> result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.getAll();
			fail("Method should throw an excpetion.");
		} catch(DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get to the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
	
	/*---------------------Tests for getAdminByUsername(String) method-----------------------*/
	
	@Test
	public void successfulGetAdminByUsername() {
		String targetUsername = "username";
		Admin returned = new Admin();
		returned.setUsername(targetUsername);
		
		Mockito.when(mockAdminRepo.getAdminByUsername(targetUsername)).thenReturn(Optional.ofNullable(returned));
		
		Admin result = testService.getAdminByUsername(targetUsername);
		
		assertNotNull("Method should never return null value.", result);
		assertThat("Method should return the Admin in the optional returned by the repo.", result, is(returned));
	}
	
	//Admin not found for given username
	@Test
	public void usernameNotFoundForGetAdminByUsername() {
		String badUsername = "badUsername";
		
		Mockito.when(mockAdminRepo.getAdminByUsername(badUsername)).thenReturn(Optional.empty());
		
		Admin result = null;
		HttpClientErrorException returnedException = null;
		
		try {
			result = testService.getAdminByUsername(badUsername);
			fail("Method should throw an exception.");
		} catch (HttpClientErrorException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get to the return statement.", result);
		assertNotNull("Method should throw an excpetion.", returnedException);
		assertThat("Method should throw http 404 exception.", returnedException.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}
	
	//Something goes wrong during the access of the database
	@Test
	public void adminRepoErrorForGetAdminByUsername() {
		String targetUsername = "Username";
		
		Mockito.when(mockAdminRepo.getAdminByUsername(targetUsername)).thenThrow(new DataAccessResourceFailureException(null));
		
		Admin result = null;
		DataAccessException returnedException = null;
		
		try {
			result = testService.getAdminByUsername(targetUsername);
			fail("Method should throw an exception.");
		} catch (DataAccessException e) {
			returnedException = e;
		}
		
		assertNull("Method should not get to the return statement.", result);
		assertNotNull("Method should throw an exception.", returnedException);
	}
}
