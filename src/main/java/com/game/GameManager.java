package com.game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class GameManager {
    private final GameLogic gameLogic;
    private final GameWorld gameWorld;
    private final GameRenderer gameRenderer;
    private final Player player;

    public GameManager(Player player) {
        this.player = player;

        // Initialize game components
        this.gameLogic = new GameLogic(this);
        this.gameWorld = new GameWorld(this);
        this.gameRenderer = new GameRenderer(this);

        this.gameWorld.reset();
    }

    // Delegates the game renderer methods

    public void drawVehicles(ArrayList<Vehicle> vehicles, GraphicsContext vehicleGraphicsContext, double canvasWidth, double canvasHeight) {
        gameRenderer.drawVehicles(vehicles, vehicleGraphicsContext, canvasWidth, canvasHeight);
    }

    public void drawBackground(GraphicsContext backgroundGraphicsContext, double canvasWidth, double canvasHeight) {
        gameRenderer.drawBackground(backgroundGraphicsContext, canvasWidth, canvasHeight);
    }

    // Delegates the game world methods

    public void updateWorld(double diffSeconds) {
        gameWorld.update(diffSeconds);
        gameLogic.getCollision();
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return gameWorld.getAllVehicles();
    }

    public ArrayList<NPC> getAllNpcs() {
        return gameWorld.getAllNpcs();
    }

    public void resetWorld() {
        gameWorld.reset();
    }

    public void moveAllVehicles(double diffSeconds) {
        gameWorld.moveAllVehicles(diffSeconds);
    }

    public ArrayList<NPC> spawnNpcVehicles() {
        return gameWorld.spawnTheNpcVehicles();
    }

    // Delegates the game logic methods

    public ArrayList<Vehicle> getCollisions() {
        return gameLogic.getCollision();
    }

    public void handleCollisions() {
        gameLogic.handleCollisions();
    }

    public Player getPlayer() {
        return player;
    }

    public double getCanvasHeight() {
        return 1048;
    }

    public double getWorldPartY() {
        return gameWorld.getWorldPartY();
    }

    public boolean adjustWorld() {
        return gameWorld.adjustWorld();
    }

}
