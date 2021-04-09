package com.example.errs;

public class EmptySearchException extends Exception {
    public EmptySearchException(String message) {
        super(message);
    }
}
