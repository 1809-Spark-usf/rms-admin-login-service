package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.services.AdminService;

@RestController
@RequestMapping(value="/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@PostMapping
	public void createAdmin(@RequestBody AdminDto adminDto) {
		
		Admin admin = new Admin();
		admin.setFirstname(adminDto.getFirstname());
		admin.setLastname(adminDto.getLastname());
		admin.setUsername(adminDto.getUsername());
		admin.setPassword(adminDto.getPassword());
		
		adminService.createAdmin(admin);
	}
}
