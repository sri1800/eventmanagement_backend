package com.sri.event.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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

import com.sri.event.pojo.CreateOwnerRequestModel;
import com.sri.event.pojo.CreateOwnerResponseModel;
import com.sri.event.pojo.OwnerDto;
import com.sri.event.pojo.OwnerResponseModel;
import com.sri.event.service.OwnerService;
import com.sri.event.shared.Role;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ModelMapper mapper;

    // ✅ Only ADMIN can create a new owner
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateOwnerResponseModel> createOwner(@Validated @RequestBody CreateOwnerRequestModel requestModel) {
        OwnerDto ownerDto = mapper.map(requestModel, OwnerDto.class);
        ownerDto.setRoles(new HashSet<>(Arrays.asList(Role.ROLE_OWNER.name())));
        OwnerDto createdOwner = ownerService.createOwner(ownerDto);
        CreateOwnerResponseModel response = mapper.map(createdOwner, CreateOwnerResponseModel.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<OwnerResponseModel>> getAllOwners() {
        List<OwnerDto> owners = ownerService.getAllOwners();
        List<OwnerResponseModel> responseList = owners.stream()
                .map(owner -> mapper.map(owner, OwnerResponseModel.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseList);
    }

    // ✅ Only ADMIN can delete any owner
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OwnerResponseModel> deleteOwner(@PathVariable("id") Long Id) {
        OwnerDto deleteowner=ownerService.deleteOwner(Id);
        OwnerResponseModel response=mapper.map(deleteowner,OwnerResponseModel.class);
        return ResponseEntity.ok().body(response);
    }
}

