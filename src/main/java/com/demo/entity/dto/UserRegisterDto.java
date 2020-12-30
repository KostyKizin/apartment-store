package com.demo.entity.dto;

import com.demo.entity.UserRole;
import lombok.Data;

@Data
public class UserRegisterDto {
    private Credentials credentials;
    private String name;
    private String sureName;
    private UserRole userRole;
    private String telephoneNumber;

}
