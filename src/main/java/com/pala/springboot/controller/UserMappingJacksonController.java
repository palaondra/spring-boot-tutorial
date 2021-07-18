package com.pala.springboot.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pala.springboot.data.User;
import com.pala.springboot.exception.UserNotFoundException;
import com.pala.springboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jacksonfilter/users")
@Validated
public class UserMappingJacksonController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
	public MappingJacksonValue getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
	    Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();
        
        Set<String> properties = new HashSet<String>();
        properties.add("userid");
        properties.add("ssn");
        properties.add("username");
        properties.add("orders");

        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(properties));
        MappingJacksonValue mapper = new MappingJacksonValue(user);
        mapper.setFilters(filterProvider);

        return mapper;
	}

    @GetMapping("/params/{id}")
	public MappingJacksonValue getUserById2(@PathVariable("id") Long id, @RequestParam Set<String> params) throws UserNotFoundException {
	    Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();
        
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(params));
        MappingJacksonValue mapper = new MappingJacksonValue(user);
        mapper.setFilters(filterProvider);

        return mapper;
	}

}
