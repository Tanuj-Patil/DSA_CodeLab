package com.CodeLab.Central_Service.exception;

public class WrongAnswerException extends RuntimeException {
    public WrongAnswerException(String message) {
        super(message);
    }
}
