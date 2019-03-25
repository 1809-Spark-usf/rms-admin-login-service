package com.revature.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.AdminController;
import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.services.AdminService;

/**
 * Tests for AdminContoller class
 * 
 * @author Kyne Liu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper om;
	
	@Mock
	private AdminService mockAdminService;
	
	@InjectMocks
	private AdminController controller;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		MockitoAnnotations.initMocks(this);
		om = new ObjectMapper();
	}
	
	/*------------------------Tests for createAdmin(AdminDto) method------------------------*/
	
	@Test
	public void successfulCreateAdmin() throws JsonProcessingException, Exception {
		AdminDto input = new AdminDto();
		input.setFirstname("First_name");
		input.setLastname("Last_name");
		input.setUsername("username");
		input.setPassword("password");
		
		Mockito.when(mockAdminService.createAdmin(any(Admin.class))).thenReturn(any(Admin.class));
		
		mockMvc.perform(post("/admin").accept(MediaType.APPLICATION_JSON)
									  .contentType(MediaType.APPLICATION_JSON)
									  .content(om.writeValueAsString(input)))
				.andDo(print())
				.andExpect(status().isCreated());
		
		Mockito.verify(mockAdminService).createAdmin(any(Admin.class));
	}
	
	//Something goes wrong during saving the admin in the database
	@Test
	public void adminRepoErrorForCreateAdmin() throws JsonProcessingException, Exception {
		AdminDto input = new AdminDto();
		input.setFirstname("First_name");
		input.setLastname("Last_name");
		input.setUsername("username");
		input.setPassword("password");
		
		Mockito.when(mockAdminService.createAdmin(any(Admin.class))).thenThrow(new DataAccessResourceFailureException(null));
		
		mockMvc.perform(post("/admin").accept(MediaType.APPLICATION_JSON)
									  .contentType(MediaType.APPLICATION_JSON)
									  .content(om.writeValueAsString(input)))
				.andDo(print())
				.andExpect(status().is5xxServerError());
		Mockito.verify(mockAdminService).createAdmin(any(Admin.class));
	}

}
