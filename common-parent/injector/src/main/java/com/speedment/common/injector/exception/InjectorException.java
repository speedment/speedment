package com.speedment.common.injector.exception;

public class InjectorException extends RuntimeException {

    private static final long serialVersionUID = -92364629276729266L;

    public InjectorException() {}

    public InjectorException(String message) { super(message); }

    public InjectorException(String message, Throwable cause) { super(message, cause); }

    public InjectorException(Throwable cause) { super(cause); }
}
