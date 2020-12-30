package com.demo.entity.dto;

import com.demo.entity.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
public class UserPageDto {
    private Collection<UserDto> users;
    private long totalElements;

    public UserPageDto(Page<User> page) {
        if (page.hasContent()) {
            this.users = page.getContent().stream().map(UserDto::new).collect(Collectors.toList());
            this.totalElements = page.getTotalElements();
        } else {
            this.users = Collections.emptyList();
            this.totalElements = page.getTotalElements();
        }

    }

}
