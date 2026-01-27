package com.smarthome.agent;

import com.smarthome.environment.*;
import java.util.*;

/**
 * Coordinator Agent: manages task allocation and agent coordination
 * Uses a BDI architecture with centralized decision making
 */
public class CoordinatorAgent extends BDIAgent {
    private List<String> registeredAgents;
    private Map<String, Integer> agentWorkload;

    public CoordinatorAgent(String name, Environment env) {
        super(name, AgentRole.COORDINATOR, env);
        this.registeredAgents = new ArrayList<>();
        this.agentWorkload = new HashMap<>();
    }

    public void registerAgent(String agentName) {
        if (!registeredAgents.contains(agentName)) {
            registeredAgents.add(agentName);
            agentWorkload.put(agentName, 0);
            System.out.println("[COORDINATOR] Agent registered: " + agentName);
        }
    }

    @Override
    protected void deliberate() {
        super.deliberate();
        
        // Coordinator-specific deliberation: allocate tasks to agents
        Set<Task> pendingTasks = beliefs.getPendingTasks();
        
        for (Task task : pendingTasks) {
            String bestAgent = findBestAgentForTask(task);
            if (bestAgent != null) {
                // Send task allocation message
                Message msg = new Message(agentName, bestAgent, 
                    Message.MessageType.REQUEST, "Please execute task: " + task.getTaskId());
                msg.setData(task);
                messageBroker.sendMessage(msg);
                
                agentWorkload.put(bestAgent, agentWorkload.getOrDefault(bestAgent, 0) + 1);
            }
        }
    }

    /**
     * Find the best agent to execute a task based on role and workload
     */
    private String findBestAgentForTask(Task task) {
        String bestAgent = null;
        int minWorkload = Integer.MAX_VALUE;

        for (String agentName : registeredAgents) {
            int workload = agentWorkload.getOrDefault(agentName, 0);
            
            // Simple heuristic: prefer agents with lower workload
            if (workload < minWorkload) {
                minWorkload = workload;
                bestAgent = agentName;
            }
        }

        return bestAgent;
    }

    @Override
    protected void act() {
        super.act();
        
        // Log coordination status
        System.out.println("[COORDINATOR] Agent workloads: " + agentWorkload);
        System.out.println("[COORDINATOR] Pending tasks: " + beliefs.getPendingTasks().size());
    }

    public Map<String, Integer> getAgentWorkloads() {
        return new HashMap<>(agentWorkload);
    }
}