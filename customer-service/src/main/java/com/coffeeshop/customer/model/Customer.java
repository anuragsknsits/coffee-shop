package com.coffeeshop.customer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private Long id;

    private String name;
    private String email;
    private String mobileNumber;
    private String address;
}
