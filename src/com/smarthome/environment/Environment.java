package com.smarthome.environment;

import java.util.*;

public class Environment {
    private Set<String> dirtyRooms = new HashSet<>();
    private Set<String> allRooms = new HashSet<>(Arrays.asList("kitchen", "livingroom", "bedroom"));

    public Environment() {
        // randomly make some rooms dirty
        Random rand = new Random();
        for (String room : allRooms) {
            if (rand.nextBoolean()) {
                dirtyRooms.add(room);
            }
        }
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
}
