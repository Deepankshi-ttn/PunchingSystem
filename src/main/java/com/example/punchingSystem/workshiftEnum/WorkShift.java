package com.example.punchingSystem.workshiftEnum;

public enum WorkShift {
    MORNING_SHIFT("Morning Shift"),
    EVENING_SHIFT("Evening Shift"),
    NIGHT_SHIFT("Night Shift");

    private final String displayName;

    WorkShift(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
