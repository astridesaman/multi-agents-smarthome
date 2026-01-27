package com.smarthome.agent;

import java.util.*;
import com.smarthome.bdi.*;
import com.smarthome.environment.*;

/**
 * Abstract base class for all agents in the system.
 * Implements the basic agent perception-deliberation-action cycle.
 */
public abstract class Agent {
    protected String agentName;
    protected AgentRole role;
    protected BeliefBase beliefs;
    protected Intentions intentions;
    protected Environment environment;
    protected MessageBroker messageBroker;
    protected int energy;
    protected boolean active;

    public Agent(String name, AgentRole role, Environment env) {
        this.agentName = name;
        this.role = role;
        this.environment = env;
        this.beliefs = new BeliefBase();
        this.intentions = new Intentions();
        this.messageBroker = MessageBroker.getInstance();
        this.messageBroker.registerAgent(name);
        this.energy = 100;
        this.active = true;
    }

    /**
     * Main agent cycle: perceive -> deliberate -> act
     */
    public void runCycle() {
        if (!active || energy <= 0) return;
        
        perceive();
        processMessages();
        deliberate();
        act();
        consumeEnergy();
    }

    /**
     * Perception: gather information from environment
     */
    protected void perceive() {
        beliefs.updateEnvironmentState(environment);
    }

    /**
     * Process incoming messages from other agents
     */
    protected void processMessages() {
        List<Message> messages = messageBroker.getAllMessages(agentName);
        for (Message msg : messages) {
            beliefs.addMessage(msg);
        }
    }

    /**
     * Deliberation: decide on intentions (abstract, implemented by subclasses)
     */
    protected abstract void deliberate();

    /**
     * Action: execute intentions
     */
    protected abstract void act();

    /**
     * Consume energy based on activities
     */
    protected void consumeEnergy() {
        if (!intentions.getIntentions().isEmpty()) {
            energy -= 5;
        } else {
            energy -= 1;
        }
        if (energy < 0) energy = 0;
    }

    // Communication
    protected void sendMessage(String receiverId, Message.MessageType type, String content) {
        Message msg = new Message(agentName, receiverId, type, content);
        messageBroker.sendMessage(msg);
    }

    // Getters
    public String getAgentName() {
        return agentName;
    }

    public AgentRole getRole() {
        return role;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, energy);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BeliefBase getBeliefs() {
        return beliefs;
    }

    public Intentions getIntentions() {
        return intentions;
    }
}
