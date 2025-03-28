package com.tutorial.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ChatGPTException extends RuntimeException {
    public ChatGPTException(String message) {
        super(message);
    }
}
