package com.example.punchingSystem.exception;

public class InvalidLineFormatException extends RuntimeException{
    public InvalidLineFormatException(String message){
        super(message);
    }
}