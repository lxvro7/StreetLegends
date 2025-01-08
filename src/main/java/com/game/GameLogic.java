package com.game;
import javafx.geometry.BoundingBox;

import java.util.ArrayList;


public class GameLogic {
    private final GameManager gameManager;
    private int distanceTraveled = 0;
    private int lastDistanceCheckpoint = 0; // Hält die letzte 100-Meter-Marke fest


    public GameLogic(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // TODO Lovro: Check bounding box for angle
    // Checks the collision between two vehicles
    public boolean isCollisionDetected(Vehicle vehicle, Vehicle vehicle2) {
        BoundingBox vehicleBounds = createBoundingBox(vehicle);
        BoundingBox vehicle2Bounds = createBoundingBox(vehicle2);
        return vehicleBounds.intersects(vehicle2Bounds);
    }

    // Game is over, if the player collides with a npc
    public boolean checkIfGameOver() {
        Player player = gameManager.getPlayer();
        Vehicle playerVehicle = player.getPlayerVehicle();
        ArrayList<NPC> npcs = gameManager.getAllNpcs();
        for(NPC npc : npcs) {
            Vehicle npcVehicle = npc.getNpcVehicle();
            if(isCollisionDetected(playerVehicle, npcVehicle)) {
                return true;
            }
        }
        return false;
    }

    private BoundingBox createBoundingBox(Vehicle vehicle) {
        return new BoundingBox(vehicle.getLeft(), vehicle.getTop(), vehicle.getWidth(), vehicle.getHeight());
    }
    public void increaseVelocity(String difficulty) {
        float increaseAmount = switch (difficulty) {
            case "Easy" -> GameConstants.EASY_DIFFICULTY_SPEED_INCREASE;
            case "Medium" -> GameConstants.MEDIUM_DIFFICULTY_SPEED_INCREASE;
            case "Hard" -> GameConstants.HARD_DIFFICULTY_SPEED_INCREASE;
            default -> {
                System.err.println("Unbekannter Schwierigkeitsgrad, Standardwert Easy verwendet.");
                yield GameConstants.EASY_DIFFICULTY_SPEED_INCREASE;
            }
        };
        float newVelocity = gameManager.getPlayer().getPlayerVehicle().getVelocity() + increaseAmount;
        if (newVelocity > GameConstants.PLAYER_CAR_MAX_VELOCITY) {
            newVelocity = GameConstants.PLAYER_CAR_MAX_VELOCITY;
        }
        gameManager.getPlayer().getPlayerVehicle().setVelocity(newVelocity);
        System.out.println("Aktuelle Geschwindigkeit: " + newVelocity);
    }

    public void update() {
        final double startPointY = GameConstants.INITIAL_PLAYER_Y;
        final double startPointX = GameConstants.INITIAL_PLAYER_X;
        double relativePlayerY = gameManager.getPlayer().getPlayerVehicle().getY();
        double relativePlayerX = gameManager.getPlayer().getPlayerVehicle().getX();
        double distanceInPixels = Math.sqrt(Math.pow(startPointX - relativePlayerX, 2)
                + Math.pow(relativePlayerY - startPointY, 2));
        double pixelsPerMeter = 10;
        distanceTraveled = (int) (distanceInPixels / pixelsPerMeter);

        if (distanceTraveled >= lastDistanceCheckpoint + 100) {
            increaseVelocity(gameManager.getDifficulty());
            lastDistanceCheckpoint += 100;
        }
    }
    public int getDistanceTraveled() {
        return distanceTraveled;
    }
}