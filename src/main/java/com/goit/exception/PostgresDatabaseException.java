package com.goit.exception;

public class PostgresDatabaseException extends RuntimeException {

    public PostgresDatabaseException(Throwable throwable) {
        super(throwable);
    }

    public PostgresDatabaseException(String message) {
        super(message);
    }
}
