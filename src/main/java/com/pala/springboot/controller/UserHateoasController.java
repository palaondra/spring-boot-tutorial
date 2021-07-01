package com.pala.springboot.controller;

import java.util.List;
import java.util.Optional;

import com.pala.springboot.data.User;
import com.pala.springboot.data.Order;
import com.pala.springboot.exception.UserNotFoundException;
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
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
	public EntityModel<User> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
		Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.get();
        Long userId = user.getUserId();
        
        Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
        EntityModel<User> response = new EntityModel<User>(user, selfLink);
        return response;
	}

    @GetMapping
	public CollectionModel<User> getAllUsers() throws UserNotFoundException {
		List<User> userList = userService.getAllUsers();
        
        // we will build serf links
        for (User user : userList) {
            Long userId = user.getUserId();
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);

            // link to user's orders
            CollectionModel<Order> userOrders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrdersForUser(userId);
            Link usersOrderLink = WebMvcLinkBuilder.linkTo(userOrders).withRel("all-orders");
            user.add(usersOrderLink);
        }
        
        // link to get all users method
        Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllUsers()).withSelfRel();

        CollectionModel<User> response = CollectionModel.of(userList, link);
        return response;
	}

}
