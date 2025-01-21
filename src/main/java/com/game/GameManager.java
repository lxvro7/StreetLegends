package com.game;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

/**
 * This class serves as the mediator between the game's core components.
 * Coordinating the interaction between the GameLogic, GameWorld and GameRenderer
 * @author lxvro7
 * @author bekoal01
 * */

public class GameManager {
    private final GameLogic gameLogic;
    private final GameWorld gameWorld;
    private final GameRenderer gameRenderer;
    private String difficulty;
    private final Player player;
    private final GameEngine gameEngine;

    private final double canvasHeight;
    private final double canvasWidth;

    public GameManager(Player player, String difficulty, double canvasHeight, double canvasWidth, GameEngine gameEngine) {
        this.player = player;
        this.difficulty=difficulty;
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.gameEngine=gameEngine;

        // Initialize game components
        this.gameLogic = new GameLogic(this);
        this.gameWorld = new GameWorld(this);
        this.gameRenderer = new GameRenderer(this);
    }

    /**
     * Delegates the GameWorld methods
     * */

    public synchronized void updateWorld(double diffSeconds) {
        gameWorld.update(diffSeconds);
    }

    public boolean isConeSpawnNeeded() {
        return gameLogic.isConesSpawnNeeded();
    }

    public void addNewObstacle() {
        gameWorld.addNewObstacle();
    }

    public ArrayList<NPC> getAllNpcs() {
        return gameWorld.getAllNpcs();
    }

    public synchronized void addNewNpcs() {
        gameWorld.addNewNpcs();
    }

    public boolean isNewSpawnNeeded() {
        return gameWorld.isNewSpawnNeeded();
    }

    public synchronized ArrayList<GameObject> getAllGameObjects() {
        return gameWorld.getAllGameObjects();
    }

    public ArrayList<Obstacle> getAllObstacles() {
        return gameWorld.getAllObstacles();
    }

    public void spawnCone() {
        gameWorld.spawnCone();
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        gameWorld.setObstacles(obstacles);
    }

    /**
     * Delegates the GameLogic methods
     */

    public boolean checkIfGameOver() {
        return gameLogic.checkIfGameOver();
    }

    public Player getPlayer() {
        return player;
    }

    public String getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty.toLowerCase();
    }

    public double getWorldPartY() {
        return gameWorld.getWorldPartY();
    }

    public boolean adjustWorld() {
        return gameWorld.adjustWorld();
    }

    /**
     * Delegates the GameRenderer methods
     * */

    public void drawObjects(ArrayList<GameObject> objects, GraphicsContext vehicleGraphicsContext, double canvasWidth, double canvasHeight) {
        gameRenderer.drawObjects(objects, vehicleGraphicsContext, canvasWidth, canvasHeight);
    }

    public void drawBackground(GraphicsContext backgroundGraphicsContext, double canvasWidth, double canvasHeight) {
        gameRenderer.drawBackground(backgroundGraphicsContext, canvasWidth, canvasHeight);
    }

    /**
     * Helper methods
     * */

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public int getDistanceTraveled() {
        return gameLogic.getDistanceTraveled();
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public void stopGameDueToOutOfBounds() {
        gameEngine.stopGame();
    }

    public double getCanvasWidth() {
        return canvasWidth;
    }
    public int getCoinCounter() {
        return gameLogic.getCoinCounter();
    }
}
