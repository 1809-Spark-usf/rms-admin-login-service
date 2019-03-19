package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Admin;
import com.revature.repositories.AdminRepository;

/**
 * Service to manage connections to the admin table in the database
 * @author Jaron | 1811-Java-Nick | 1/4/2019
 *
 */
@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;
	
	/**
	 * Saves an admin to the database
	 * @param admin
	 */
	public void createAdmin(Admin admin) {
		adminRepository.save(admin);
	}
	
	/**
	 * Gets an admin from the databse by id
	 * @param id
	 * @return Admin
	 */
	public Admin getAdmin(Integer id) {
		return adminRepository.findById(id).get();
	}
	
	/**
	 * Gets all admins from the database
	 * @return List of admins
	 */
	public List<Admin> getAll() {
		return adminRepository.findAll();
	}
	
	/**
	 * Gets an admin from the database by username
	 * @param username
	 * @return
	 */
	public Admin getAdminByUsername(String username) {
		Optional<Admin> result = adminRepository.getAdminByUsername(username);
		return result.get(); 
	}
	
}
