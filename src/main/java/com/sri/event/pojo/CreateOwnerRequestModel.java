package com.sri.event.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateOwnerRequestModel {

	@NotNull(message="firstname cannot be null")
	@Size(min=2,message="Enter more than 2 characters")
	private String fname;
	
	@NotNull(message="lastname cannot be null")
	@Size(min=2,message="Enter more than 2 characters")
	private String lname;
	
	@NotNull(message="password cannot be null")
	@Size(min=6,max =12,message="Enter 6-12 characters")
	private String password;
	
	@NotNull(message="password cannot be null")
	@Email
	private String email;
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

