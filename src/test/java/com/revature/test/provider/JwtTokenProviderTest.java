package com.revature.test.provider;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.exception.CustomException;
import com.revature.models.Admin;
import com.revature.models.Role;
import com.revature.repositories.AdminRepository;
import com.revature.security.JwtTokenProvider;
import com.revature.security.MyUserDetails;

import io.jsonwebtoken.JwtException;

/**
 * 
 * @author Alim Wooden
 *
 */

@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
public class JwtTokenProviderTest {

//	@Autowired
//	MockMvc mvc;
//		
	@MockBean
	MyUserDetails myUserDetails;

	@InjectMocks
	JwtTokenProvider jwtTokenProvider;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createTokenTest() {

		Admin admin = mock(Admin.class);
		admin.setUsername("test@revature.com");
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
		String token = jwtTokenProvider.createToken(admin.getUsername(), admin.getRoles());
		//assertEquals(role, admin.getRoles());
		assertNotNull(token);	
	}
	
	@Test
	public void getUsernameTest() {
		Admin admin = mock(Admin.class);
		admin.setUsername("test@revature.com");
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
		String token = jwtTokenProvider.createToken("test@revature.com", admin.getRoles());
		String username = jwtTokenProvider.getUsername(token);
		assertEquals("test@revature.com", username);
	} 

	@Test
	public void getAuthenticatioinTest() {
		
		Admin admin = mock(Admin.class);
		admin.setUsername("test@revature.com");
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
		String token = jwtTokenProvider.createToken("test@revature.com", admin.getRoles());
		
		UserDetails userdetails = mock(UserDetails.class);
		userdetails = org.springframework.security.core.userdetails.User
		        .withUsername("test@revature.com")
		        .password("password")
		        .authorities(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)))
		        .accountExpired(false)
		        .accountLocked(false)
		        .credentialsExpired(false)
		        .disabled(false)
		        .build();
		
		Mockito.when(myUserDetails.loadUserByUsername(Mockito.anyString())).thenReturn(userdetails);
		Authentication auth = jwtTokenProvider.getAuthentication(token);
		assertNotNull(auth);
	}
	
	@Test
	public void validateTokenTestSucess() {
		Admin admin = mock(Admin.class);
		admin.setUsername("test@revature.com");
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
		String token = jwtTokenProvider.createToken(admin.getUsername(), admin.getRoles());
		
		Boolean check = jwtTokenProvider.validateToken(token);
		assertEquals(true, check);
	}
	
	@Test
	public void resolveTokenTest() {}
	
}
