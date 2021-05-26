package com.example.errors;

public class BookstoreApiWrongParameterException extends Exception {
    public BookstoreApiWrongParameterException(String message) {
        super(message);
    }
}

