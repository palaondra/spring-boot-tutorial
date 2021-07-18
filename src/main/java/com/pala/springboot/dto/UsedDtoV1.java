package com.pala.springboot.dto;

import java.util.List;
import com.pala.springboot.data.Order;

public class UsedDtoV1 {
    
	private Long userId;
	
	private String username;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private String role;
	
	private String ssn;
	
	private List<Order> orders;

    public UsedDtoV1() {

    }

    public UsedDtoV1(Long userId, String username, String firstname, String lastname, String email, String role,
            String ssn, List<Order> orders) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.ssn = ssn;
        this.orders = orders;
    }

}
