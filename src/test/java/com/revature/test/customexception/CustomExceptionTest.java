package com.revature.test.customexception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.revature.exception.CustomException;

public class CustomExceptionTest {

	@Test(expected = CustomException.class)
	public void CustomExceptionConstructorTest() {
		throw new CustomException("testing message", HttpStatus.I_AM_A_TEAPOT);
	}
	
	@Test
	public void CustomExceptionMessageandStatusTest() {
		try {
			throw new CustomException("testing message",HttpStatus.I_AM_A_TEAPOT);
		}catch(CustomException ex) {
			assertEquals("testing message",ex.getMessage());
			assertEquals(HttpStatus.I_AM_A_TEAPOT, ex.getHttpStatus());		
		}
	}
	
}
