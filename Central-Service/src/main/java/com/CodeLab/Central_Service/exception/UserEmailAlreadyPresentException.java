package com.CodeLab.Central_Service.exception;

public class UserEmailAlreadyPresentException extends RuntimeException {
    public UserEmailAlreadyPresentException(String message) {
        super(message);
    }
}
