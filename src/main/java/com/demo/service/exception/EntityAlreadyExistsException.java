package com.demo.service.exception;

public class EntityAlreadyExistsException extends ServiceException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
