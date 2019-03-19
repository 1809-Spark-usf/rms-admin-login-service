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

import com.revature.controllers.LoginController;
import com.revature.services.AuthService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	AuthService mockAuthService;
	
	@InjectMocks
	LoginController controller;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		MockitoAnnotations.initMocks(this);
	}

}
