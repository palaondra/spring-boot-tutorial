package com.pala.springboot.controller;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.pala.springboot.data.User;
import com.pala.springboot.data.Views;
import com.pala.springboot.exception.UserNotFoundException;
import com.pala.springboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jsonview/users")
@Validated
public class UserJsonViewController {
   
    @Autowired
    private UserService userService;

    @GetMapping("/external/{id}")
    @JsonView(Views.External.class)
	public Optional<User> getUserByIdExternal(@PathVariable("id") Long id) throws UserNotFoundException {
		return userService.getUserById(id);
	}

    @GetMapping("/internal/{id}")
    @JsonView(Views.Internal.class)
	public Optional<User> getUserByInternal(@PathVariable("id") Long id) throws UserNotFoundException {
		return userService.getUserById(id);
	}

}
