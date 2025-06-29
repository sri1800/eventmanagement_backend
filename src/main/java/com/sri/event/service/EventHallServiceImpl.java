package com.sri.event.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.event.Entity.EventHallEntity;
import com.sri.event.pojo.EventHallDto;
import com.sri.event.repo.EventHallRepo;

@Service
public class EventHallServiceImpl implements EventHallService{

	   @Autowired
	    private ModelMapper mapper;
	    
	    @Autowired
	    private EventHallRepo eventHallRepo;
	
	@Override
	public EventHallDto createEventHall(EventHallDto eventHallDto) {
		eventHallDto.setHallId(UUID.randomUUID().toString());
		
		EventHallEntity eventHallEntity=mapper.map(eventHallDto,EventHallEntity.class);
		
		eventHallRepo.save(eventHallEntity);
		
		EventHallDto ReturnValue=mapper.map(eventHallEntity,EventHallDto.class);
		return ReturnValue;
	}

	@Override
	public EventHallDto getEventHallById(String hallId) {
		Optional<EventHallEntity> eventHallEntity=eventHallRepo.findByHallId(hallId);
		EventHallDto ReturnValue=mapper.map(eventHallEntity,EventHallDto.class);
		return ReturnValue;
	}

	@Override
	public List<EventHallDto> getAllEventHalls() {
		List<EventHallEntity> eventHallEntities=eventHallRepo.findAll();
		return eventHallEntities.stream().map(hall->mapper.map(hall,EventHallDto.class)).collect(Collectors.toList());
	}

	@Override
	public Boolean deleteEventHall(String hallId) {
		Optional<EventHallEntity> optionalEntity = eventHallRepo.findByHallId(hallId);
	    if (optionalEntity.isPresent()) {
	        eventHallRepo.delete(optionalEntity.get());
	        return true;
	    } else {
	        return false;
	    }
	}

	@Override
	public Boolean bookEventHall(String hallId) 
	{
		Optional<EventHallEntity> hallOptional = eventHallRepo.findByHallId(hallId);
	    if (hallOptional.isPresent()) 
	    {
	        EventHallEntity hall = hallOptional.get();
	        if (!hall.isBooked()) 
	        {
	            hall.setBooked(true); 
	            eventHallRepo.save(hall);
	            return true;
	        } else 
	        {
	            return false;
	        }
	    }else {
	    	return false;
	    }
	}

	



}
