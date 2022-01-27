package com.kata.survivor;


public enum ExperienceLevel {
    BLUE, YELLOW, ORANGE, RED;

    public static ExperienceLevel from(int experience) {
        if (experience <= 6) return BLUE;
        if (experience <= 18) return YELLOW;
        if (experience <= 42) return ORANGE;
        return RED;
    }

    public static ExperienceLevel initial() {
        return BLUE;
    }
}
