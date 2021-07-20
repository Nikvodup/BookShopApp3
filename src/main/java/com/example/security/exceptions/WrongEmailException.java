package com.example.security.exceptions;

public class WrongEmailException extends Exception {
    public WrongEmailException(String message) {
        super(message);
    }
}
