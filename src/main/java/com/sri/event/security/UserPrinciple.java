package com.sri.event.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sri.event.Entity.AuthoritiesEntity;
import com.sri.event.Entity.RoleEntity;
import com.sri.event.Entity.UserEntity;

public class UserPrinciple implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private UserEntity userEntity;
	
	public UserPrinciple(UserEntity userEntity) {
		super();
		this.userEntity = userEntity;
		this.userId=this.userEntity.getUserId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities=new HashSet<>();
		
		Collection<RoleEntity> roles=userEntity.getRoles();
		Collection<AuthoritiesEntity>authorities=new HashSet<>();
		
		
		if(roles==null)return grantedAuthorities;
		roles.forEach((role)->
		{grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		authorities.addAll(role.getAuthorities());
		});
		
		authorities.forEach((authority)->
		{
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		});
		return grantedAuthorities;
	}

	
	@Override
	public String getPassword() {
		return userEntity.getEncrypassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
