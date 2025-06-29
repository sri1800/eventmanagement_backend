package com.sri.event.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sri.event.pojo.CreateEventHallRequestModel;
import com.sri.event.pojo.CreateEventHallResponseModel;
import com.sri.event.pojo.EventHallDto;
import com.sri.event.service.EventHallService;


@RestController
@RequestMapping("/events")
public class EventHallController {

    @Autowired
    private EventHallService eventHallService;

    @Autowired
    private ModelMapper mapper;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = "/create",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<CreateEventHallResponseModel> createEventHall(
            @Validated @RequestBody CreateEventHallRequestModel eventHallRequestModel) {

        EventHallDto eventHallDto = mapper.map(eventHallRequestModel, EventHallDto.class);
        EventHallDto createdEventHall = eventHallService.createEventHall(eventHallDto);

        CreateEventHallResponseModel response = mapper.map(createdEventHall, CreateEventHallResponseModel.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping(value = "/{hallId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateEventHallResponseModel> getEventHallById(@PathVariable("hallId") String hallId) {
        EventHallDto eventDetails = eventHallService.getEventHallById(hallId);
        CreateEventHallResponseModel response = mapper.map(eventDetails, CreateEventHallResponseModel.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CreateEventHallResponseModel>> getAllEventHalls() {
        List<EventHallDto> allEvents = eventHallService.getAllEventHalls();
        List<CreateEventHallResponseModel> response = allEvents.stream()
                .map(event -> mapper.map(event, CreateEventHallResponseModel.class))
                .toList();

        return ResponseEntity.ok().body(response);
    }


    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping(value = "/{hallId}")
    public ResponseEntity<String> deleteEvent(@PathVariable String hallId) {
        Boolean isDeleted=eventHallService.deleteEventHall(hallId);
        if(isDeleted) {
        return ResponseEntity.ok("Event deleted successfully");}
        else {
        	return ResponseEntity.ok("Event Deletion failed");
        }
    }
    
    @PostMapping("/book/{hallId}")
    public ResponseEntity<String> bookEventHall(@PathVariable String hallId) {
    	Boolean isBooked =eventHallService.bookEventHall(hallId);
    	if(isBooked) {
            return ResponseEntity.ok("Event Booked successfully");}
            else {
            	return ResponseEntity.ok("Event Booking failed");
            }
    }

}



