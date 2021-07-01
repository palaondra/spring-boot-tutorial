package com.pala.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pala.springboot.data.User;
import com.pala.springboot.exception.UserExistsException;
import com.pala.springboot.exception.UserNotFoundException;
import com.pala.springboot.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) throws UserExistsException {
		userService.createUser(user);
			
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				uriComponentsBuilder
					.path("/users/{id}")
					.buildAndExpand(user.getUserId())
					.toUri()
				);
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
		return userService.getUserById(id);
	}
	
	@PutMapping("/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) throws UserNotFoundException {
		return userService.updateUserById(id, user);
	}
	
	@DeleteMapping("/{id}")
	public void deleteByUserId(@PathVariable("id") Long id) throws UserNotFoundException {
		userService.deleteUserById(id);
	}
	
	@GetMapping("/by-username/{username}")
	public User getUserByUsername(@PathVariable("username") String username) throws UserNotFoundException {
		return userService.getUserByUsername(username);
	}
}
