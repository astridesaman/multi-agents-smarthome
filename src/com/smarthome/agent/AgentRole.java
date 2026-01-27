package com.smarthome.agent;

/**
 * Enumerates the roles that agents can play in the system
 */
public enum AgentRole {
    CLEANER("Cleaner", "Responsible for cleaning rooms"),
    DISHWASHER("Dishwasher", "Responsible for washing dishes"),
    GARBAGE_COLLECTOR("Garbage Collector", "Responsible for garbage management"),
    ORGANIZER("Organizer", "Responsible for organizing objects"),
    COORDINATOR("Coordinator", "Coordinates tasks and agents"),
    MONITOR("Monitor", "Monitors task completion");

    private String displayName;
    private String description;

    AgentRole(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
