package com.smarthome.agent;

import com.smarthome.environment.*;
import java.util.*;

/**
 * Reactive Agent: responds directly to environmental stimuli using condition-action rules.
 * No deliberation or planning, immediate reaction to perceived state.
 */
public class ReactiveAgent extends Agent {
    
    public ReactiveAgent(String name, AgentRole role, Environment env) {
        super(name, role, env);
    }

    /**
     * Reactive agents have no deliberation phase - they map percepts directly to actions
     */
    @Override
    protected void deliberate() {
        // Reactive agents don't deliberate - rules are applied directly
    }

    /**
     * Apply condition-action rules based on current beliefs
     */
    @Override
    protected void act() {
        intentions.clear();
        
        // Get dirty rooms
        Set<String> dirtyRooms = beliefs.getDirtyRooms();
        
        // Condition-Action Rule 1: If room is dirty -> Clean it
        if (role == AgentRole.CLEANER) {
            for (String room : dirtyRooms) {
                environment.cleanRoom(room);
                System.out.println("[REACTIVE] " + agentName + " (" + role.getDisplayName() + ") cleaned " + room);
            }
        }
        
        // Condition-Action Rule 2: If there are pending cleaning tasks -> Execute them
        Set<Task> pendingTasks = beliefs.getPendingTasks();
        for (Task task : pendingTasks) {
            if (isResponsibleForTask(task)) {
                executeTask(task);
            }
        }
    }

    /**
     * Check if this agent is responsible for a task based on role
     */
    private boolean isResponsibleForTask(Task task) {
        switch (role) {
            case CLEANER:
                return task.getType() == Task.TaskType.CLEAN_ROOM;
            case DISHWASHER:
                return task.getType() == Task.TaskType.WASH_DISHES;
            case GARBAGE_COLLECTOR:
                return task.getType() == Task.TaskType.THROW_GARBAGE;
            case ORGANIZER:
                return task.getType() == Task.TaskType.ORGANIZE_OBJECTS;
            default:
                return false;
        }
    }

    /**
     * Execute a task immediately
     */
    private void executeTask(Task task) {
        if (energy >= 5) {  // Check if agent has enough energy
            environment.assignTaskToAgent(task.getTaskId(), agentName);
            System.out.println("[REACTIVE] " + agentName + " executing task: " + task.getTaskId());
        }
    }
}