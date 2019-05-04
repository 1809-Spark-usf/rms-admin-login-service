package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.security.JwtTokenProvider;
import com.revature.services.AuthService;

@RestController
public class LoginController {

	@Autowired
	AuthService authService;

	@Autowired
	JwtTokenProvider jwtTokenPovider;

	/**
	 * Returns an admin in a response body if the admin send in the request body
	 * matches an admin in the database. Will return null if the admin does not
	 * exist.
	 * 
	 * @param adminDto
	 * @return An admin or null if one is not found
	 * @author Jaron | 1811-Java-Nick | 1/4/2019
	 */
	@PostMapping(value = "/login")
	public Admin loginPost(@RequestBody AdminDto tmp) {

		Admin authAdmin = authService.validateAdmin(tmp.getUsername(), tmp.getPassword());

		String token = jwtTokenPovider.createToken(authAdmin.getUsername(), authAdmin.getRoles());
		authAdmin.setToken(token);

		return authAdmin;
	}

	@ExceptionHandler
	public ResponseEntity<String> handleHttpClientException(HttpClientErrorException e) {
		String message = e.getMessage();
		return ResponseEntity.status(e.getStatusCode()).body(message);

	}

}
