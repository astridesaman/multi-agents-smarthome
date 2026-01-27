package com.smarthome.bdi;

/**
 * Represents possible desires/goals that an agent may have.
 * Each desire corresponds to a high-level objective the agent wants to achieve.
 */
public enum Desire {
    CLEAN_ENVIRONMENT("Clean all dirty rooms", 3),
    COMPLETE_TASKS("Complete assigned tasks", 2),
    MINIMIZE_ENERGY("Minimize energy consumption", 1),
    HELP_OTHERS("Assist other agents", 2),
    GATHER_INFORMATION("Learn about environment state", 1),
    MAINTAIN_ORDER("Keep objects organized", 2);

    private String description;
    private int priority;

    Desire(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
