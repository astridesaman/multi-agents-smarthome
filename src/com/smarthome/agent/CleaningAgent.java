package com.smarthome.agent;

import com.smarthome.bdi.*;
import com.smarthome.environment.*;
import java.util.*;

/**
 * Cleaning Agent: specialized BDI agent for room cleaning tasks
 */
public class CleaningAgent extends BDIAgent {
    
    public CleaningAgent(String name, Environment env) {
        super(name, AgentRole.CLEANER, env);
    }

    @Override
    protected void deliberate() {
        intentions.clear();
        
        // Check for dirty rooms
        Set<String> dirtyRooms = beliefs.getDirtyRooms();
        
        if (!dirtyRooms.isEmpty() && energy > 10) {
            // Form intention to clean
            for (String room : dirtyRooms) {
                intentions.addIntention("clean " + room);
            }
            intentions.setCurrentDesire(Desire.CLEAN_ENVIRONMENT);
        }
        
        // Also consider task-based cleaning
        Set<Task> pendingTasks = beliefs.getPendingTasks();
        for (Task task : pendingTasks) {
            if (task.getType() == Task.TaskType.CLEAN_ROOM && task.isPending()) {
                intentions.addTaskIntention(task);
            }
        }
    }
}
