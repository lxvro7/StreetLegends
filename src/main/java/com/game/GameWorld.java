package com.game;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    private final Player player;
    private final GameManager gameManager;
    private static final int SCROLL_BOUNDS = GameConstants.SCROLL_BOUNDS;
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
    public ArrayList<NPC> spawnTheNpcVehicles(String difficulty) {
        ArrayList<NPC> npcs = new ArrayList<>();
        Random random = new Random();
        int quantity;
        switch (difficulty) {
            case "Easy":
                quantity = 3;
                break;
            case "Medium":
                quantity = 5;
                break;
            case "Hard":
                quantity = 10;
                break;
            default:
                quantity = GameConstants.MIN_NPC_QUANTITY;
        }

        while (npcs.size() < quantity) {
            boolean placed = false;
            int attempts = 0;

            while (!placed && attempts < GameConstants.MAX_NPC_ATTEMPTS) {
                double randomXValue = GameConstants.LANES[random.nextInt(GameConstants.LANES.length)];
                double y = player.getPlayerVehicle().getY() + random.nextDouble() * 400 - 200;

                Vehicle.type vehicleType = Vehicle.type.values()[random.nextInt(Vehicle.type.values().length)];
                Vehicle.color vehicleColor = Vehicle.color.values()[random.nextInt(Vehicle.color.values().length)];
                Vehicle newVehicle = new Vehicle(randomXValue, y, vehicleType, vehicleColor, Vehicle.playerType.NPC);

                boolean collision = false;
                for (NPC existingNpc : npcs) {
                    Vehicle existingVehicle = existingNpc.getNpcVehicle();
                    double dy = Math.abs(existingVehicle.getY() - newVehicle.getY());
                    double minYDistance = existingVehicle.getRadius() + newVehicle.getRadius() + 100;

                    if (dy < minYDistance) {
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

            if (attempts >= GameConstants.MAX_NPC_ATTEMPTS) {
                System.err.println("MAXIMUM ATTEMPTS REACHED FOR NPC SPAWN");
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
        npcs.addAll(spawnTheNpcVehicles(gameManager.getDifficulty()));
    }

    public void moveAllVehicles(double diffSeconds) {
        player.getPlayerVehicle().move(diffSeconds);
        for (NPC npc : npcs) {
            npc.move(diffSeconds);
        }
    }

    public double getWorldPartY() {
        return worldPartY;
    }
}
