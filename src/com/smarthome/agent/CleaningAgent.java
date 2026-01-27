package com.smarthome.agent;

import com.smarthome.bdi.*;
import com.smarthome.environment.*;
import java.util.*;

/**
 * Cleaning Agent: specialized BDI agent for room cleaning tasks
 */
public class CleaningAgent extends BDIAgent {
    
    public CleaningAgent(String name, Environment env) {
        super(name, AgentRole.CLEANER, env);
    }

    @Override
    protected void deliberate() {
<<<<<<< HEAD
        intentions.clear();
        
        // Check for dirty rooms
        Set<String> dirtyRooms = beliefs.getDirtyRooms();
        
        if (!dirtyRooms.isEmpty() && energy > 10) {
            // Form intention to clean
            for (String room : dirtyRooms) {
                intentions.addIntention("clean " + room);
            }
            intentions.setCurrentDesire(Desire.CLEAN_ENVIRONMENT);
        }
        
        // Also consider task-based cleaning
        Set<Task> pendingTasks = beliefs.getPendingTasks();
        for (Task task : pendingTasks) {
            if (task.getType() == Task.TaskType.CLEAN_ROOM && task.isPending()) {
                intentions.addTaskIntention(task);
            }
        }
    }
=======
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
>>>>>>> 3d89eaa101651dd6fa926a11f07f3b8b917d33ca
}
