package com.smarthome.agent;

import com.smarthome.bdi.*;
import com.smarthome.environment.*;
import java.util.*;

/**
 * Dishwasher Agent: specialized BDI agent for washing dishes
 */
public class DishwasherAgent extends BDIAgent {
    
    public DishwasherAgent(String name, Environment env) {
        super(name, AgentRole.DISHWASHER, env);
    }

    @Override
    protected void deliberate() {
        intentions.clear();
        
        Set<Task> pendingTasks = beliefs.getPendingTasks();
        
        // Focus on washing dishes tasks
        for (Task task : pendingTasks) {
            if (task.getType() == Task.TaskType.WASH_DISHES && energy > 8) {
                intentions.addTaskIntention(task);
                intentions.setCurrentDesire(Desire.COMPLETE_TASKS);
                break;  // Focus on one task at a time
            }
        }
    }
}