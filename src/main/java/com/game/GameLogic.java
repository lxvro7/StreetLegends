package com.game;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Iterator;


public class GameLogic {
    private final GameManager gameManager;
    private int distanceTraveled = 0;
    private int lastDistanceCheckpointVelocity = 0;
    private int coinCounter = 0;
    private boolean isConesSpawnNeeded = false;
    private int coinBonus = 0;

    private boolean isOutOfBounds(Vehicle vehicle) {
        double vehicleX = vehicle.getX();
        return vehicleX < 0.3 * gameManager.getCanvasWidth() || vehicleX > 0.7 * gameManager.getCanvasWidth();
    }

    public GameLogic(GameManager gameManager) {
        this.gameManager = gameManager;
        String difficulty=gameManager.getDifficulty();
        checkDifficulty(difficulty);
        int velocityNow= checkDifficulty(difficulty);
        gameManager.getPlayer().getPlayerVehicle().setVelocity(velocityNow);
    }

    // Checks the collision between two vehicles
    public boolean isCollisionDetected(Vehicle vehicle1, Vehicle vehicle2) {
        for (Circle circle1 : vehicle1.getCollisionCircles()) {
            for (Circle circle2 : vehicle2.getCollisionCircles()) {
                double dx = circle1.getCenterX() - circle2.getCenterX();
                double dy = circle1.getCenterY() - circle2.getCenterY();
                double distance = circle1.getRadius() + circle2.getRadius();
                if (dx * dx + dy * dy < distance * distance) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCollisionDetected(Vehicle vehicle, Obstacle obstacle) {
        for (Circle circle1 : vehicle.getCollisionCircles()) {
            for (Circle circle2 : obstacle.getCollisionCircles()) {
                double dx = circle1.getCenterX() - circle2.getCenterX();
                double dy = circle1.getCenterY() - circle2.getCenterY();
                double distance = circle1.getRadius() + circle2.getRadius();
                if (dx * dx + dy * dy < distance * distance) {
                    return true;
                }
            }
        }
        return false;
    }

    // Game is over, if the player collides with a npc or obstacle
    public synchronized boolean checkIfGameOver() {
        Player player = gameManager.getPlayer();
        Vehicle playerVehicle = player.getPlayerVehicle();
        ArrayList<NPC> npcs = gameManager.getAllNpcs();
        for(NPC npc : npcs) {
            Vehicle npcVehicle = npc.getNpcVehicle();
            if(isCollisionDetected(playerVehicle, npcVehicle)) {
                return true;
            }
        }
        ArrayList<Obstacle> obstacles = gameManager.getAllObstacles();
        Iterator<Obstacle> obstacleIterator = obstacles.iterator();
        while(obstacleIterator.hasNext()) {
            Obstacle obstacle = obstacleIterator.next();
            if (isCollisionDetected(playerVehicle, obstacle)) {
                // CHECK IF COLLISION IS A CONE OR A COIN
                if(obstacle.getObstacleType() == Obstacle.ObstacleType.CONE) {
                    return true;
                }
                if(obstacle.getObstacleType() == Obstacle.ObstacleType.COIN) {
                    coinCounter++;
                    coinBonus += 200;
                    obstacleIterator.remove();
                    gameManager.setObstacles(obstacles);
                }
            }
        }
        // Check if player is out of bounds
        if(isOutOfBounds(playerVehicle)) {
            return true;
        }

        return false;
    }


    private void increaseVelocity(String difficulty) {
        float increaseAmount = switch (difficulty.toLowerCase()) {
            case "easy" -> GameConstants.EASY_DIFFICULTY_SPEED_INCREASE;
            case "medium" -> GameConstants.MEDIUM_DIFFICULTY_SPEED_INCREASE;
            case "hard" -> GameConstants.HARD_DIFFICULTY_SPEED_INCREASE;
            default -> {
                System.err.println("UNKNOWN DIFFICULTY, DIFFICULTY EASY USED");
                yield GameConstants.EASY_DIFFICULTY_SPEED_INCREASE;
            }
        };
        float newVelocity = gameManager.getPlayer().getPlayerVehicle().getVelocity() + increaseAmount;
        if (newVelocity > GameConstants.PLAYER_CAR_MAX_VELOCITY) {
            newVelocity = GameConstants.PLAYER_CAR_MAX_VELOCITY;
        }
        gameManager.getPlayer().getPlayerVehicle().setVelocity(newVelocity);
        System.out.println("Current velocity: " + newVelocity);
    }
    public int checkDifficulty(String difficulty) {
        return switch(difficulty.toLowerCase()) {
            case "easy" -> GameConstants.PLAYER_CAR_EASY_START;
            case "medium" -> GameConstants.PLAYER_CAR_Medium_START;
            case "hard" -> GameConstants.PLAYER_CAR_Hard_START;
            default -> {
                System.err.println("UNKNOWN DIFFICULTY, DIFFICULTY EASY USED");
                yield GameConstants.EASY_DIFFICULTY_SPEED_INCREASE;
            }
        };
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

        if (distanceTraveled >= lastDistanceCheckpointVelocity + 400) {
            lastDistanceCheckpointVelocity += 400;
            String difficulty = gameManager.getDifficulty();
            increaseVelocity(gameManager.getDifficulty());
        }

    }

    public boolean isConesSpawnNeeded() {
        return isConesSpawnNeeded;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public int getScore() {
        return distanceTraveled + coinBonus;
    }

    public int getCoinBonus() {
        return coinBonus;
    }

    public int getCoinCounter() {
        return coinCounter;
    }

}