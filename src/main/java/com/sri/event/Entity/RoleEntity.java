package com.sri.event.Entity;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="roles")
public class RoleEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false,length=20)
	private String name;
	
	@ManyToMany(mappedBy="roles")
	private Collection<UserEntity> users;
	
	@ManyToMany(mappedBy="roles")
	private Collection<OwnerEntity> owners;
	
	@ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
	@JoinTable(name="roles_authorities",
				joinColumns=@JoinColumn(name="roles_id",referencedColumnName="id"),
				inverseJoinColumns=@JoinColumn(name="authorities_id",referencedColumnName="id"))
	private Collection<AuthoritiesEntity> authorities;
	
	public RoleEntity() {
		
	}
	public RoleEntity(String name) {
		super();
		this.name=name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserEntity> users) {
		this.users = users;
	}

	public Collection<OwnerEntity> getOwners() {
		return owners;
	}
	public void setOwners(Collection<OwnerEntity> owners) {
		this.owners = owners;
	}
	public Collection<AuthoritiesEntity> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<AuthoritiesEntity> authorities) {
		this.authorities = authorities;
	}
	
	
	
	
}
