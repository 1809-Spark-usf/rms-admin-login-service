package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
		
		Admin admin = null;
		try {
			admin = adminService.getAdminByUsername(username);
		} catch (DataAccessException e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong please try again later");
		} catch (HttpClientErrorException e) {
			if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid username or password.");
			}
		}
		
		if(admin != null && password.equals(admin.getPassword())) {
			return admin;
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid username or password");
		}
		
	}
}
