package com.smarthome.environment;

import java.util.*;

import com.smarthome.agent.Agent;

/**
 * Represents the smart apartment environment where agents operate.
 */
public class Environment {

    private Map<String, Room> rooms;
    private Map<String, SmartObject> objects;
    private List<Task> tasks;

    private Set<String> roomsWithTrash;
    private boolean laundryNeeded;
    private boolean centralTrashFull;

    private Random rand;
    private int timeStep;
    private static final int MAX_TIME_STEPS = 100;

    public Environment() {
        this.rooms = new HashMap<>();
        this.objects = new HashMap<>();
        this.tasks = new ArrayList<>();
        this.roomsWithTrash = new HashSet<>();
        this.rand = new Random();
        this.timeStep = 0;

        initializeRooms();
        initializeObjects();
        generateInitialTasks();
    }

    /* ---------- INITIALIZATION ---------- */

    private void initializeRooms() {
        String[] names = {"kitchen", "living_room", "bedroom", "bathroom"};
        for (String name : names) {
            rooms.put(name, new Room(name));
        }
    }

    private void initializeObjects() {
        SmartObject dishes = new SmartObject("dishes-1",
                SmartObject.ObjectType.DISHES, "kitchen", 5);
        dishes.setDirty(true);
        addObject(dishes);

        SmartObject garbage = new SmartObject("garbage-1",
                SmartObject.ObjectType.GARBAGE, "living_room", 3);
        addObject(garbage);

        SmartObject furniture = new SmartObject("furniture-1",
                SmartObject.ObjectType.FURNITURE, "bedroom", 1);
        addObject(furniture);

        SmartObject tools = new SmartObject("tools-1",
                SmartObject.ObjectType.CLEANING_TOOL, "kitchen", 3);
        addObject(tools);
    }

    private void addObject(SmartObject obj) {
        objects.put(obj.getId(), obj);
        rooms.get(obj.getLocation()).addObject(obj);
    }

    private void generateInitialTasks() {
        tasks.add(new Task("task-1", Task.TaskType.CLEAN_ROOM, "kitchen", 3, 10));
        tasks.add(new Task("task-2", Task.TaskType.CLEAN_ROOM, "living_room", 2, 8));
        tasks.add(new Task("task-3", Task.TaskType.WASH_DISHES, "kitchen", 3, 5));
        tasks.add(new Task("task-4", Task.TaskType.THROW_GARBAGE, "living_room", 2, 3));
        tasks.add(new Task("task-5", Task.TaskType.ORGANIZE_OBJECTS, "bedroom", 1, 7));
    }

    /* ---------- SIMULATION ---------- */

    public void simulateTimeStep() {
        timeStep++;

        for (Task task : tasks) {
            if (task.isOngoing()) {
                task.decreaseTime(1);
                if (task.getTimeRemaining() <= 0) {
                    task.setStatus(Task.TaskStatus.COMPLETED);
                }
            }
        }

        // Random environment changes
        for (Room room : rooms.values()) {
            if (rand.nextBoolean()) {
                roomsWithTrash.add(room.getName());
                room.setClean(false);
            }
        }

        laundryNeeded = rand.nextBoolean();
    }

    /* ---------- PERCEPTION ---------- */

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
        Set<Task> result = new HashSet<>();
        for (Task task : tasks) {
            if (task.isPending()) result.add(task);
        }
        return result;
    }

    public Set<Task> getOngoingTasks() {
        Set<Task> result = new HashSet<>();
        for (Task task : tasks) {
            if (task.isOngoing()) result.add(task);
        }
        return result;
    }

    public Set<Task> getCompletedTasks() {
        Set<Task> result = new HashSet<>();
        for (Task task : tasks) {
            if (task.isCompleted()) result.add(task);
        }
        return result;
    }

    /* ---------- ACTIONS ---------- */

    public boolean assignTaskToAgent(String taskId, String agentName) {
        Task task = getTaskById(taskId);
        if (task != null && task.isPending()) {
            task.setAssignedAgent(agentName);
            task.setStatus(Task.TaskStatus.ONGOING);
            return true;
        }
        return false;
    }

    public void cleanRoom(String roomName) {
        Room room = rooms.get(roomName);
        if (room != null) room.setClean(true);
    }

    public void emptyTrash(String room) {
        roomsWithTrash.remove(room);
        centralTrashFull = true;
    }

    public void takeOutTrash() {
        centralTrashFull = false;
    }

    /* ---------- GETTERS ---------- */

    public Task getTaskById(String id) {
        for (Task t : tasks) {
            if (t.getTaskId().equals(id)) return t;
        }
        return null;
    }

    public boolean isLaundryNeeded() {
        return laundryNeeded;
    }

    public Set<String> getRoomsWithTrash() {
        return new HashSet<>(roomsWithTrash);
    }

    public boolean isSimulationComplete() {
        return timeStep >= MAX_TIME_STEPS || allTasksCompleted();
    }

    private boolean allTasksCompleted() {
        for (Task t : tasks) {
            if (!t.isCompleted()) return false;
        }
        return true;
    }

    public void printStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printStatus'");
    }

    public String getTimeStep() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTimeStep'");
    }

    public List<Agent> getAllRooms() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRooms'");
    }
}
