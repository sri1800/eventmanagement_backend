package com.sri.event.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sri.event.Entity.OwnerEntity;
import com.sri.event.Entity.RoleEntity;
import com.sri.event.Entity.AuthoritiesEntity;

public class OwnerPrinciple implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String ownerId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    private OwnerEntity ownerEntity;

    public OwnerPrinciple(OwnerEntity ownerEntity) {
        super();
        this.ownerEntity = ownerEntity;
        this.ownerId = this.ownerEntity.getOwnerId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Collection<RoleEntity> roles = ownerEntity.getRoles();
        Collection<AuthoritiesEntity> authorities = new HashSet<>();

        if (roles == null) return grantedAuthorities;

        roles.forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.addAll(role.getAuthorities());
        });

        authorities.forEach(authority -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        });

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return ownerEntity.getEncrypassword();
    }

    @Override
    public String getUsername() {
        return ownerEntity.getEmail();
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
