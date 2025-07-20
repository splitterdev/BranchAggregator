package net.example.demo.rest.util.exception;

import java.io.IOException;

public class RestException extends IOException {

    public RestException(String errorCode, String cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    private final String errorCode;

    public String getErrorCode() {
        return errorCode;
    }
}
