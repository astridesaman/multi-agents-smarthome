package com.smarthome.bdi;

import java.util.*;
import com.smarthome.environment.Task;

/**
 * Represents the agent's intentions (commitment to action).
 * Intentions are formed based on beliefs and desires through deliberation.
 * They guide the agent's behavior.
 */
public class Intentions {
    private List<String> actionIntentions;
    private Stack<Task> taskIntentions;
    private Desire currentDesire;
    private int updateCounter;

    public Intentions() {
        this.actionIntentions = new ArrayList<>();
        this.taskIntentions = new Stack<>();
        this.updateCounter = 0;
    }

    /**
     * Add an action intention
     */
    public void addIntention(String action) {
        actionIntentions.add(action);
    }

    /**
     * Add a task intention
     */
    public void addTaskIntention(Task task) {
        taskIntentions.push(task);
    }

    /**
     * Remove and return the next task intention (LIFO)
     */
    public Task popTaskIntention() {
        return taskIntentions.isEmpty() ? null : taskIntentions.pop();
    }

    /**
     * Peek at the next task intention without removing it
     */
    public Task peekTaskIntention() {
        return taskIntentions.isEmpty() ? null : taskIntentions.peek();
    }

    /**
     * Get all action intentions
     */
    public List<String> getIntentions() {
        return new ArrayList<>(actionIntentions);
    }

    /**
     * Get all task intentions
     */
    public List<Task> getTaskIntentions() {
        return new ArrayList<>(taskIntentions);
    }

    /**
     * Set the current driving desire
     */
    public void setCurrentDesire(Desire desire) {
        this.currentDesire = desire;
        updateCounter++;
    }

    /**
     * Get the current driving desire
     */
    public Desire getCurrentDesire() {
        return currentDesire;
    }

    /**
     * Check if there are any pending intentions
     */
    public boolean hasIntentions() {
        return !actionIntentions.isEmpty() || !taskIntentions.isEmpty();
    }

    /**
     * Get the number of intentions
     */
    public int getIntentionCount() {
        return actionIntentions.size() + taskIntentions.size();
    }

    /**
     * Clear all intentions
     */
    public void clear() {
        actionIntentions.clear();
        taskIntentions.clear();
        currentDesire = null;
    }

    /**
     * Check if a specific intention exists
     */
    public boolean hasIntention(String action) {
        return actionIntentions.contains(action);
    }

    public int getUpdateCounter() {
        return updateCounter;
    }

    @Override
    public String toString() {
        return String.format("Intentions[actions=%d, tasks=%d, desire=%s]",
                actionIntentions.size(), taskIntentions.size(), currentDesire);
    }
}
