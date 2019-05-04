package com.revature.test.userdetail;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.models.Admin;
import com.revature.models.Role;
import com.revature.security.MyUserDetails;
import com.revature.services.AdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyUserDetailServiceTest {

	@Mock
	AdminService adminService = mock(AdminService.class);

	@InjectMocks
	MyUserDetails myUserDetails;

	@Test
	public void loadByUserNameSuccess() {
		Admin admin = new Admin("test", "test", "test@revature.com", "1234");

		Mockito.when(adminService.getAdminByUsername("test")).thenReturn(admin);

		UserDetails userDetails = org.springframework.security.core.userdetails.User//
				.withUsername("test")//
				.password("1234")//
				.authorities(Role.ROLE_ADMIN)//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();

		assertEquals(userDetails, myUserDetails.loadUserByUsername("test"));

	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadByUserNameFailureNoUser() {

		Mockito.when(adminService.getAdminByUsername("test")).thenReturn(null);

		myUserDetails.loadUserByUsername("test");
	}

}
