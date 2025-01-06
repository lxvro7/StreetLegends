package com.game;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class GameEngine {

    private Player player;
    private boolean running;
    private Consumer<ArrayList<Vehicle>> updateCallback;
    private final GameManager gameManager;
    private final KeyEventHandler keyEventHandler;
    private double canvasWidth;
    private double canvasHeight;
    private final GameUI gameUI;

    public GameEngine(String playerName, Vehicle.color selectedColor, String difficulty, GameUI gameUI,
                      double canvasHeight, double canvasWidth) {
        this.gameUI=gameUI;
        this.player = createPlayerVehicle(playerName, selectedColor, difficulty);
        this.gameManager = new GameManager(player, difficulty, canvasHeight, canvasWidth);
        this.keyEventHandler = new KeyEventHandler(player);
    }

    public void setUpdateCallback(Consumer<ArrayList<Vehicle>> callback) {
        this.updateCallback = callback;
    }

    public void startGameLoop() {
        running = true;
        Thread gameLoopThread = new Thread(() -> {
            long lastTime = System.nanoTime();
            while(running) {
                long currentTime = System.nanoTime();
                double diffSeconds = (currentTime - lastTime) / 1_000_000_000.0;
                lastTime = currentTime;
                gameManager.updateWorld(diffSeconds);
                gameManager.handleCollisions();
                Platform.runLater(() -> {
                    if (updateCallback != null) {
                        updateCallback.accept(gameManager.getAllVehicles());
                    }
                    System.out.println(player.getPlayerVehicle().getY());
                    if (gameManager.adjustWorld()) {
                        gameManager.drawBackground(gameUI.getBackgroundGraphicsContext(),
                                canvasWidth, canvasHeight);
                    }
                    if(gameManager.isNewSpawnNeeded()) {
                        System.out.println("CHANGE");
                        gameManager.addNewNpcs();
                        gameManager.drawVehicles(gameManager.getAllVehicles(), gameUI.getVehicleGraphicsContext(),
                                canvasWidth, canvasHeight);

                    }
                });
                // 60 FPS
                try {
                    Thread.sleep(GameConstants.FRAME_DELAY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        // Runs in background
        gameLoopThread.setDaemon(true);
        gameLoopThread.start();
    }

    // Handles the events, that occurred during the game
    public void processUserInput(KeyCode keyCode) {
        switch(keyCode) {
            case LEFT:
                keyEventHandler.onLeftArrowPressed();
                break;
            case RIGHT:
                keyEventHandler.onRightArrowPressed();
                break;
        }
    }

    public void processKeyRelease(KeyCode key) {
        switch (key) {
            case LEFT:
                player.setTurningLeft(false);
                break;
            case RIGHT:
                player.setTurningRight(false);
                break;
        }
        if(!player.isTurningLeft() && !player.isTurningRight()) {
            player.getPlayerVehicle().setAlfa(GameConstants.ROTATION_270_RAD);
        }
    }

    public void stopGame() {
        running = false;
        Platform.runLater(() -> {
            GameUI.showGameOverWindow(player.getPlayerName(), gameUI);
        });
    }

    // Creates a player vehicle for the specified color and difficulty
    private Player createPlayerVehicle(String playerName, Vehicle.color selectedColor, String difficulty) {
        Vehicle.type vehicleType = switch (difficulty) {
            case "Easy"   -> Vehicle.type.BIKE;
            case "Medium" -> Vehicle.type.CAR;
            case "Hard"   -> Vehicle.type.TRUCK;
            default -> null;
        };
        Random random = new Random();
        // Don't change these values
        double x = random.nextDouble(1400 - 540) + 540;
        double y = GameConstants.INITIAL_PLAYER_Y;
        player = new Player(playerName, new Vehicle(x, y, vehicleType, selectedColor, Vehicle.playerType.PLAYER));
        return player;
    }

    public void renderVehicles(GraphicsContext vgc, ArrayList<Vehicle> vehicles) {
        gameManager.drawVehicles(vehicles, vgc, canvasWidth, canvasHeight);
    }

    public void renderBackground(GraphicsContext bgc) {
        gameManager.drawBackground(bgc, canvasWidth, canvasHeight);
    }

    public void setCanvasWidth(double canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public void setCanvasHeight(double canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

}
