package com.smarthome.agent;

import java.util.Set;

import com.smarthome.bdi.*;
import com.smarthome.environment.*;

public abstract class Agent {
    protected String agentName;
    protected BeliefBase beliefs;
    protected Intentions intentions;
    protected Environment environment;

    public Agent(String name, Environment env) {
        this.agentName = name;
        this.environment = env;
        this.beliefs = new BeliefBase();
        this.intentions = new Intentions();
    }

    public void runCycle() {
        perceive();
        deliberate();
        act();
    }

    protected void perceive() {
        Set<String> dirty = environment.getDirtyRooms();
        beliefs.updateBeliefs(dirty);
    }

    protected abstract void deliberate();

    protected void act() {
        for (String intention : intentions.getIntentions()) {
            if (intention.startsWith("clean ")) {
                String room = intention.substring(6);
                environment.cleanRoom(room);
                System.out.println(agentName + " cleaned " + room);
            }
        }
        intentions.clear();
    }
}
