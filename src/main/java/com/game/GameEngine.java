package com.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GameEngine {

    private Player player;
    private boolean running;
    private Consumer<ArrayList<Vehicle>> updateCallback;
    private final GameManager gameManager;
    private final KeyEventHandler keyEventHandler;
    private double canvasWidth;
    private double canvasHeight;
    private final UserInterface userInterface;
    private enum GameState {RUNNING, GAME_OVER}
    private GameState currentState = GameState.RUNNING;
    private AnimationTimer gameLoop;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public GameEngine(String playerName, String difficulty, UserInterface userInterface, double canvasHeight, double canvasWidth) {
        this.userInterface = userInterface;
        this.player = createPlayerVehicle(playerName);
        this.gameManager = new GameManager(player, difficulty, canvasHeight,canvasWidth,this);
        this.keyEventHandler = new KeyEventHandler(player);
    }

    public void setUpdateCallback(Consumer<ArrayList<Vehicle>> callback) {
        this.updateCallback = callback;
    }

    public void startGameLoop() {
        running = true;
        gameLoop = new AnimationTimer() {
            private long lastTime = 0;
            private int frameCount = 0;
            private long fpsLastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    fpsLastTime = now;
                    return;
                }

                double diffSeconds = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                // Calculate FPS
                frameCount++;
                if (now - fpsLastTime >= 1_000_000_000L) {
                    int currentFps = frameCount;
                    frameCount = 0;
                    fpsLastTime = now;

                    System.out.println("FPS: " + currentFps);
                }

                // Game logic in separate thread
                executorService.submit(() -> {
                    gameManager.updateWorld(diffSeconds);

                    if (gameManager.isNewSpawnNeeded()) {
                        gameManager.addNewNpcs();
                    }
                    if (gameManager.checkIfGameOver()) {
                        stopGame();
                        stop();
                    }
                });

                // Rendering on the UI thread
                Platform.runLater(() -> {
                    if (updateCallback != null) {
                        updateCallback.accept(gameManager.getAllVehicles());
                    }

                    if (gameManager.adjustWorld()) {
                        gameManager.drawBackground(userInterface.getBackgroundGraphicsContext(),
                                canvasWidth, canvasHeight);
                    }
                    gameManager.drawVehicles(gameManager.getAllVehicles(),
                            userInterface.getVehicleGraphicsContext(), canvasWidth, canvasHeight);
                });
            }
        };
        gameLoop.start();
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
            player.getPlayerVehicle().setVehicleImage(player.getDefaultImage());
        }
    }

    public void stopGame() {
        if (currentState != GameState.GAME_OVER) {
            currentState = GameState.GAME_OVER;
            running = false;
            Platform.runLater(() -> {
                int finalDistance = getDistanceTraveled();
                userInterface.showGameOverWindow(finalDistance);
            });
            executorService.shutdown();
            gameLoop.stop();
        }
    }

    // Creates a player vehicle for the specified color and difficulty
    private Player createPlayerVehicle(String playerName) {
        Vehicle.VehicleType vehicleType = Vehicle.VehicleType.MUSTANG;
        double x = GameConstants.INITIAL_PLAYER_X;
        double y = GameConstants.INITIAL_PLAYER_Y;
        player = new Player(playerName, new Vehicle(x, y, vehicleType, Vehicle.PlayerType.PLAYER));
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

    public int getDistanceTraveled() {
        return gameManager.getDistanceTraveled();
    }

}
