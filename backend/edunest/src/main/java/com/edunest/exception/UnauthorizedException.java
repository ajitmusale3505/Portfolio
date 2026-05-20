package com.edunest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a user tries to access a resource they don't have permission for.
 * Automatically maps to HTTP 403 Forbidden.
 *
 * Usage:
 *   throw new UnauthorizedException("You are not allowed to delete another user's account");
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}