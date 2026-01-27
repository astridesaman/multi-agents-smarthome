package com.smarthome.agent;

import com.smarthome.bdi.*;
import com.smarthome.environment.*;
import java.util.*;

/**
 * Organizer Agent: specialized BDI agent for organizing objects
 */
public class OrganizerAgent extends BDIAgent {
    
    public OrganizerAgent(String name, Environment env) {
        super(name, AgentRole.ORGANIZER, env);
    }

    @Override
    protected void deliberate() {
        intentions.clear();
        
        Set<Task> pendingTasks = beliefs.getPendingTasks();
        
        // Focus on organizing and maintenance tasks
        for (Task task : pendingTasks) {
            if (task.getType() == Task.TaskType.ORGANIZE_OBJECTS && energy > 7) {
                intentions.addTaskIntention(task);
                intentions.setCurrentDesire(Desire.MAINTAIN_ORDER);
                break;
            }
        }
    }
}