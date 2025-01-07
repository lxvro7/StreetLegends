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
    private final String difficulty;
    private final Player player;

    private final double canvasHeight;

    public GameManager(Player player, String difficulty, double canvasHeight, double canvasWidth, GameEngine gameEngine) {
        this.player = player;
        this.difficulty = difficulty;
        this.canvasHeight = canvasHeight;

        // Initialize game components
        this.gameLogic = new GameLogic(this);
        this.gameWorld = new GameWorld(this);
        this.gameRenderer = new GameRenderer(this);
        this.gameWorld.reset();
    }

    /**
     * Delegates the GameWorld methods
     * */

    public void updateWorld(double diffSeconds) {
        gameWorld.update(diffSeconds);
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return gameWorld.getAllVehicles();
    }

    public ArrayList<NPC> getAllNpcs() {
        return gameWorld.getAllNpcs();
    }

    public void addNewNpcs() {
        gameWorld.addNewNpcs();
    }

    public boolean isNewSpawnNeeded() {
        return gameWorld.isNewSpawnNeeded();
    }

    /**
     * Delegates the GameLogic methods
     */

    public boolean collisionDetected() {
        return gameLogic.collisionDetected();
    }

    public Player getPlayer() {
        return player;
    }

    public String getDifficulty() {
        return difficulty;
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

    public void drawVehicles(ArrayList<Vehicle> vehicles, GraphicsContext vehicleGraphicsContext, double canvasWidth, double canvasHeight) {
        gameRenderer.drawVehicles(vehicles, vehicleGraphicsContext, canvasWidth, canvasHeight);
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

}
