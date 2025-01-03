package com.game;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class GameHandler {

    private Player player;
    private boolean running;
    private Consumer<ArrayList<Vehicle>> updateCallback;
    private final KeyEventHandler keyEventHandler;
    private final GameWorld gameWorld;

    public GameHandler(String playerName, Vehicle.color selectedColor, String difficulty) {
        player = createPlayerVehicle(playerName, selectedColor, difficulty);
        keyEventHandler = new KeyEventHandler(player);
        gameWorld = new GameWorld(player);
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
                // Rendering
                gameWorld.update(diffSeconds);

                if(updateCallback != null) {
                    Platform.runLater(() -> updateCallback.accept(gameWorld.getAllVehicles()));
                }
                // 60 FPS
                try {
                    Thread.sleep(16);
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
            case UP:
                keyEventHandler.onUpArrowPressed();
                break;
            case DOWN:
                keyEventHandler.onDownArrowPressed();
                break;
            case LEFT:
                keyEventHandler.onLeftArrowPressed();
                break;
            case RIGHT:
                keyEventHandler.onRightArrowPressed();
                break;
            case Q:
                keyEventHandler.onQPressed();
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
            player.getPlayerVehicle().setAlfa(3 * Math.PI/2);
        }
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
        double y = random.nextDouble(1900);
        player = new Player(playerName, new Vehicle(x, y, vehicleType, selectedColor, Vehicle.playerType.PLAYER));
        return player;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }
}
