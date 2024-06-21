package com.example.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LuhnException extends RuntimeException{

    public LuhnException(String message) {
        super(message);
    }

}
