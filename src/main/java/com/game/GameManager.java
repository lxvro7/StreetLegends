package com.game;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class GameManager {
    private final GameLogic gameLogic;
    private final GameWorld gameWorld;
    private final GameRenderer gameRenderer;
    private final String difficulty;
    private Player player;
    private final GameEngine gameEngine;

    public GameManager(Player player, String difficulty, GameEngine gameEngine) {
        this.player = player;
        this.difficulty = difficulty;
        this.gameEngine = gameEngine;
        this.gameLogic = new GameLogic(this);
        this.gameWorld = new GameWorld(this);
        this.gameRenderer = new GameRenderer(this);
        this.gameWorld.reset();
    }

    public void updateWorld(double diffSeconds) {
        gameWorld.update(diffSeconds);
        gameLogic.handleCollisions();
    }

    public void drawVehicles(ArrayList<Vehicle> vehicles, GraphicsContext vehicleGraphicsContext, double canvasWidth, double canvasHeight) {
        gameRenderer.drawVehicles(vehicles, vehicleGraphicsContext, canvasWidth, canvasHeight);
    }

    public void drawBackground(GraphicsContext backgroundGraphicsContext, double canvasWidth, double canvasHeight) {
        gameRenderer.drawBackground(backgroundGraphicsContext, canvasWidth, canvasHeight);
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

    public void handleCollisions() {
        gameLogic.handleCollisions();
    }

    public Player getPlayer() {
        return player;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void stopGame() {
        gameEngine.stopGame();
    }
    public double getWorldPartY() {
        return gameWorld.getWorldPartY();
    }

    public boolean adjustWorld() {
        return gameWorld.adjustWorld();
    }
}
