package com.smarthome.bdi;

import java.util.*;

public class BeliefBase {
    private Set<String> dirtyRooms = new HashSet<>();
    private boolean laundryNeeded = false;
    private Set<String> roomsWithTrash = new HashSet<>();
    private boolean centralTrashFull = false;

    public void updateBeliefs(Set<String> perceivedDirtyRooms, boolean laundry, Set<String> trashRooms, boolean centralTrash) {
        dirtyRooms = new HashSet<>(perceivedDirtyRooms);
        laundryNeeded = laundry;
        roomsWithTrash = new HashSet<>(trashRooms);
        centralTrashFull = centralTrash;
    }

    public Set<String> getDirtyRooms() {
        return new HashSet<>(dirtyRooms);
    }

    public boolean isLaundryNeeded() {
        return laundryNeeded;
    }

    public Set<String> getRoomsWithTrash() {
        return new HashSet<>(roomsWithTrash);
    }

    public boolean isCentralTrashFull() {
        return centralTrashFull;
    }
}
