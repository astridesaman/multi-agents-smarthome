package com.smarthome.agent;

import com.smarthome.bdi.*;
import com.smarthome.environment.*;
import java.util.*;

/**
 * Garbage Collector Agent: specialized BDI agent for garbage management
 */
public class GarbageCollectorAgent extends BDIAgent {
    
    public GarbageCollectorAgent(String name, Environment env) {
        super(name, AgentRole.GARBAGE_COLLECTOR, env);
    }

    @Override
    protected void deliberate() {
        intentions.clear();
        
        Set<Task> pendingTasks = beliefs.getPendingTasks();
        
        // Focus on garbage disposal tasks
        for (Task task : pendingTasks) {
            if (task.getType() == Task.TaskType.THROW_GARBAGE && energy > 5) {
                intentions.addTaskIntention(task);
                intentions.setCurrentDesire(Desire.COMPLETE_TASKS);
                break;
            }
        }
    }
}