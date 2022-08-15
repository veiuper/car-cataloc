package ru.embedika.exception;

public class NoObjectCreatedException extends RuntimeException{
    public NoObjectCreatedException(String message) {
        super(message);
    }
}
