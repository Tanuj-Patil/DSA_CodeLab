package com.CodeLab.Central_Service.exception;

public class AdminEmailAlreadyPresentException extends RuntimeException {
    public AdminEmailAlreadyPresentException(String message) {
        super(message);
    }
}
