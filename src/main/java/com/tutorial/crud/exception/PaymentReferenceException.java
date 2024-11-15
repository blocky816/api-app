package com.tutorial.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class PaymentReferenceException extends RuntimeException {
    public PaymentReferenceException(String message) {
        super(message);
    }
}
