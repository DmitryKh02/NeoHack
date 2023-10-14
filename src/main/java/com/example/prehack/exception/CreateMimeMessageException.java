package com.example.prehack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateMimeMessageException extends RuntimeException {
    public CreateMimeMessageException(String massage) {
        super(massage);
    }
}
