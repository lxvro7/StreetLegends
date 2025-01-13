package com.game;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Iterator;
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
        spawnTriggerY = GameConstants.INITIAL_PLAYER_Y -
                gameManager.getCanvasHeight() - gameManager.getCanvasHeight()/3;
    }

    public synchronized void update(double diffSeconds) {
            moveAllVehicles(diffSeconds);
            deleteVehicles();
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

    // TODO Lovro: Fix this, based off the players velocity
    public boolean isNewSpawnNeeded() {
        double canvasHeight = gameManager.getCanvasHeight();
        double playerY = player.getPlayerVehicle().getY();
        double playerVelocity = player.getPlayerVehicle().getVelocity();
        boolean withinTolerance = Math.abs(playerY - spawnTriggerY) < GameConstants.SPAWN_TRIGGER_TOLERANCE;
        if(withinTolerance) {
            System.out.println("SPAWN NEEDED");
            spawnTriggerY = playerY - canvasHeight - canvasHeight/2 - (playerVelocity * 0.1);
            return true;
        }
        return false;
    }

    // Delete all vehicles, that are below the player's canvas
    public void deleteVehicles() {
        double deleteTriggerY = player.getPlayerVehicle().getY()
                + gameManager.getCanvasHeight() + gameManager.getCanvasHeight()/2;
        Iterator<NPC> iterator = npcs.iterator();
        while(iterator.hasNext()) {
            NPC npc = iterator.next();
            if(Math.abs(npc.getNpcVehicle().getY() - deleteTriggerY) < GameConstants.SPAWN_TRIGGER_TOLERANCE) {
                iterator.remove();
            }
        }
    }

    // TODO Lovro:
    private boolean isConesSpawnNeeded() {
        return true;
    }

    // TODO Alton: Coin spawned auf einen der 4 lanes zufällig, calculateStreetLanes() benutzen
    private boolean isCoinSpawnNeeded() {
        return true;
    }

    public ArrayList<NPC> spawnTheNpcVehicles() {
        // Create north npcs
        ArrayList<NPC> newNorthNpcs = new ArrayList<>();
        int quantityNorth = calculateNpcNorthSpawningAmount();
        while(newNorthNpcs.size() < quantityNorth) {
            NPC generatedNorthNpc = generateUniqueNorthNpc(newNorthNpcs);
            if(generatedNorthNpc != null) {
                newNorthNpcs.add(generatedNorthNpc);
            }
            else {
                System.err.println("FAILED TO PLACE NORTH NPC AFTER MAXIMAL ATTEMPTS");
                break;
            }
         }
        // Create south npcs
        ArrayList<NPC> newSouthNpcs = new ArrayList<>();
        int quantitySouth = calculateNpcSouthSpawningAmount();
        while(newSouthNpcs.size() < quantitySouth) {
            NPC generatedSouthNpc = generateUniqueSouthNpc(newSouthNpcs);
            if(generatedSouthNpc != null) {
                newSouthNpcs.add(generatedSouthNpc);
            }
            else {
                System.err.println("FAILED TO PLACE SOUTH NPC AFTER MAXIMAL ATTEMPTS");
                break;
            }
        }
        ArrayList<NPC> newNpcs = new ArrayList<>();
        newNpcs.addAll(newNorthNpcs);
        newNpcs.addAll(newSouthNpcs);

        return newNpcs;
    }

    private NPC generateUniqueNorthNpc(ArrayList<NPC> existingNpcs) {
        final double SPAWN_OFFSET = GameConstants.SPAWN_OFFSET;
        final double SPAWN_RANGE  = GameConstants.SPAWN_RANGE;
        double[] streetLanes = calculateStreetLanes();
        int attempts = 0;
        while(attempts < GameConstants.MAX_NPC_ATTEMPTS) {
            // Random 3rd or 4th lane
            double randomXValue = streetLanes[random.nextInt(2) + 2];
            double y = spawnTriggerY + random.nextDouble() * SPAWN_RANGE - SPAWN_OFFSET;
            // Create random type and color
            VehicleType vehicleType  = VehicleType.values()[random.nextInt(VehicleType.values().length)];
            Vehicle newVehicle = new Vehicle(randomXValue, y, vehicleType, PlayerType.NPC_NORTH);
            if(hasNoCollisionWithExistingNpcs(existingNpcs, newVehicle)) {
                System.out.println("NPC spawned at X Value: " + randomXValue + " and Y Value: " + y);
                return new NPC(newVehicle);
            }
            attempts++;
        }
        return null;
    }

    private NPC generateUniqueSouthNpc(ArrayList<NPC> existingNpcs) {
        final double SPAWN_OFFSET = GameConstants.SPAWN_OFFSET;
        final double SPAWN_RANGE = GameConstants.SPAWN_RANGE;
        double[] streetLanes = calculateStreetLanes();
        int attempts = 0;
        while(attempts < GameConstants.MAX_NPC_ATTEMPTS) {
            // Random 1st or 2nd lane
            double randomXValue = streetLanes[random.nextInt(2)];
            double y = spawnTriggerY + random.nextDouble() * SPAWN_RANGE - SPAWN_OFFSET;

            VehicleType vehicleType  = VehicleType.values()[random.nextInt(VehicleType.values().length)];
            Vehicle newVehicle = new Vehicle(randomXValue, y, vehicleType, PlayerType.NPC_SOUTH);
            if(hasNoCollisionWithExistingNpcs(existingNpcs, newVehicle)) {
                return new NPC(newVehicle);
            }
            attempts++;
        }
        return null;
    }

    private double [] calculateStreetLanes() {
        final double STREET_WIDTH = gameManager.getCanvasWidth();
        final double firstLaneX = STREET_WIDTH * 0.275;
        final double secondLaneX = STREET_WIDTH * 0.38;
        final double thirdLaneX = STREET_WIDTH * 0.495;
        final double fourthLaneX = STREET_WIDTH * 0.61;

        return new double[]{ firstLaneX, secondLaneX, thirdLaneX, fourthLaneX };
    }

    private boolean hasNoCollisionWithExistingNpcs(ArrayList<NPC> existingNpcs, Vehicle newVehicle) {
        double collisionScalingFactor = switch (gameManager.getDifficulty()) {
            case "Easy" -> GameConstants.EASY_COLLISION_SCALING_FACTOR;
            case "Medium" -> GameConstants.MEDIUM_COLLISION_SCALING_FACTOR;
            case "Hard" -> GameConstants.HARD_COLLISION_SCALING_FACTOR;
            default -> GameConstants.FALLBACK_COLLISION_SCALING_FACTOR;
        };

        for (NPC existingNpc : existingNpcs) {
            for (Circle circle1 : existingNpc.getNpcVehicle().getCollisionCircles()) {
                for (Circle circle2 : newVehicle.getCollisionCircles()) {
                    double dx = circle1.getCenterX() - circle2.getCenterX();
                    double dy = circle1.getCenterY() - circle2.getCenterY();
                    double distance = circle1.getRadius() + circle2.getRadius() * collisionScalingFactor;
                    if (dx * dx + dy * dy < distance * distance) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int calculateNpcNorthSpawningAmount() {
        String difficulty = gameManager.getDifficulty();
        return switch (difficulty) {
            case "Easy"   -> GameConstants.EASY_DIFFICULTY_NPC_NORTH_AMOUNT;
            case "Middle" -> GameConstants.MIDDLE_DIFFICULTY_NPC_NORTH_AMOUNT;
            case "Hard"   -> GameConstants.HARD_DIFFICULTY_NPC_NORTH_AMOUNT;
            default       -> GameConstants.MIN_NPC_QUANTITY;
        };
    }

    private int calculateNpcSouthSpawningAmount() {
        String difficulty = gameManager.getDifficulty();
        return switch (difficulty) {
            case "Easy"   -> GameConstants.EASY_DIFFICULTY_NPC_SOUTH_AMOUNT;
            case "Middle" -> GameConstants.MIDDLE_DIFFICULTY_NPC_SOUTH_AMOUNT;
            case "Hard"   -> GameConstants.HARD_DIFFICULTY_NPC_SOUTH_AMOUNT;
            default       -> GameConstants.MIN_NPC_QUANTITY;
        };
    }

    public double getWorldPartY() {
        return worldPartY;
    }
}


