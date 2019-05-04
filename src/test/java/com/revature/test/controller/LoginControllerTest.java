package com.revature.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.LoginController;
import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.models.Role;
import com.revature.security.JwtTokenFilter;
import com.revature.security.JwtTokenProvider;
import com.revature.services.AuthService;

/**
 * Tests for LoginController
 * 
 * @author Kyne Liu
 *
 */

@RunWith(SpringRunner.class)
public class LoginControllerTest {

	private MockMvc mockMvc;

	@Mock
	JwtTokenProvider provider;

	@MockBean
	private AuthService mockAuthService;

	@InjectMocks
	LoginController loginController;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(loginController)
				.apply(SecurityMockMvcConfigurers.springSecurity(new JwtTokenFilter(provider))).build();
	}

	/*----------------------Tests for loginPost(AdminDto) method-----------------------*/

	@Test
	public void successfulLoginPost() throws JsonProcessingException, Exception {

		ObjectMapper om = new ObjectMapper();

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

		ArrayList<Role> tmp = new ArrayList<Role>();
		tmp.add(Role.ROLE_ADMIN);
		returned.setRoles(tmp);

		Mockito.when(mockAuthService.validateAdmin(username, password)).thenReturn(returned);

		System.out.println(om.writeValueAsString(input));

		mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(input))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().bytes(om.writeValueAsBytes(returned))).andDo(MockMvcResultHandlers.print());
	}

	// Wrong password given
	@Test
	public void wrongPasswordForLoginPost() throws JsonProcessingException, Exception {
		ObjectMapper om = new ObjectMapper();
		String username = "Username";
		String badPassword = "badPassword";
		AdminDto input = new AdminDto();
		input.setUsername(username);
		input.setPassword(badPassword);

		Mockito.when(mockAuthService.validateAdmin(username, badPassword))
				.thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

		mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(input))).andDo(print()).andExpect(status().isBadRequest());
	}

	// Wrong username given
	@Test
	public void wrongUsernameForLoginPost() throws JsonProcessingException, Exception {
		ObjectMapper om = new ObjectMapper();

		String badUsername = "badUsername";
		String password = "password";
		AdminDto input = new AdminDto();
		input.setUsername(badUsername);
		input.setPassword(password);

		Mockito.when(mockAuthService.validateAdmin(badUsername, password))
				.thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

		mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(input))).andDo(print()).andExpect(status().isBadRequest());
	}

	// Something goes wrong in the database while fetching the Admin
	@Test
	public void adminRepoErrorForLoginPost() throws JsonProcessingException, Exception {
		ObjectMapper om = new ObjectMapper();

		String username = "Username";
		String password = "password";
		AdminDto input = new AdminDto();
		input.setUsername(username);
		input.setPassword(password);

		Mockito.when(mockAuthService.validateAdmin(username, password))
				.thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

		mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(input))).andDo(print()).andExpect(status().is5xxServerError());
	}
}
