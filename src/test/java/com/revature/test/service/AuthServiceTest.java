package com.revature.test.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.services.AdminService;
import com.revature.services.AuthService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {

	@Mock
	AdminService mockAdminService;
	
	@InjectMocks
	AuthService service;
}
