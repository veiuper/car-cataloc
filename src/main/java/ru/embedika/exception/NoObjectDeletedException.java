package ru.embedika.exception;

public class NoObjectDeletedException extends RuntimeException{
    public NoObjectDeletedException(String message) {
        super(message);
    }
}
