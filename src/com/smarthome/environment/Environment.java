package com.smarthome.environment;

import java.util.*;

public class Environment {
    private Set<String> dirtyRooms = new HashSet<>();
    private Set<String> allRooms = new HashSet<>(Arrays.asList("kitchen", "livingroom", "bedroom"));
    private boolean laundryNeeded = false;
    private Set<String> roomsWithTrash = new HashSet<>();
    private boolean centralTrashFull = false;

    public Environment() {
        // randomly make some rooms dirty
        Random rand = new Random();
        for (String room : allRooms) {
            if (rand.nextBoolean()) {
                dirtyRooms.add(room);
            }
            if (rand.nextBoolean()) {
                roomsWithTrash.add(room);
            }
        }
        laundryNeeded = rand.nextBoolean();
    }

    public Set<String> getDirtyRooms() {
        return new HashSet<>(dirtyRooms);
    }

    public void cleanRoom(String room) {
        dirtyRooms.remove(room);
    }

    public boolean isRoomDirty(String room) {
        return dirtyRooms.contains(room);
    }

    public boolean isLaundryNeeded() {
        return laundryNeeded;
    }

    public void doLaundry() {
        laundryNeeded = false;
    }

    public Set<String> getRoomsWithTrash() {
        return new HashSet<>(roomsWithTrash);
    }

    public void emptyTrash(String room) {
        roomsWithTrash.remove(room);
        centralTrashFull = true; // emptying adds to central
    }

    public boolean isCentralTrashFull() {
        return centralTrashFull;
    }

    public void takeOutTrash() {
        centralTrashFull = false;
    }
}
