package com.pala.springboot.controller;

import java.util.List;
import java.util.Optional;

import com.pala.springboot.data.Order;
import com.pala.springboot.data.User;
import com.pala.springboot.exception.UserNotFoundException;
import com.pala.springboot.repository.OrderRepository;
import com.pala.springboot.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/users")
public class OrderController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userId}/orders")
    public List<Order> getAllOrdersForUser(@PathVariable("userId") Long userId) throws UserNotFoundException {
        // check if exists incoming user
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with " + userId + " not found");
        }


        return userOptional.get().getOrders();
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<Void> createOrder(@PathVariable("userId") Long userId, @RequestBody Order order, UriComponentsBuilder uriComponentsBuilder) throws UserNotFoundException {
        // check if exists incoming user
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with " + userId + " not found");
        }

        User user = userOptional.get();

        // set order's user
        order.setUser(user);

        // save
        Order createdOrder = orderRepository.save(order);

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				uriComponentsBuilder
					.path("/users/{userId}/orders")
					.buildAndExpand(user.getId())
					.toUri()
				);
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

}
