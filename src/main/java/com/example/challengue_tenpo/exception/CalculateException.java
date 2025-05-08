package com.example.challengue_tenpo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CalculateException extends RuntimeException {

    public CalculateException(String message) {
        super(message);
    }

    public CalculateException(String message, Throwable cause) {
        super(message, cause);
}

}