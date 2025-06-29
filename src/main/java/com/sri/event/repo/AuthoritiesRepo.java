package com.sri.event.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.event.Entity.AuthoritiesEntity;


public interface AuthoritiesRepo extends JpaRepository<AuthoritiesEntity,Long>{
	AuthoritiesEntity findByName(String name);
}
