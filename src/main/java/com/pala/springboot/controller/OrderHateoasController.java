package com.pala.springboot.controller;

import java.util.List;
import java.util.Optional;

import com.pala.springboot.data.Order;
import com.pala.springboot.data.User;
import com.pala.springboot.exception.UserNotFoundException;
import com.pala.springboot.repository.OrderRepository;
import com.pala.springboot.repository.UserRepository;
import com.pala.springboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/hateoas/orders")
@Validated
public class OrderHateoasController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}/orders")
    public CollectionModel<Order> getAllOrdersForUser(@PathVariable("userId") Long userId) throws UserNotFoundException {
        // check if exists incoming user
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with " + userId + " not found");
        }

        List<Order> orderList = userOptional.get().getOrders();

        CollectionModel<Order> response = CollectionModel.of(orderList);
        return response;
    }

}
