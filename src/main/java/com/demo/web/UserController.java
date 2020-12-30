package com.demo.web;

import com.demo.entity.User;
import com.demo.entity.dto.Credentials;
import com.demo.entity.dto.UserDto;
import com.demo.entity.dto.UserRegisterDto;
import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping("/user/register")
    public UserDto register(@RequestBody UserRegisterDto registerDto, HttpServletRequest request) {
        User user = userService.register(registerDto);
        updateSession(user, request);
        return new UserDto(user);
    }


    @PostMapping("/user/login")
    public UserDto login(@RequestBody Credentials credentials, HttpServletRequest request) {
        User user = userService.login(credentials.getEmail(), credentials.getPassword());
        this.updateSession(user, request);
        return new UserDto(user);
    }

    @PostMapping("/user/logout")
    public HttpStatus logout(HttpServletRequest request) {
        invalidateSession(request);
        return HttpStatus.OK;
    }


    @GetMapping("/user/currentUser")
    public UserDto status(@SessionAttribute(name = "user") User user) {
        return new UserDto(user);
    }

    private void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    private void updateSession(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30 * 60);
    }


}
