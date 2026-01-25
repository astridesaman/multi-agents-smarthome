package com.smarthome.bdi;

import java.util.*;

public class BeliefBase {
    private Set<String> dirtyRooms = new HashSet<>();

    public void updateBeliefs(Set<String> perceivedDirtyRooms) {
        dirtyRooms = new HashSet<>(perceivedDirtyRooms);
    }

    public Set<String> getDirtyRooms() {
        return new HashSet<>(dirtyRooms);
    }
}
