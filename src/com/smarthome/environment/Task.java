package com.smarthome.environment;

public class Task {
    public enum TaskType {
        CLEAN_ROOM, WASH_DISHES, THROW_GARBAGE, ORGANIZE_OBJECTS, MONITOR_COMPLETION
    }

    public enum TaskStatus {
        PENDING, ONGOING, COMPLETED, FAILED
    }

    private String taskId;
    private TaskType type;
    private TaskStatus status;
    private String location;
    private int priority;
    private int estimatedTime;
    private int timeRemaining;
    private String assignedAgent;
    private int requiredAgents;

    public Task(String taskId, TaskType type, String location, int priority, int estimatedTime) {
        this.taskId = taskId;
        this.type = type;
        this.location = location;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
        this.timeRemaining = estimatedTime;
        this.status = TaskStatus.PENDING;
        this.requiredAgents = 1;
    }

    // Getters and Setters
    public String getTaskId() {
        return taskId;
    }

    public TaskType getType() {
        return type;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public int getPriority() {
        return priority;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int time) {
        this.timeRemaining = time;
    }

    public void decreaseTime(int amount) {
        this.timeRemaining = Math.max(0, timeRemaining - amount);
    }

    public String getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(String agentName) {
        this.assignedAgent = agentName;
    }

    public int getRequiredAgents() {
        return requiredAgents;
    }

    public void setRequiredAgents(int count) {
        this.requiredAgents = count;
    }

    public boolean isCompleted() {
        return status == TaskStatus.COMPLETED;
    }

    public boolean isPending() {
        return status == TaskStatus.PENDING;
    }

    public boolean isOngoing() {
        return status == TaskStatus.ONGOING;
    }

    @Override
    public String toString() {
        return String.format("Task[id=%s, type=%s, location=%s, status=%s, priority=%d, time=%d]",
                taskId, type, location, status, priority, timeRemaining);
    }
}
