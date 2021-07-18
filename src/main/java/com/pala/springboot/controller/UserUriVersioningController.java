package com.pala.springboot.controller;

import java.util.Optional;

import com.pala.springboot.data.User;
import com.pala.springboot.dto.UsedDtoV1;
import com.pala.springboot.dto.UsedDtoV2;
import com.pala.springboot.dto.UserModelMapperDto;
import com.pala.springboot.exception.UserNotFoundException;
import com.pala.springboot.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/versioning/users")
public class UserUriVersioningController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping({"/v1.0/{id}", "/v1.1/{id}"})
	public UsedDtoV1 getUserByUserId(@PathVariable("id") Long id) throws UserNotFoundException {
		Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();

        // source and target fields must has the same names
        UsedDtoV1 userDto = modelMapper.map(user, UsedDtoV1.class);
        return userDto;
	}

    @GetMapping({"/v2.0/{id}", "/v2.1/{id}"})
	public UsedDtoV2 getUserByUserId2(@PathVariable("id") Long id) throws UserNotFoundException {
		Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();

        // source and target fields must has the same names
        UsedDtoV2 userDto = modelMapper.map(user, UsedDtoV2.class);
        return userDto;
	}

}
