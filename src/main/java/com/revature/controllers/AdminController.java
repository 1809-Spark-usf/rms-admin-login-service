package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.services.AdminService;

@RestController
@RequestMapping(value="/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	/**
	 * this doesn't seem to be ever used because the method signiture is not in the repository
	 * @param adminDto
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createAdmin(@RequestBody AdminDto adminDto) {
		
		Admin admin = new Admin();
		admin.setFirstname(adminDto.getFirstname());
		admin.setLastname(adminDto.getLastname());
		admin.setUsername(adminDto.getUsername());
		admin.setPassword(adminDto.getPassword());
		try {
			adminService.createAdmin(admin);
		} catch (DataAccessException e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Please try again later.");
		}
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleHttpClientException(HttpClientErrorException e) {
		String message = e.getMessage();
		return ResponseEntity.status(e.getStatusCode()).body(message);

	}
}
