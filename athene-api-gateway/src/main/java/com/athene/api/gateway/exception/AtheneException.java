package com.athene.api.gateway.exception;

/**
 * Created by fe on 16/9/9.
 */
public class AtheneException extends Exception {


    private static final long serialVersionUID = 1L;

    public AtheneException() {

    }

    public AtheneException(String message) {
        super(message);
    }

    public AtheneException(Exception e) {
        super(e);
    }
}
