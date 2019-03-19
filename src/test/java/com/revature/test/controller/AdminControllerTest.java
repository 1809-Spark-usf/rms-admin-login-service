package com.revature.test.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.controllers.AdminController;
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

}
