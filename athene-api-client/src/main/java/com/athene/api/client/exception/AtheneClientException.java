package com.athene.api.client.exception;

/**
 * Created by fe on 16/9/18.
 */
public class AtheneClientException extends RuntimeException {

    public AtheneClientException() {
        super();
    }

    public AtheneClientException(String message) {
        super(message);
    }

    public AtheneClientException(Throwable exception) {
        super(exception);
    }

    public AtheneClientException(String message, Throwable exception) {
        super(message, exception);
    }
}
