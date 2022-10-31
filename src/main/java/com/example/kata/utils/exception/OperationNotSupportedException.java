package com.example.kata.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad operation")
public class OperationNotSupportedException extends RuntimeException {

    public OperationNotSupportedException(String message) {
        super(message);
    }
}
