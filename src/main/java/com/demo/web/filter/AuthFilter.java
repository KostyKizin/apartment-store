package com.demo.web.filter;

import com.demo.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPublicRequest(request) || isUserAuthorized(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(getMessage());
        }

    }


    private String getMessage() {
        try {
            return mapper.writeValueAsString(new ResponseEntity<>("user unauthorized", HttpStatus.UNAUTHORIZED));
        } catch (Exception e) {
            return "";
        }
    }


    private boolean isUserAuthorized(HttpServletRequest request) {
        return this.getUser(request) != null;
    }


    private boolean isPublicRequest(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        return requestURI.startsWith("/swagger")
                || requestURI.startsWith("/webjars")
                || requestURI.startsWith("/v2/api-docs")
                || requestURI.startsWith("/csrf")
                || requestURI.startsWith("/user/login")
                || requestURI.startsWith("/user/logout")
                || requestURI.startsWith("/user/register")
                || requestURI.equals("/");
    }


    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object user = session.getAttribute("user");
            if (user != null) {
                return (User) user;
            }
        }
        return null;
    }
}
