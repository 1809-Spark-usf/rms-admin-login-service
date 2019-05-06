package com.revature.test.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.revature.exception.CustomException;
import com.revature.models.Admin;
import com.revature.models.Role;
import com.revature.security.JwtTokenProvider;
import com.revature.security.MyUserDetails;

import io.jsonwebtoken.Jwts;

/**
 * 
 * @author Quinton Cook
 * @author Alim Wooden
 *
 */
@RunWith(SpringRunner.class)
public class JwtTokenProviderTest {

	@MockBean
	MyUserDetails myUserDetails;

	@InjectMocks
	JwtTokenProvider jwtTokenProvider;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "secret-key");
		ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", 3600000L);
    
	}

	@Test
	public void createTokenTest() {
		
		Admin admin = mock(Admin.class);
		admin.setUsername("test@revature.com");
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
		String token = jwtTokenProvider.createToken(admin.getUsername(), admin.getRoles());
		// assertEquals(role, admin.getRoles());
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
		userdetails = org.springframework.security.core.userdetails.User.withUsername("test@revature.com")
				.password("password").authorities(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)))
				.accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false).build();

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

	@Test(expected = CustomException.class)
	public void validateTokenTestFailureIllegalArument() {
		jwtTokenProvider.validateToken("stuff");
	}

	@Test(expected = CustomException.class)
	public void validateTokenTestFailureJwtException() {
		jwtTokenProvider.validateToken("test");
	}
	
	@Test
	public void resolveTokenTestSuccess() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		//build request header
		Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer hi");
		String subString = jwtTokenProvider.resolveToken(request);
		assertNotNull(subString);
	}
	
	@Test
	public void resolveTokenTestFailure() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		String subString = jwtTokenProvider.resolveToken(request);
		assertNull(subString);
	}

}
