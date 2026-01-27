package com.smarthome.environment;

import java.util.*;

/**
 * Represents the smart apartment environment where agents operate.
 * Manages rooms, objects, tasks, and provides perception/action mechanisms.
 */
public class Environment {
<<<<<<< HEAD
    private Map<String, Room> rooms;
    private List<Task> tasks;
    private Map<String, SmartObject> objects;
    private int timeStep;
    private static final int MAX_TIME_STEPS = 100;
=======
    private Set<String> dirtyRooms = new HashSet<>();
    private Set<String> allRooms = new HashSet<>(Arrays.asList("kitchen", "livingroom", "bedroom"));
    private boolean laundryNeeded = false;
    private Set<String> roomsWithTrash = new HashSet<>();
    private boolean centralTrashFull = false;
>>>>>>> 3d89eaa101651dd6fa926a11f07f3b8b917d33ca

    public Environment() {
        this.rooms = new HashMap<>();
        this.tasks = new ArrayList<>();
        this.objects = new HashMap<>();
        this.timeStep = 0;
        
        initializeRooms();
        initializeObjects();
        generateInitialTasks();
    }

    private void initializeRooms() {
        String[] roomNames = {"kitchen", "living_room", "bedroom", "bathroom"};
        for (String name : roomNames) {
            rooms.put(name, new Room(name));
        }
    }

    private void initializeObjects() {
        // Kitchen objects
        SmartObject dishes = new SmartObject("dishes-1", SmartObject.ObjectType.DISHES, "kitchen", 5);
        dishes.setDirty(true);
        objects.put("dishes-1", dishes);
        rooms.get("kitchen").addObject(dishes);

        // Living room objects
        SmartObject garbage = new SmartObject("garbage-1", SmartObject.ObjectType.GARBAGE, "living_room", 3);
        objects.put("garbage-1", garbage);
        rooms.get("living_room").addObject(garbage);

        // Bedroom objects
        SmartObject furniture = new SmartObject("furniture-1", SmartObject.ObjectType.FURNITURE, "bedroom", 1);
        objects.put("furniture-1", furniture);
        rooms.get("bedroom").addObject(furniture);

        // Tools
        SmartObject cleaningTools = new SmartObject("tools-1", SmartObject.ObjectType.CLEANING_TOOL, "kitchen", 3);
        objects.put("tools-1", cleaningTools);
        rooms.get("kitchen").addObject(cleaningTools);
    }

    private void generateInitialTasks() {
        tasks.add(new Task("task-1", Task.TaskType.CLEAN_ROOM, "kitchen", 3, 10));
        tasks.add(new Task("task-2", Task.TaskType.CLEAN_ROOM, "living_room", 2, 8));
        tasks.add(new Task("task-3", Task.TaskType.WASH_DISHES, "kitchen", 3, 5));
        tasks.add(new Task("task-4", Task.TaskType.THROW_GARBAGE, "living_room", 2, 3));
        tasks.add(new Task("task-5", Task.TaskType.ORGANIZE_OBJECTS, "bedroom", 1, 7));
    }

    public void simulateTimeStep() {
        timeStep++;
        // Process ongoing tasks
        for (Task task : tasks) {
            if (task.isOngoing()) {
                task.decreaseTime(1);
                if (task.getTimeRemaining() <= 0) {
                    task.setStatus(Task.TaskStatus.COMPLETED);
                    System.out.println("[ENV] Task " + task.getTaskId() + " completed!");
                }
            }
            if (rand.nextBoolean()) {
                roomsWithTrash.add(room);
            }
        }
        laundryNeeded = rand.nextBoolean();
    }

    // Perception methods
    public Set<String> getDirtyRooms() {
        Set<String> dirty = new HashSet<>();
        for (Room room : rooms.values()) {
            if (!room.isClean()) {
                dirty.add(room.getName());
            }
        }
        return dirty;
    }

    public Set<Task> getPendingTasks() {
        Set<Task> pending = new HashSet<>();
        for (Task task : tasks) {
            if (task.isPending()) {
                pending.add(task);
            }
        }
        return pending;
    }

    public Set<Task> getOngoingTasks() {
        Set<Task> ongoing = new HashSet<>();
        for (Task task : tasks) {
            if (task.isOngoing()) {
                ongoing.add(task);
            }
        }
        return ongoing;
    }

    public Set<Task> getCompletedTasks() {
        Set<Task> completed = new HashSet<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completed.add(task);
            }
        }
        return completed;
    }

    public Task getTaskById(String taskId) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    public Room getRoomByName(String roomName) {
        return rooms.get(roomName);
    }

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    public SmartObject getObjectById(String objectId) {
        return objects.get(objectId);
    }

    public Collection<SmartObject> getAllObjects() {
        return objects.values();
    }

    // Action methods
    public void cleanRoom(String roomName) {
        Room room = rooms.get(roomName);
        if (room != null) {
            room.setClean(true);
            System.out.println("[ENV] Room '" + roomName + "' is now clean");
        }
    }

    public void markRoomDirty(String roomName) {
        Room room = rooms.get(roomName);
        if (room != null) {
            room.setClean(false);
            System.out.println("[ENV] Room '" + roomName + "' is now dirty");
        }
    }

    public boolean assignTaskToAgent(String taskId, String agentName) {
        Task task = getTaskById(taskId);
        if (task != null && task.isPending()) {
            task.setAssignedAgent(agentName);
            task.setStatus(Task.TaskStatus.ONGOING);
            System.out.println("[ENV] Task " + taskId + " assigned to " + agentName);
            return true;
        }
        return false;
    }

    public boolean completeTask(String taskId) {
        Task task = getTaskById(taskId);
        if (task != null && task.isOngoing()) {
            task.setStatus(Task.TaskStatus.COMPLETED);
            System.out.println("[ENV] Task " + taskId + " marked as completed");
            return true;
        }
        return false;
    }

    public boolean moveObject(String objectId, String newLocation) {
        SmartObject obj = objects.get(objectId);
        if (obj != null) {
            Room oldRoom = rooms.get(obj.getLocation());
            Room newRoom = rooms.get(newLocation);
            if (oldRoom != null && newRoom != null) {
                oldRoom.removeObject(obj);
                obj.setLocation(newLocation);
                newRoom.addObject(obj);
                System.out.println("[ENV] Object " + objectId + " moved to " + newLocation);
                return true;
            }
        }
        return false;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public boolean isSimulationComplete() {
        return timeStep >= MAX_TIME_STEPS || allTasksCompleted();
    }

    public boolean allTasksCompleted() {
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    public void printStatus() {
        System.out.println("\n=== Environment Status (T=" + timeStep + ") ===");
        System.out.println("Rooms: " + getDirtyRooms().size() + " dirty");
        System.out.println("Pending tasks: " + getPendingTasks().size());
        System.out.println("Ongoing tasks: " + getOngoingTasks().size());
        System.out.println("Completed tasks: " + getCompletedTasks().size());
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
