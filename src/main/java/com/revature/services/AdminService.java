package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Admin;
import com.revature.repositories.AdminRepository;

/**
 * Service to manage connections to the admin table in the database
 * 
 * @author Jaron | 1811-Java-Nick | 1/4/2019
 *
 */
@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;

	/**
	 * Saves an admin to the database
	 * 
	 * @param admin
	 */
	public Admin createAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	/**
	 * Gets an admin from the databse by id
	 * 
	 * @param id
	 * @return Admin
	 */
	public Admin getAdmin(Integer id) throws HttpClientErrorException {
		return adminRepository.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Admin with given id was not found."));
	}

	/**
	 * Gets all admins from the database
	 * 
	 * @return List of admins
	 */
	public List<Admin> getAll() {
		return adminRepository.findAll();
	}

	/**
	 * Gets an admin from the database by username
	 * 
	 * @param username
	 * @return
	 */
	public Admin getAdminByUsername(String username) {
		return adminRepository.getAdminByUsername(username).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "An admin with given username was not found"));
	}

}
