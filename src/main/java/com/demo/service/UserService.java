package com.demo.service;

import com.demo.entity.User;
import com.demo.entity.dto.Credentials;
import com.demo.entity.dto.UserRegisterDto;
import com.demo.repository.UserRepository;
import com.demo.service.exception.EntityAlreadyExistsException;
import com.demo.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User register(UserRegisterDto dto) {
        final Credentials credentials = dto.getCredentials();
        userRepository.findByEmail(credentials.getEmail()).ifPresent(user -> {
            throw new EntityAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        });
        return userRepository.save(registerNew(dto));

    }

    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, encryptPassword(password));
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Can't find user by email and password");
        }
        return user.get();
    }


    private String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] encrypted = digest.digest(password.getBytes());
            return new String(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt password!");
        }
    }


    private User registerNew(UserRegisterDto dto) {
        final Credentials credentials = dto.getCredentials();
        return User.builder()
                .email(credentials.getEmail())
                .name(dto.getName())
                .surname(dto.getSureName())
                .password(encryptPassword(credentials.getPassword()))
                .role(dto.getUserRole())
                .telephone(dto.getTelephoneNumber())
                .build();
    }
}
