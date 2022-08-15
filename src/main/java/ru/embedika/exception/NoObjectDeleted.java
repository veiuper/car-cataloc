package ru.embedika.exception;

public class NoObjectDeleted extends RuntimeException{
    public NoObjectDeleted(String message) {
        super(message);
    }
}
