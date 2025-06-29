package com.sri.event.service;

import java.util.List;

import com.sri.event.pojo.EventHallDto;


public interface EventHallService {

	EventHallDto createEventHall(EventHallDto eventHallDto);

	EventHallDto getEventHallById(String eventId);

	List<EventHallDto> getAllEventHalls();

	Boolean deleteEventHall(String hallId);
	
	Boolean bookEventHall(String hallId);
}

