package com.edunest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the client sends invalid or conflicting data.
 * Automatically maps to HTTP 400 Bad Request.
 *
 * Usage:
 *   throw new BadRequestException("Username is already taken");
 *   throw new BadRequestException("Passwords do not match");
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}