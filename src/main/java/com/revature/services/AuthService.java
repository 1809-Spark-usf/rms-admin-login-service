package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Admin;

/**
 * Service to authorize admins logging in
 * @author Jaron | 1811-Java-Nick | 1/4/2019
 *
 */
@Service
public class AuthService {

	@Autowired
	AdminService adminService;
	
	/**
	 * Checks if a given username and password match and admin
	 * in the database and returns the admin if they exist
	 * 
	 * @param username
	 * @param password
	 * @return An admin or null if there is none that matches
	 * @author Jaron | 1811-Java-Nick | 1/4/2019
	 */
	public Admin validateAdmin(String username, String password) {
		
		Admin admin = adminService.getAdminByUsername(username);
		
		if(admin != null && password.equals(admin.getPassword())) {
			return admin;
		}
		
		return null;
	}
}
