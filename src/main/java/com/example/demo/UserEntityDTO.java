package com.example.demo;

import lombok.Data;

@Data
public class UserEntityDTO {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String line;
    private String role;
}
