package com.revature.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.LoginController;
import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.services.AuthService;

/**
 * Tests for LoginController
 * 
 * @author Kyne Liu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper om;
	
	@Mock
	AuthService mockAuthService;
	
	@InjectMocks
	LoginController controller;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		om = new ObjectMapper();
	}
	
	/*----------------------Tests for loginPost(AdminDto) method-----------------------*/

	@Test
	public void successfulLoginPost() throws JsonProcessingException, Exception{
		String username = "Username";
		String password = "password";
		AdminDto input = new AdminDto();
		input.setUsername(username);
		input.setPassword(password);
		
		int id = 10;
		Admin returned = new Admin();
		returned.setAdminId(id);
		returned.setUsername(username);
		returned.setPassword(password);
		 
		Mockito.when(mockAuthService.validateAdmin(username, password)).thenReturn(returned);
		
		mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON)
								.content(om.writeValueAsString(input)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().bytes(om.writeValueAsBytes(returned)));
	}
	
	//Wrong password given
	@Test
	public void wrongPasswordForLoginPost() throws JsonProcessingException, Exception {
		String username = "Username";
		String badPassword = "badPassword";
		AdminDto input = new AdminDto();
		input.setUsername(username);
		input.setPassword(badPassword);
		
		Mockito.when(mockAuthService.validateAdmin(username, badPassword)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON)
								.content(om.writeValueAsString(input)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	//Wrong username given
	@Test
	public void wrongUsernameForLoginPost() throws JsonProcessingException, Exception {
		String badUsername = "badUsername";
		String password = "password";
		AdminDto input = new AdminDto();
		input.setUsername(badUsername);
		input.setPassword(password);
		
		Mockito.when(mockAuthService.validateAdmin(badUsername, password)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON)
								.content(om.writeValueAsString(input)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	//Something goes wrong in the database while fetching the Admin
	@Test
	public void adminRepoErrorForLoginPost() throws JsonProcessingException, Exception {
		String username = "Username";
		String password = "password";
		AdminDto input = new AdminDto();
		input.setUsername(username);
		input.setPassword(password);
		
		Mockito.when(mockAuthService.validateAdmin(username, password)).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
		
		mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON)
								.content(om.writeValueAsString(input)))
				.andDo(print())
				.andExpect(status().is5xxServerError());
	}
}
