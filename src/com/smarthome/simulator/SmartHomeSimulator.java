package com.smarthome.simulator;

import com.smarthome.agent.*;
import com.smarthome.environment.*;
import java.util.*;

/**
 * SmartHomeSimulator: Main simulator that orchestrates the multi-agent system
 * Runs the simulation loop and coordinates all agents
 */
public class SmartHomeSimulator {
    private Environment environment;
    private List<Agent> agents;
    private CoordinatorAgent coordinator;
    private int simulationSteps;
    private int maxSteps;
    private boolean verbose;

    public SmartHomeSimulator(int maxSteps) {
        this.environment = new Environment();
        this.agents = new ArrayList<>();
        this.simulationSteps = 0;
        this.maxSteps = maxSteps;
        this.verbose = true;
        this.coordinator = new CoordinatorAgent("Coordinator", environment);
        agents.add(coordinator);
    }

    /**
     * Add an agent to the system
     */
    public void addAgent(Agent agent) {
        agents.add(agent);
        if (agent != coordinator) {
            coordinator.registerAgent(agent.getAgentName());
        }
    }

    /**
     * Run a single simulation step
     */
    public void step() {
        if (simulationSteps >= maxSteps || environment.isSimulationComplete()) {
            return;
        }

        simulationSteps++;
        
        if (verbose) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("SIMULATION STEP: " + simulationSteps);
            System.out.println("=".repeat(60));
        }

        // Agents perceive, deliberate, and act
        for (Agent agent : agents) {
            if (agent.isActive()) {
                agent.runCycle();
            }
        }

        // Environment evolves
        environment.simulateTimeStep();

        // Print status
        if (verbose) {
            printAgentStatus();
            environment.printStatus();
        }
    }

    /**
     * Run the complete simulation
     */
    public void run() {
        System.out.println("╔" + "═".repeat(58) + "╗");
        System.out.println("║" + " ".repeat(58) + "║");
        System.out.println("║" + centerString("SMART HOME MULTI-AGENT SYSTEM SIMULATION", 58) + "║");
        System.out.println("║" + " ".repeat(58) + "║");
        System.out.println("╚" + "═".repeat(58) + "╝");

        System.out.println("\n📋 Initial Configuration:");
        System.out.println("   Agents: " + (agents.size() - 1));  // Exclude coordinator
        System.out.println("   Max Steps: " + maxSteps);
        System.out.println("   Environment: Smart Apartment (4 rooms)");
        
        // Print agent roster
        System.out.println("\n🤖 Agent Roster:");
        for (Agent agent : agents) {
            if (!(agent instanceof CoordinatorAgent)) {
                System.out.println("   - " + agent.getAgentName() + " (" + agent.getRole().getDisplayName() + ")");
            }
        }

        // Run simulation
        while (simulationSteps < maxSteps && !environment.isSimulationComplete() && hasActiveAgents()) {
            step();
        }

        // Final report
        printFinalReport();
    }

    /**
     * Check if there are any active agents
     */
    private boolean hasActiveAgents() {
        for (Agent agent : agents) {
            if (agent.isActive() && agent.getEnergy() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print agent status information
     */
    private void printAgentStatus() {
        System.out.println("\n🔍 Agent Status:");
        for (Agent agent : agents) {
            if (!(agent instanceof CoordinatorAgent)) {
                System.out.println(String.format("   %-15s [Energy: %3d] [Intentions: %d] [Active: %s]",
                        agent.getAgentName(),
                        agent.getEnergy(),
                        agent.getIntentions().getIntentionCount(),
                        agent.isActive() ? "✓" : "✗"));
            }
        }
    }

    /**
     * Print final simulation report
     */
    private void printFinalReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SIMULATION COMPLETED");
        System.out.println("=".repeat(60));

        System.out.println("\n📊 Final Statistics:");
        System.out.println("   Total Steps: " + simulationSteps);
        System.out.println("   Final Time: " + environment.getTimeStep());

        System.out.println("\n📝 Task Summary:");
        System.out.println("   Pending: " + environment.getPendingTasks().size());
        System.out.println("   Ongoing: " + environment.getOngoingTasks().size());
        System.out.println("   Completed: " + environment.getCompletedTasks().size());

        System.out.println("\n🏠 Environment State:");
        System.out.println("   Dirty Rooms: " + environment.getDirtyRooms().size());
        System.out.println("   Total Rooms: " + environment.getAllRooms().size());

        System.out.println("\n⚡ Agent Energy Levels:");
        for (Agent agent : agents) {
            if (!(agent instanceof CoordinatorAgent)) {
                System.out.println(String.format("   %s: %d / 100", 
                        agent.getAgentName(), agent.getEnergy()));
            }
        }

        System.out.println("\n✨ Simulation Status: " + 
            (environment.isSimulationComplete() ? "SUCCESS" : "INCOMPLETE"));
    }

    private String centerString(String str, int width) {
        if (str.length() >= width) return str;
        int totalPadding = width - str.length();
        int leftPadding = totalPadding / 2;
        return " ".repeat(leftPadding) + str + " ".repeat(totalPadding - leftPadding);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public List<Agent> getAgents() {
        return new ArrayList<>(agents);
    }

    public int getSimulationSteps() {
        return simulationSteps;
    }

    /**
     * Main entry point for the simulator
     */
    public static void main(String[] args) {
        // Create simulator
        SmartHomeSimulator simulator = new SmartHomeSimulator(30);

        // Add BDI agents
        simulator.addAgent(new CleaningAgent("Cleaner-1", simulator.getEnvironment()));
        simulator.addAgent(new DishwasherAgent("Washer-1", simulator.getEnvironment()));
        simulator.addAgent(new GarbageCollectorAgent("Collector-1", simulator.getEnvironment()));
        simulator.addAgent(new OrganizerAgent("Organizer-1", simulator.getEnvironment()));

        // Add reactive agents
        simulator.addAgent(new ReactiveAgent("ReactiveBot-1", AgentRole.CLEANER, simulator.getEnvironment()));

        // Run simulation
        simulator.run();
    }
}