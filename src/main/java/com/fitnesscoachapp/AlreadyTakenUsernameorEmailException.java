package com.fitnesscoachapp;

public class AlreadyTakenUsernameorEmailException extends RuntimeException {
    public AlreadyTakenUsernameorEmailException(String message) {
        super(message);
    }
}
