package com.smarthome.environment;

import java.util.*;

public class Room {
    private String name;
    private boolean isClean;
    private Set<SmartObject> objects;

    public Room(String name) {
        this.name = name;
        this.isClean = true;
        this.objects = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        this.isClean = clean;
    }

    public void addObject(SmartObject obj) {
        objects.add(obj);
    }

    public void removeObject(SmartObject obj) {
        objects.remove(obj);
    }

    public Set<SmartObject> getObjects() {
        return new HashSet<>(objects);
    }

    public Set<SmartObject> getDirtyObjects() {
        Set<SmartObject> dirty = new HashSet<>();
        for (SmartObject obj : objects) {
            if (obj.isDirty()) {
                dirty.add(obj);
            }
        }
        return dirty;
    }

    @Override
    public String toString() {
        return String.format("Room[name=%s, clean=%b, objects=%d]", name, isClean, objects.size());
    }
}
