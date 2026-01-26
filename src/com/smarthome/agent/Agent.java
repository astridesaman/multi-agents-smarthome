package com.smarthome.agent;

import java.util.Set;

import com.smarthome.bdi.*;
import com.smarthome.environment.*;

public abstract class Agent {
    protected String agentName;
    protected BeliefBase beliefs;
    protected Intentions intentions;
    protected Environment environment;
    protected int energy = 10; // max energy
    protected final int MAX_ENERGY = 10;

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
        boolean laundry = environment.isLaundryNeeded();
        Set<String> trash = environment.getRoomsWithTrash();
        boolean central = environment.isCentralTrashFull();
        beliefs.updateBeliefs(dirty, laundry, trash, central);
    }

    protected abstract void deliberate();

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
        }
        intentions.clear();
    }

    public boolean hasEnergyForAction(int cost) {
        return energy >= cost;
    }
}
