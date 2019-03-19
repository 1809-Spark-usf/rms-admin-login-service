package com.revature.test.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.repositories.AdminRepository;
import com.revature.services.AdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

	@Mock
	AdminRepository mockAdminRepo;
	
	@InjectMocks
	AdminService service;
}
