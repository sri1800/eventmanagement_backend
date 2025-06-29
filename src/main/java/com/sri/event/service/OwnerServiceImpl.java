package com.sri.event.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sri.event.Entity.OwnerEntity;
import com.sri.event.Entity.RoleEntity;
import com.sri.event.pojo.OwnerDto;
import com.sri.event.repo.OwnerRepo;
import com.sri.event.repo.RoleRepo;
import com.sri.event.security.OwnerPrinciple;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepo ownerRepo;
    
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public OwnerDto createOwner(OwnerDto ownerDto) {
        OwnerEntity entity = mapper.map(ownerDto, OwnerEntity.class);
        entity.setOwnerId(UUID.randomUUID().toString());
        entity.setEncrypassword(passwordEncoder.encode(ownerDto.getPassword()));
        Collection<RoleEntity> role=new HashSet<RoleEntity>();
        for(String roles:ownerDto.getRoles()) {
        	RoleEntity roleEntity=roleRepo.findByName(roles);
        	if(roleEntity!=null) {
        		role.add(roleEntity);
        	}
        }
        entity.setRoles(role);
        OwnerEntity savedEntity = ownerRepo.save(entity);
        return mapper.map(savedEntity, OwnerDto.class);
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        List<OwnerEntity> owners = ownerRepo.findAll();
        return owners.stream()
                .map(owner -> mapper.map(owner, OwnerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OwnerDto deleteOwner(Long Id) {
    	Optional<OwnerEntity> owner=ownerRepo.findById(Id);
			ownerRepo.deleteById(Id);
			return mapper.map(owner,OwnerDto.class);
	
    }

	@Override
	public UserDetails loadUserByUsername(String ownerEmail) throws UsernameNotFoundException {
		OwnerEntity entity=ownerRepo.findByEmail(ownerEmail);
		if(entity==null)throw new UsernameNotFoundException("Owner not found");
		return new OwnerPrinciple(entity);
	}

	@Override
	public OwnerDto getOwnerDetailsByEmail(String ownerEmail) throws UsernameNotFoundException{
		OwnerEntity entity=ownerRepo.findByEmail(ownerEmail);
		if(entity==null)throw new UsernameNotFoundException("Owner not found");
		return mapper.map(entity,OwnerDto.class);
	}

}
