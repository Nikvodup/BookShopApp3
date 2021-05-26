package com.example.errors;

public class EmptySearchException extends Exception {
    public EmptySearchException(String message) {
        super(message);
    }
}
