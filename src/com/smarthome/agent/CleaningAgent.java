package com.smarthome.agent;

import com.smarthome.environment.*;
import java.util.*;

public class CleaningAgent extends Agent {
    
    public CleaningAgent(String name, Environment env) {
        super(name, env);
    }

    @Override
    protected void deliberate() {
        // If low energy, rest
        if (energy < 2) {
            intentions.addIntention("rest");
            return;
        }

        // Prioritize tasks: clean rooms, empty trash, do laundry, take out trash
        Set<String> dirty = beliefs.getDirtyRooms();
        if (!dirty.isEmpty() && hasEnergyForAction(2)) {
            String room = dirty.iterator().next();
            intentions.addIntention("clean " + room);
        } else {
            Set<String> trashRooms = beliefs.getRoomsWithTrash();
            if (!trashRooms.isEmpty() && hasEnergyForAction(1)) {
                String room = trashRooms.iterator().next();
                intentions.addIntention("empty trash " + room);
            } else if (beliefs.isLaundryNeeded() && hasEnergyForAction(3)) {
                intentions.addIntention("do laundry");
            } else if (beliefs.isCentralTrashFull() && hasEnergyForAction(2)) {
                intentions.addIntention("take out trash");
            } else {
                intentions.addIntention("rest");
            }
        }
    }

    public static void main(String[] args) {
        Environment env = new Environment();
        CleaningAgent agent = new CleaningAgent("Cleaner", env);
        System.out.println("Initial state:");
        System.out.println("Dirty rooms: " + env.getDirtyRooms());
        System.out.println("Rooms with trash: " + env.getRoomsWithTrash());
        System.out.println("Laundry needed: " + env.isLaundryNeeded());
        System.out.println("Central trash full: " + env.isCentralTrashFull());
        System.out.println();

        int cycle = 0;
        while (( !env.getDirtyRooms().isEmpty() || !env.getRoomsWithTrash().isEmpty() || env.isLaundryNeeded() || env.isCentralTrashFull() ) || agent.energy < agent.MAX_ENERGY) {
            cycle++;
            System.out.println("Cycle " + cycle + " (Energy: " + agent.energy + "):");
            agent.runCycle();
            System.out.println("Remaining: Dirty=" + env.getDirtyRooms() + ", Trash=" + env.getRoomsWithTrash() + ", Laundry=" + env.isLaundryNeeded() + ", CentralTrash=" + env.isCentralTrashFull());
            System.out.println();
        }
        System.out.println("All tasks completed and agent is rested!");
    }
}
