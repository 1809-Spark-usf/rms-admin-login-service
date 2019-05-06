package com.revature.test.jwttokenfilter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.revature.exception.CustomException;
import com.revature.models.Role;
import com.revature.security.JwtTokenFilter;
import com.revature.security.JwtTokenProvider;


public class JwtTokenFilterTests {

	@Mock
	JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);

	@Mock
	FilterChain filterChain = mock(FilterChain.class);

	@Mock
	HttpServletRequest request = mock(HttpServletRequest.class);

	@Mock
	HttpServletResponse response = mock(HttpServletResponse.class);

	@Test
	public void doFilterSuccessTest() throws ServletException, IOException {
		JwtTokenFilter tmp = new JwtTokenFilter(jwtTokenProvider);
		UserDetails userDetails = org.springframework.security.core.userdetails.User//
				.withUsername("test")//
				.password("test")//
				.authorities(Role.ROLE_ADMIN)//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();

		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());

		Mockito.when(jwtTokenProvider.resolveToken(request)).thenReturn("test");
		Mockito.when(jwtTokenProvider.validateToken("test")).thenReturn(true);
		Mockito.when(jwtTokenProvider.getAuthentication("test")).thenReturn(auth);

		tmp.doFilter(request, response, filterChain);

		assertEquals(auth, SecurityContextHolder.getContext().getAuthentication());
		SecurityContextHolder.clearContext();
	}

	@Test
	public void doFilterRequestFailureNullToken() throws ServletException, IOException {

		JwtTokenFilter tmp = new JwtTokenFilter(jwtTokenProvider);
		Mockito.when(jwtTokenProvider.resolveToken(request)).thenReturn(null);

		tmp.doFilter(request, response, filterChain);

		assertEquals(null, SecurityContextHolder.getContext().getAuthentication());

	}

	@Test
	public void doFilterRequestFailureInvalidToken() throws ServletException, IOException {
		JwtTokenFilter tmp = new JwtTokenFilter(jwtTokenProvider);
		Mockito.when(jwtTokenProvider.resolveToken(request)).thenReturn("test");

		tmp.doFilter(request, response, filterChain);

		assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
	}

	@Test
	public void doFilterRequestFailureExpiredToken() throws ServletException, IOException {
		JwtTokenFilter tmp = new JwtTokenFilter(jwtTokenProvider);
		Mockito.when(jwtTokenProvider.resolveToken(request)).thenReturn("test");
		Mockito.when(jwtTokenProvider.validateToken("test"))
				.thenThrow(new CustomException("TEST", HttpStatus.INTERNAL_SERVER_ERROR));

		tmp.doFilter(request, response, filterChain);

		assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
	}

}
