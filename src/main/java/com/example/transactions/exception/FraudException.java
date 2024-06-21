package com.example.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FraudException extends RuntimeException{
    public FraudException(String message) {
        super(message);
    }

}
