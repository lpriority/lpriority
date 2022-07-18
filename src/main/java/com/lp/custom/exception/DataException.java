package com.lp.custom.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class DataException extends RuntimeException {
    public DataException() {
        super();
    }
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataException(String message) {
        super(message);
    }
    public DataException(Throwable cause) {
        super(cause);
    }
}