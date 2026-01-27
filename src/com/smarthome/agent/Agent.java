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
<<<<<<< HEAD
    protected MessageBroker messageBroker;
    protected int energy;
    protected boolean active;
=======
    protected int energy = 10; // max energy
    protected final int MAX_ENERGY = 10;
>>>>>>> 3d89eaa101651dd6fa926a11f07f3b8b917d33ca

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
<<<<<<< HEAD
        beliefs.updateEnvironmentState(environment);
=======
        Set<String> dirty = environment.getDirtyRooms();
        boolean laundry = environment.isLaundryNeeded();
        Set<String> trash = environment.getRoomsWithTrash();
        boolean central = environment.isCentralTrashFull();
        beliefs.updateBeliefs(dirty, laundry, trash, central);
>>>>>>> 3d89eaa101651dd6fa926a11f07f3b8b917d33ca
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

<<<<<<< HEAD
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
=======
    protected void act() {
        for (String intention : intentions.getIntentions()) {
            if (intention.startsWith("clean ")) {
                String room = intention.substring(6);
                environment.cleanRoom(room);
                System.out.println(agentName + " cleaned " + room);
                energy -= 2;
            } else if (intention.equals("do laundry")) {
                environment.doLaundry();
                System.out.println(agentName + " did the laundry");
                energy -= 3;
            } else if (intention.startsWith("empty trash ")) {
                String room = intention.substring(12);
                environment.emptyTrash(room);
                System.out.println(agentName + " emptied trash in " + room);
                energy -= 1;
            } else if (intention.equals("take out trash")) {
                environment.takeOutTrash();
                System.out.println(agentName + " took out the trash");
                energy -= 2;
            } else if (intention.equals("rest")) {
                energy = Math.min(MAX_ENERGY, energy + 2);
                System.out.println(agentName + " is resting. Energy: " + energy);
            }
>>>>>>> 3d89eaa101651dd6fa926a11f07f3b8b917d33ca
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

    public boolean hasEnergyForAction(int cost) {
        return energy >= cost;
    }
}
