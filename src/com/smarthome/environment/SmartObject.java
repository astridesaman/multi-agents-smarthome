package com.smarthome.environment;

public class SmartObject {
    public enum ObjectType {
        DISHES, GARBAGE, CLEANING_TOOL, FURNITURE, OTHER
    }

    private String objectId;
    private ObjectType type;
    private String location;
    private boolean isDirty;
    private int quantity;

    public SmartObject(String objectId, ObjectType type, String location, int quantity) {
        this.objectId = objectId;
        this.type = type;
        this.location = location;
        this.quantity = quantity;
        this.isDirty = false;
    }

    // Getters and Setters
    public String getObjectId() {
        return objectId;
    }

    public ObjectType getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity = Math.max(0, quantity - amount);
    }

    @Override
    public String toString() {
        return String.format("Object[id=%s, type=%s, location=%s, dirty=%b, qty=%d]",
                objectId, type, location, isDirty, quantity);
    }
}
