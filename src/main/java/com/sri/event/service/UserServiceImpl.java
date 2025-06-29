package com.sri.event.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sri.event.Entity.RoleEntity;
import com.sri.event.Entity.UserEntity;
import com.sri.event.Exception.EmailAlreadyExistException;
import com.sri.event.pojo.UserDto;
import com.sri.event.repo.RoleRepo;
import com.sri.event.repo.UserRepo;
import com.sri.event.security.UserPrinciple;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) throws EmailAlreadyExistException {
		
		UserEntity checkEmail = userRepo.findByEmail(userDto.getEmail());
		if(checkEmail != null)
			throw new EmailAlreadyExistException("User with email "+ userDto.getEmail() + " already exist, please use another email");
		
		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncrypassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		
		UserEntity entity=mapper.map(userDto,UserEntity.class); 
		
		Collection<RoleEntity> rolesEntities=new HashSet<>();
		for(String role:userDto.getRoles()) {
			RoleEntity roleEntity=roleRepo.findByName(role);
			rolesEntities.add(roleEntity);
		}
		entity.setRoles(rolesEntities);
		
		userRepo.save(entity);
		UserDto ReturnValue=mapper.map(entity,UserDto.class);
		
		return ReturnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity entity=userRepo.findByEmail(email);
		if(entity==null)throw new UsernameNotFoundException("User not found");
		return new UserPrinciple(entity);
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) throws UsernameNotFoundException{
		UserEntity entity=userRepo.findByEmail(email);
		if(entity==null)throw new UsernameNotFoundException("User not found");
		return mapper.map(entity,UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity entity=userRepo.findByUserId(userId);
		return mapper.map(entity,UserDto.class);
	}

}
