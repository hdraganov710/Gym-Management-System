package com.fitnesscoachapp;

public enum GENDER {
    MALE, FEMALE;

    public static GENDER fromInt(int choice) {
        return switch (choice) {
            case 1 -> MALE;
            case 2 -> FEMALE;
            default -> throw new IllegalArgumentException("Invalid gender choice: " + choice);
        };
    }
}