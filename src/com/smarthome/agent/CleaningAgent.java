package com.smarthome.agent;

import com.smarthome.environment.*;
import java.util.*;

public class CleaningAgent extends Agent {
    
    public CleaningAgent(String name, Environment env) {
        super(name, env);
    }

    @Override
    protected void deliberate() {
        Set<String> dirty = beliefs.getDirtyRooms();
        for (String room : dirty) {
            intentions.addIntention("clean " + room);
        }
    }

    public static void main(String[] args) {
        Environment env = new Environment();
        CleaningAgent agent = new CleaningAgent("Cleaner", env);
        System.out.println("Initial dirty rooms: " + env.getDirtyRooms());
        agent.runCycle();
        System.out.println("After cleaning: " + env.getDirtyRooms());
    }
}
