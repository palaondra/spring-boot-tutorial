package com.pala.springboot.dto;

import java.util.List;
import com.pala.springboot.data.Order;

public class UsedDtoV2 {
    
    private Long userId;
	
	private String username;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private String role;
	
	private String ssn;
	
	private List<Order> orders;

    private String address;

    public UsedDtoV2() {

    }

    public UsedDtoV2(Long userId, String username, String firstname, String lastname, String email, String role,
            String ssn, List<Order> orders, String address) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.ssn = ssn;
        this.orders = orders;
        this.address = address;
    }

    


}
