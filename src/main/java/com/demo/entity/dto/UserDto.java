package com.demo.entity.dto;

import com.demo.entity.User;
import com.demo.entity.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String sureName;
    private UserRole userRole;
    private String email;
    private String telephoneNumber;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.sureName = user.getSurname();
        this.userRole = user.getRole();
        this.email = user.getEmail();
        this.telephoneNumber = user.getTelephone();
    }
}
