package com.pala.springboot.controller;

import java.util.Optional;

import com.pala.springboot.data.User;
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
@RequestMapping("/modelmapper/users")
public class UserModelMapperController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
	public UserModelMapperDto getUserByUserId(@PathVariable("id") Long id) throws UserNotFoundException {
		Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();

        // source and target fields must has the same names
        UserModelMapperDto userModelMapperDto = modelMapper.map(user, UserModelMapperDto.class);
        return userModelMapperDto;
	}

}
