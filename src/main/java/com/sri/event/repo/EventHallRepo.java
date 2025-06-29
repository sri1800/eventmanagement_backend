package com.sri.event.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.event.Entity.EventHallEntity;

public interface EventHallRepo extends JpaRepository<EventHallEntity, Long>{

	Optional<EventHallEntity> findByHallId(String hallId);

	

	
}
