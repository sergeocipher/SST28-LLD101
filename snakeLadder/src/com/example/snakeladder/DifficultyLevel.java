package com.example.snakeladder;

public enum DifficultyLevel {
    EASY,
    HARD;

    public static DifficultyLevel from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("difficulty_level is required");
        }
        String normalized = value.trim().toUpperCase();
        if ("EASY".equals(normalized)) {
            return EASY;
        }
        if ("HARD".equals(normalized)) {
            return HARD;
        }
        throw new IllegalArgumentException("difficulty_level must be easy or hard");
    }
}
