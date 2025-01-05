package com.game;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    private final Player player;
    private final GameManager gameManager;
    private static final int SCROLL_BOUNDS = 500;

    private double worldPartY = 10000;

    private final ArrayList<NPC> npcs;

    public GameWorld(GameManager gameManager) {
        this.gameManager = gameManager;
        this.player = gameManager.getPlayer();
        this.npcs = new ArrayList<>();
    }

    public void update(double diffSeconds) {
        moveAllVehicles(diffSeconds);
    }

    // Spawn NPC vehicles
    public ArrayList<NPC> spawnTheNpcVehicles() {
        ArrayList<NPC> npcs = new ArrayList<>();
        Random random = new Random();
        double[] randomArray = new double[4];
        // Change this, to generate more or less npcs
        int quantity = random.nextInt(4) + 2;

        while (npcs.size() < quantity) {
            // Flag to check if NPC has been placed
            boolean placed = false;
            int attempts = 0;

            // Try placing the NPC up to 100 times to avoid endless loops
            while (!placed && attempts < 100) {
                double x = random.nextDouble()  * 220 + 540;
                double x2 = random.nextDouble() * 200 + 760;
                double x3 = random.nextDouble() * 180 + 990;
                double x4 = random.nextDouble() * 170 + 1200;

                randomArray[0] = x;
                randomArray[1] = x2;
                randomArray[2] = x3;
                randomArray[3] = x4;

                double randomXValue = randomArray[random.nextInt(4)];

                double y = player.getPlayerVehicle().getY() + (random.nextDouble() * 400 - 200);

                // Create a new vehicle with the generated position and random type/color
                Vehicle.type vehicleType = Vehicle.type.values()[random.nextInt(Vehicle.type.values().length)];
                Vehicle.color vehicleColor = Vehicle.color.values()[random.nextInt(Vehicle.color.values().length)];
                Vehicle newVehicle = new Vehicle(randomXValue, y, vehicleType, vehicleColor, Vehicle.playerType.NPC);

                // Check for collisions with existing NPCs
                boolean collision = false;
                for (NPC existingNpc : npcs) {
                    Vehicle existingVehicle = existingNpc.getNpcVehicle();
                    double dx = existingVehicle.getX() - newVehicle.getX();
                    double dy = existingVehicle.getY() - newVehicle.getY();
                    double distanceSquared = dx * dx + dy * dy;
                    double minDistance = existingVehicle.getRadius() + newVehicle.getRadius();
                    if (distanceSquared < minDistance * minDistance) {
                        collision = true;
                        break;
                    }
                }
                if (!collision) {
                    NPC npc = new NPC(newVehicle);
                    npcs.add(npc);
                    placed = true;
                }
                attempts++;
            }
            if (attempts >= 100) {
                System.err.println("MAXIMUM ATTEMPTS REACHED");
                break;
            }
        }
        return npcs;
    }
    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> allVehicles = new ArrayList<>();
        allVehicles.add(player.getPlayerVehicle());
        for (NPC npc : npcs) {
            allVehicles.add(npc.getNpcVehicle());
        }
        return allVehicles;
    }

    public boolean adjustWorld() {
        boolean worldHasChanged = false;

        if (player.getPlayerVehicle().getY() < worldPartY + SCROLL_BOUNDS) {
            worldPartY = player.getPlayerVehicle().getY() - SCROLL_BOUNDS;

            if (worldPartY <= 0) {
                worldPartY = 0;
            }
            worldHasChanged = true;
        }
        return worldHasChanged;
    }

    public ArrayList<NPC> getAllNpcs() {
        return npcs;
    }

    public void reset() {
        npcs.clear();
        npcs.addAll(spawnTheNpcVehicles());
    }

    public void moveAllVehicles(double diffSeconds) {
        player.getPlayerVehicle().move(diffSeconds);
        for(NPC npc : npcs) {
            npc.move(diffSeconds);
        }
    }

    public double getWorldPartY() {
        return worldPartY;
    }

}
