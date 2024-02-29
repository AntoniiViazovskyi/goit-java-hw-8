package com.goit.exception;

public class ClientDaoException extends RuntimeException {

    public ClientDaoException(Throwable throwable) {
        super(throwable);
    }

    public ClientDaoException(String message) {
        super(message);
    }
}
