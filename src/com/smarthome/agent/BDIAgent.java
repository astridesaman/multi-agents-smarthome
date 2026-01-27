package com.smarthome.agent;

import com.smarthome.bdi.*;
import com.smarthome.environment.*;
import java.util.*;

/**
 * BDI Agent: Uses beliefs, desires, and intentions for deliberative behavior.
 * Plans actions based on goals and world state.
 */
public class BDIAgent extends Agent {
    private Set<Desire> desires;
    
    public BDIAgent(String name, AgentRole role, Environment env) {
        super(name, role, env);
        this.desires = new HashSet<>();
        initializeDesires();
    }

    private void initializeDesires() {
        // All agents share basic desires
        desires.add(Desire.COMPLETE_TASKS);
        desires.add(Desire.MINIMIZE_ENERGY);
        
        // Role-specific desires
        switch (role) {
            case CLEANER:
                desires.add(Desire.CLEAN_ENVIRONMENT);
                break;
            case COORDINATOR:
                desires.add(Desire.HELP_OTHERS);
                desires.add(Desire.GATHER_INFORMATION);
                break;
            default:
                desires.add(Desire.MAINTAIN_ORDER);
        }
    }

    /**
     * BDI deliberation: form intentions from beliefs and desires
     */
    @Override
    protected void deliberate() {
        intentions.clear();
        
        // Analyze beliefs and form goals
        if (beliefs.hasAnyPendingTask()) {
            deliberateOnTasks();
        }
        
        if (beliefs.hasAnyDirtyRoom() && role == AgentRole.CLEANER) {
            deliberateOnCleaning();
        }
    }

    /**
     * Deliberate on task-related goals
     */
    private void deliberateOnTasks() {
        Set<Task> pendingTasks = beliefs.getPendingTasks();
        
        // Prioritize tasks by priority and energy availability
        List<Task> sortedTasks = new ArrayList<>(pendingTasks);
        sortedTasks.sort((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));
        
        for (Task task : sortedTasks) {
            if (isCapableOfTask(task)) {
                if (energy >= 10) {  // Deliberative agents use more energy for thinking
                    intentions.addTaskIntention(task);
                    intentions.setCurrentDesire(Desire.COMPLETE_TASKS);
                }
            }
        }
    }

    /**
     * Deliberate on cleaning goals (cleaner agent specific)
     */
    private void deliberateOnCleaning() {
        Set<String> dirtyRooms = beliefs.getDirtyRooms();
        for (String room : dirtyRooms) {
            intentions.addIntention("clean " + room);
        }
        intentions.setCurrentDesire(Desire.CLEAN_ENVIRONMENT);
    }

    /**
     * Check if agent is capable of performing a task
     */
    private boolean isCapableOfTask(Task task) {
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
     * Execute intentions formed during deliberation
     */
    @Override
    protected void act() {
        // Execute action intentions
        for (String intention : intentions.getIntentions()) {
            if (intention.startsWith("clean ")) {
                String room = intention.substring(6);
                environment.cleanRoom(room);
                System.out.println("[BDI] " + agentName + " (" + role.getDisplayName() + ") cleaned " + room);
            }
        }
        
        // Execute task intentions
        Task task = intentions.peekTaskIntention();
        if (task != null) {
            if (energy >= 10) {
                environment.assignTaskToAgent(task.getTaskId(), agentName);
                String desireStr = intentions.getCurrentDesire() != null 
                    ? intentions.getCurrentDesire().getDescription() 
                    : "UNKNOWN";
                System.out.println("[BDI] " + agentName + " pursuing task: " + task.getTaskId() 
                    + " (Desire: " + desireStr + ")");
                intentions.popTaskIntention();
            }
        }
    }

    public Set<Desire> getDesires() {
        return new HashSet<>(desires);
    }
}