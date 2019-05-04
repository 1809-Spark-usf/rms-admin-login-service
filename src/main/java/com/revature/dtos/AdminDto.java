package com.revature.dtos;

import java.util.List;

import com.revature.models.Role;

public class AdminDto {

	private int adminId;

	List<Role> roles;

	private String firstname;

	private String lastname;

	private String username;

	private String password;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public AdminDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminDto(int adminId, String firstname, String lastname, String username, String password, List<Role> roles) {
		super();
		this.adminId = adminId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
		return "AdminDto [adminId=" + adminId + ", roles=" + roles + ", firstname=" + firstname + ", lastname="
				+ lastname + ", username=" + username + ", password=" + password + "]";
	}
	
}
