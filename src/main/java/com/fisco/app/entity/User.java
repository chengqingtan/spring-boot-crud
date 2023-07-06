package com.fisco.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private String private_key;
    private String public_key;
    private String address;
    private String role;
}
