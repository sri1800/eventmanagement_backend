package com.sri.event.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.event.Entity.RoleEntity;

public interface RoleRepo extends JpaRepository<RoleEntity,Long>{

	RoleEntity findByName(String name);

}
