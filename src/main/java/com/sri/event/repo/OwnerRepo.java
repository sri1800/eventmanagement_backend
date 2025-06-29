package com.sri.event.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sri.event.Entity.OwnerEntity;

@Repository
public interface OwnerRepo extends JpaRepository<OwnerEntity, Long> {
	
	Optional<OwnerEntity> findById(Long Id);
	OwnerEntity findByEmail(String email);
	void deleteById(Long Id);
	OwnerEntity findByOwnerId(String ownerId);

}
