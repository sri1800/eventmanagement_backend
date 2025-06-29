package com.sri.event.pojo;

import java.io.Serializable;
import java.util.Collection;

public class UserDto implements Serializable{
	
	private static final long serialVersionUID= 1L;
	
	private String userId;
	private String fname;
	private String lname;
	private String email;
	private String password;
	private String encrypassword;
	private Collection<String> roles;
	
	public Collection<String> getRoles() {
		return roles;
	}
	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEncrypassword() {
		return encrypassword;
	}
	public void setEncrypassword(String encrypassword) {
		this.encrypassword = encrypassword;
	}
	
	

}
