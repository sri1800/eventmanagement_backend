package com.sri.event.Entity;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Users")
public class UserEntity {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false,length=100)
	private String userId;
	
	@Column(nullable=false,length=100)
	private String fname;
	
	@Column(nullable=false,length=100)
	private String lname;
	
	@Column(nullable=false,length=100,unique=true)
	private String email;
	
	@Column(nullable=false,unique=true)
	private String encrypassword;
	
	@ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
	@JoinTable(name="users_roles",
				joinColumns=@JoinColumn(name="users_id",referencedColumnName="id"),
				inverseJoinColumns=@JoinColumn(name="roles_id",referencedColumnName="id"))
	
	private Collection<RoleEntity> roles;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Collection<RoleEntity> roles) {
		this.roles = roles;
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
