package com.revature.test.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.AdminController;
import com.revature.dtos.AdminDto;
import com.revature.services.AdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private AdminService mockAdminService;
	
	@InjectMocks
	private AdminController controller;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createAdminTestWithGoodInput() throws JsonProcessingException, Exception {
		ObjectMapper om = new ObjectMapper();
		
		AdminDto input = new AdminDto();
		input.setFirstname("First_name");
		input.setLastname("Last_name");
		input.setUsername("username");
		input.setPassword("password");
		
		mockMvc.perform(post("/admin").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(input))).andDo(print());
	}

}
