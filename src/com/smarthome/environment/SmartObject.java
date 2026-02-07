package com.smarthome.environment;

/**
 * Represents a smart object located in the environment.
 * Objects can be manipulated by agents during tasks.
 */
public class SmartObject {

    public enum ObjectType {
        DISHES,
        GARBAGE,
        CLEANING_TOOL,
        FURNITURE,
        OTHER
    }

    private final String objectId;
    private final ObjectType type;
    private String location;
    private boolean dirty;
    private int quantity;

    public SmartObject(String objectId,
                       ObjectType type,
                       String location,
                       int quantity) {

        this.objectId = objectId;
        this.type = type;
        this.location = location;
        this.quantity = Math.max(0, quantity);
        this.dirty = false;
    }

    /* ---------- GETTERS ---------- */

    public String getId() {
        return objectId;
    }

    public ObjectType getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public boolean isDirty() {
        return dirty;
    }

    public int getQuantity() {
        return quantity;
    }

    /* ---------- SETTERS ---------- */

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    /* ---------- QUANTITY MANAGEMENT ---------- */

    public void increaseQuantity(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public void decreaseQuantity(int amount) {
        if (amount > 0) {
            quantity = Math.max(0, quantity - amount);
        }
    }

    /* ---------- UTILITY ---------- */

    public boolean isAvailable() {
        return quantity > 0;
    }

    @Override
    public String toString() {
        return String.format(
                "SmartObject[id=%s, type=%s, location=%s, dirty=%b, qty=%d]",
                objectId, type, location, dirty, quantity
        );
    }
}
