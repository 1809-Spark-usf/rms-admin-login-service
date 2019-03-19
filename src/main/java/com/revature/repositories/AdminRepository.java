package com.revature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	public Optional<Admin> getAdminByUsername(String username);
}
