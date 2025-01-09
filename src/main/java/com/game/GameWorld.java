package com.game;

import javafx.scene.shape.Circle;

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
    private double counter = 0;

    public GameWorld(GameManager gameManager) {
        this.gameManager = gameManager;
        this.player = gameManager.getPlayer();
        this.npcs = new ArrayList<>();
        spawnTriggerY = GameConstants.INITIAL_PLAYER_Y -
                gameManager.getCanvasHeight() - gameManager.getCanvasHeight()/3;
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

    // TODO: Refactor spawning mechanism, so it gets more fun to play!

    public ArrayList<NPC> spawnTheNpcVehicles() {
        ArrayList<NPC> newNpcs = new ArrayList<>();
        int quantity = calculateNpcSpawningAmount();
        while(newNpcs.size() < quantity) {
            NPC generatedNpc = generateUniqueNpc(newNpcs);
            if(generatedNpc != null) {
                newNpcs.add(generatedNpc);
            }
            else {
                System.err.println("FAILED TO PLACE NPC AFTER MAXIMAL ATTEMPTS");
                break;
            }
         }
        return newNpcs;
    }

    private NPC generateUniqueNpc(ArrayList<NPC> existingNpcs) {
        final double SPAWN_OFFSET = GameConstants.SPAWN_OFFSET;
        final double SPAWN_RANGE  = GameConstants.SPAWN_RANGE;
        final double [] LANES     = GameConstants.LANES;
        int attempts = 0;
        while(attempts < GameConstants.MAX_NPC_ATTEMPTS) {
            // Create random coordinates
            double randomXValue = LANES[random.nextInt(LANES.length)];
            double y = spawnTriggerY + random.nextDouble() * SPAWN_RANGE - SPAWN_OFFSET;
            // Create random type and color
            VehicleType vehicleType  = VehicleType.values()[random.nextInt(VehicleType.values().length)];
            Vehicle newVehicle = new Vehicle(randomXValue, y, vehicleType, PlayerType.NPC);
            if(!hasCollisionWithExistingNpcs(existingNpcs, newVehicle)) {
                return new NPC(newVehicle);
            }
            attempts++;
        }
        return null;
    }

    private boolean hasCollisionWithExistingNpcs(ArrayList<NPC> existingNpcs, Vehicle newVehicle) {
        final double COLLISION_SCALING_FACTOR = 4;
        for (NPC existingNpc : existingNpcs) {
            for (Circle circle1 : existingNpc.getNpcVehicle().getCollisionCircles()) {
                for (Circle circle2 : newVehicle.getCollisionCircles()) {
                    double dx = circle1.getCenterX() - circle2.getCenterX();
                    double dy = circle1.getCenterY() - circle2.getCenterY();
                    double distance = circle1.getRadius() + circle2.getRadius() * COLLISION_SCALING_FACTOR;
                    if (dx * dx + dy * dy < distance * distance) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    public double getWorldPartY() {
        return worldPartY;
    }
}


