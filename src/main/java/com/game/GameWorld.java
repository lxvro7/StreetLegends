package com.game;

import java.util.ArrayList;
import java.util.Random;
import static com.game.Vehicle.VehicleType;
import static com.game.Vehicle.PlayerType;

/**
 * Represents the Game World, including the spawning of NPCs, vehicle movement,
 * and adjustments of the world, relative of the player's position.
 *
 * @author lxvro7
 * @author bekoal01
 * */

public class GameWorld {
    private final Player player;
    private final GameManager gameManager;
    private double worldPartY = GameConstants.INITIAL_WORLD_PART_Y;
    private final ArrayList<NPC> npcs;
    private final Random random = new Random();
    private double spawnTriggerY;

    public GameWorld(GameManager gameManager) {
        this.gameManager = gameManager;
        this.player = gameManager.getPlayer();
        this.npcs = new ArrayList<>();
        spawnTriggerY = GameConstants.INITIAL_PLAYER_Y - gameManager.getCanvasHeight() - gameManager.getCanvasHeight()/3;
    }

    public void update(double diffSeconds) {
        moveAllVehicles(diffSeconds);
        gameManager.getGameLogic().update();
    }

    public void addNewNpcs() {
        ArrayList<NPC> newNpcs = spawnTheNpcVehicles();
        npcs.addAll(newNpcs);
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
        if (player.getPlayerVehicle().getY() < worldPartY + GameConstants.SCROLL_BOUNDS) {
            worldPartY = player.getPlayerVehicle().getY() - GameConstants.SCROLL_BOUNDS;
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

    public void reset() {}

    public void moveAllVehicles(double diffSeconds) {
        player.getPlayerVehicle().move(diffSeconds);
        for (NPC npc : npcs) {
            npc.move(diffSeconds);
        }
    }

    public boolean isNewSpawnNeeded() {
        double canvasHeight = gameManager.getCanvasHeight();
        double playerY = player.getPlayerVehicle().getY();
        boolean withinTolerance = Math.abs(playerY - spawnTriggerY) < GameConstants.SPAWN_TRIGGER_TOLERANCE;
        if(withinTolerance) {
            spawnTriggerY = playerY - canvasHeight - canvasHeight/2;
            return true;
        }
        return false;
    }

    public ArrayList<NPC> spawnTheNpcVehicles() {
        ArrayList<NPC> newNpcs = new ArrayList<>();
        int quantity = calculateNpcSpawningAmount();
        while(newNpcs.size() < quantity) {
            NPC npc = generateUniqueNpc(newNpcs);
            if (npc != null) {
                newNpcs.add(npc);
            } else {
                System.err.println("FAILED TO PLACE NPC AFTER MAXIMAL ATTEMPTS");
                break;
            }
        }
        return newNpcs;
    }

    // TODO: Radius berücksichtigen beim spawnen für Koordinate x!
    private NPC generateUniqueNpc(ArrayList<NPC> existingNpcs) {
        final int MAX_ATTEMPTS    = GameConstants.MAX_NPC_ATTEMPTS;
        final double SPAWN_OFFSET = GameConstants.SPAWN_OFFSET;
        final double SPAWN_RANGE  = GameConstants.SPAWN_RANGE;
        final double [] LANES     = GameConstants.LANES;
        int attempts = 0;
        while(attempts < MAX_ATTEMPTS) {
            // Create random coordinates
            double randomXValue = LANES[random.nextInt(LANES.length)];
            double y = spawnTriggerY + random.nextDouble() * SPAWN_RANGE - SPAWN_OFFSET;
            // Create random type and color
            VehicleType vehicleType  = VehicleType.values()[random.nextInt(VehicleType.values().length)];
            System.out.println("Vehicle " + vehicleType.toString() + " Spawned at coordinates: " + y + randomXValue);

            Vehicle newVehicle = new Vehicle(randomXValue, y, vehicleType, PlayerType.NPC);
            if(!hasCollisionWithExistingNpcs(existingNpcs, newVehicle)) {
                return new NPC(newVehicle);
            }
            attempts++;
        }
        return null;
    }

    private int calculateNpcSpawningAmount() {
        String difficulty = gameManager.getDifficulty();
        return switch (difficulty) {
            case "Easy"   -> GameConstants.EASY_DIFFICULTY_NPC_AMOUNT;
            case "Middle" -> GameConstants.MIDDLE_DIFFICULTY_NPC_AMOUNT;
            case "Hard"   -> GameConstants.HARD_DIFFICULTY_NPC_AMOUNT;
            default       -> GameConstants.MIN_NPC_QUANTITY;
        };
    }

    private boolean hasCollisionWithExistingNpcs(ArrayList<NPC> newNpcs, Vehicle newVehicle) {
        final double NPC_PADDING  = GameConstants.NPC_COLLISION_PADDING;
        for (NPC existingNpc : newNpcs) {
            Vehicle existingVehicle = existingNpc.getNpcVehicle();
            double dy = Math.abs(existingVehicle.getY() - newVehicle.getY());
            double minYDistance = existingVehicle.getRadius() + newVehicle.getRadius() + NPC_PADDING;
            if (dy < minYDistance) {
                return true;
            }
        }
        return false;
    }

    public double getWorldPartY() {
        return worldPartY;
    }
}


