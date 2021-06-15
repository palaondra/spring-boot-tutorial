package com.pala.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pala.springboot.data.User;
import com.pala.springboot.exception.UserExistsException;
import com.pala.springboot.exception.UserNotFoundException;
import com.pala.springboot.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public void createUser(User user) throws UserExistsException {
		User findingUser = userRepository.findByUsername(user.getUsername());
		if (!ObjectUtils.isEmpty(findingUser)) {
			throw new UserExistsException("User with " + user.getUsername() + " already exists");
		}
		
		
		userRepository.save(user);
	}
	
	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User with " + id + " not found");
		} else {
			return optionalUser;
		}
	}
	
	public User updateUserById(Long id, User user) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User with " + id + " not found");
		}
		
		user.setId(id);
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id) throws UserNotFoundException {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		} else {
			throw new UserNotFoundException("User with " + id + " not found");
		}
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}
