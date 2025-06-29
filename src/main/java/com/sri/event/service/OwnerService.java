package com.sri.event.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sri.event.pojo.OwnerDto;

public interface OwnerService extends UserDetailsService{
    OwnerDto createOwner(OwnerDto ownerDto);
    List<OwnerDto> getAllOwners();
    OwnerDto deleteOwner(Long Id);
	OwnerDto getOwnerDetailsByEmail(String ownerEmail);
}
