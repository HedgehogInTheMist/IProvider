package com.epam.inet.provider.service.exception;

/**
 * Created by Hedgehog on 24.05.2016.
 */
public class AuthenticationServiceException extends Exception {

    public AuthenticationServiceException() {
        super();
    }

    public AuthenticationServiceException(String message) {
        super(message);
    }

    public AuthenticationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationServiceException(Throwable cause) {
        super(cause);
    }
}
