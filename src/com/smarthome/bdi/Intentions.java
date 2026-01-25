package com.smarthome.bdi;

import java.util.*;

public class Intentions {
    private List<String> intentions = new ArrayList<>();

    public void addIntention(String action) {
        intentions.add(action);
    }

    public List<String> getIntentions() {
        return new ArrayList<>(intentions);
    }

    public void clear() {
        intentions.clear();
    }
}
