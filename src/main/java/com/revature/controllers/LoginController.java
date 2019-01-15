package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.services.AuthService;

@Controller
@RequestMapping(value="/login")
public class LoginController {

	@Autowired
	AuthService authService;
	
	/**
	 * Returns an admin in a response body if the admin send in
	 * the request body matches an admin in the database. Will
	 * return null if the admin does not exist.
	 * 
	 * @param adminDto
	 * @return An admin or null if one is not found
	 * @author Jaron | 1811-Java-Nick | 1/4/2019
	 */
	@PostMapping
	@ResponseBody
	public Admin loginPost(@RequestBody AdminDto adminDto) {
		
		Admin authAdmin = authService.validateAdmin(adminDto.getUsername(), adminDto.getPassword());
		
		return authAdmin;
	}
	
}
