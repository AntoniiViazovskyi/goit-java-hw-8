package com.goit.exception;

public class PropertiesUtilException extends RuntimeException{

    public PropertiesUtilException(Throwable throwable){
        super(throwable);
    }

    public PropertiesUtilException(String message) {super(message);}
}
