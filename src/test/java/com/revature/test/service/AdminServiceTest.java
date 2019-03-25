package com.revature.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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

@RunWith(SpringRunner.class)
@SpringBootTest
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
	
}
