package com.smarthome.bdi;

import java.util.*;
import com.smarthome.agent.Message;
import com.smarthome.environment.*;

/**
 * Represents the agent's knowledge about the world.
 * Stores beliefs about environment state, tasks, and other agents.
 */
public class BeliefBase {
<<<<<<< HEAD
    private Set<String> dirtyRooms;
    private Set<Task> knownTasks;
    private Set<Task> completedTasks;
    private List<Message> messageHistory;
    private Map<String, Integer> agentCapabilities;
    private int updateCounter;

    public BeliefBase() {
        this.dirtyRooms = new HashSet<>();
        this.knownTasks = new HashSet<>();
        this.completedTasks = new HashSet<>();
        this.messageHistory = new ArrayList<>();
        this.agentCapabilities = new HashMap<>();
        this.updateCounter = 0;
=======
    private Set<String> dirtyRooms = new HashSet<>();
    private boolean laundryNeeded = false;
    private Set<String> roomsWithTrash = new HashSet<>();
    private boolean centralTrashFull = false;

    public void updateBeliefs(Set<String> perceivedDirtyRooms, boolean laundry, Set<String> trashRooms, boolean centralTrash) {
        dirtyRooms = new HashSet<>(perceivedDirtyRooms);
        laundryNeeded = laundry;
        roomsWithTrash = new HashSet<>(trashRooms);
        centralTrashFull = centralTrash;
>>>>>>> 3d89eaa101651dd6fa926a11f07f3b8b917d33ca
    }

    /**
     * Update beliefs based on environment perception
     */
    public void updateEnvironmentState(Environment environment) {
        updateCounter++;
        
        // Update dirty rooms
        dirtyRooms = new HashSet<>(environment.getDirtyRooms());
        
        // Update known tasks
        knownTasks = new HashSet<>(environment.getPendingTasks());
        knownTasks.addAll(environment.getOngoingTasks());
        
        // Update completed tasks
        completedTasks = new HashSet<>(environment.getCompletedTasks());
    }

    // Getters for beliefs
    public Set<String> getDirtyRooms() {
        return new HashSet<>(dirtyRooms);
    }

<<<<<<< HEAD
    public Set<Task> getPendingTasks() {
        Set<Task> pending = new HashSet<>();
        for (Task task : knownTasks) {
            if (task.isPending()) {
                pending.add(task);
            }
        }
        return pending;
    }

    public Set<Task> getOngoingTasks() {
        Set<Task> ongoing = new HashSet<>();
        for (Task task : knownTasks) {
            if (task.isOngoing()) {
                ongoing.add(task);
            }
        }
        return ongoing;
    }

    public Set<Task> getCompletedTasks() {
        return new HashSet<>(completedTasks);
    }

    public Set<Task> getAllKnownTasks() {
        Set<Task> all = new HashSet<>(knownTasks);
        all.addAll(completedTasks);
        return all;
    }

    public Task getTaskByType(Task.TaskType type) {
        for (Task task : knownTasks) {
            if (task.getType() == type && task.isPending()) {
                return task;
            }
        }
        return null;
    }

    // Message handling
    public void addMessage(Message message) {
        messageHistory.add(message);
    }

    public List<Message> getMessageHistory() {
        return new ArrayList<>(messageHistory);
    }

    public List<Message> getRecentMessages(int count) {
        int start = Math.max(0, messageHistory.size() - count);
        return new ArrayList<>(messageHistory.subList(start, messageHistory.size()));
    }

    public void clearMessages() {
        messageHistory.clear();
    }

    // Agent capability tracking
    public void recordAgentCapability(String agentName, int capability) {
        agentCapabilities.put(agentName, capability);
    }

    public int getAgentCapability(String agentName) {
        return agentCapabilities.getOrDefault(agentName, 0);
    }

    public int getUpdateCounter() {
        return updateCounter;
    }

    // Utility methods
    public boolean hasAnyDirtyRoom() {
        return !dirtyRooms.isEmpty();
    }

    public boolean hasAnyPendingTask() {
        return !getPendingTasks().isEmpty();
    }

    public int getPendingTaskCount() {
        return getPendingTasks().size();
    }

    public int getDirtyRoomCount() {
        return dirtyRooms.size();
=======
    public boolean isLaundryNeeded() {
        return laundryNeeded;
    }

    public Set<String> getRoomsWithTrash() {
        return new HashSet<>(roomsWithTrash);
    }

    public boolean isCentralTrashFull() {
        return centralTrashFull;
>>>>>>> 3d89eaa101651dd6fa926a11f07f3b8b917d33ca
    }
}
