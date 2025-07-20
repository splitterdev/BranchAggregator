package net.example.demo.rest.bean.error;

import net.example.demo.rest.util.exception.RestException;

import java.io.Serializable;

public class ErrorBean implements Serializable {

    public ErrorBean(RestException restException) {
        this.status = restException.getErrorCode();
        this.message = restException.getMessage();
    }

    private final String status;
    private final String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
