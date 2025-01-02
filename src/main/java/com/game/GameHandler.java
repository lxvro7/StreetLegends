package com.game;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class GameHandler {

    private final ArrayList<NPC> npcs;
    private Player player;
    private boolean running;
    private Consumer<ArrayList<Vehicle>> updateCallback;
    private final KeyEventHandler keyEventHandler;

    public GameHandler(String playerName, Vehicle.color selectedColor, String difficulty) {
        npcs = new ArrayList<>();
        createPlayerVehicle(playerName, selectedColor, difficulty);
        keyEventHandler = new KeyEventHandler(player);
    }

    // Spawns NPC vehicles
    public void spawnNpcVehicles() {
        Random random = new Random();
        // Change the bound, to spawn more or less cars
        int quantity = random.nextInt(5) + 3;
        for(int i = 0; i < quantity; i++) {
            double x = random.nextDouble() * 1000;
            double y = random.nextDouble() * 1000;
            // Creates random vehicle types and colors
            Vehicle.type vehicleType = Vehicle.type.values()[random.nextInt(Vehicle.type.values().length)];
            Vehicle.color vehicleColor = Vehicle.color.values()[random.nextInt(Vehicle.type.values().length)];
            Vehicle vehicle = new Vehicle(x, y, vehicleType, vehicleColor, Vehicle.playerType.NPC);
            NPC npc = new NPC(vehicle);
            npcs.add(npc);
        }
    }

    public void createPlayerVehicle(String playerName, Vehicle.color selectedColor, String difficulty) {
        Vehicle.type vehicleType = null;
        switch (difficulty) {
            case "Easy":
                vehicleType = Vehicle.type.BIKE;
                break;
            case "Medium":
                vehicleType = Vehicle.type.CAR;
                break;
            case "Hard":
                vehicleType = Vehicle.type.TRUCK;
                break;
        }
        player = new Player(playerName, new Vehicle(300, 300, vehicleType, selectedColor, Vehicle.playerType.PLAYER));
    }

    public void setUpdateCallback(Consumer<ArrayList<Vehicle>> callback) {
        this.updateCallback = callback;
    }

    public void startGameLoop() {
        running = true;
        Thread gameLoopThread = new Thread(() -> {
            long lastTime = System.nanoTime();
            while(running) {
                // Don't change this
                long currentTime = System.nanoTime();
                double diffSeconds = (currentTime - lastTime) / 1_000_000_000.0;
                lastTime = currentTime;

                update(diffSeconds);


                moveAllVehicles(diffSeconds);
                if(updateCallback != null) {
                    Platform.runLater(() -> updateCallback.accept(getAllVehicles()));
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

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> allVehicles = new ArrayList<>();
        allVehicles.add(player.getPlayerVehicle());
        for (NPC npc : npcs) {
            allVehicles.add(npc.getNpcVehicle());
        }
        return allVehicles;
    }

    public void moveAllVehicles(double diffSeconds) {
        player.getPlayerVehicle().move(diffSeconds);
        for(NPC npc : npcs) {
            npc.move(diffSeconds);
        }
    }
    public void update(double diffSeconds) {
        // slowly if no key is pressed
        if (!keyEventHandler.isAnyKeyPressed()) {
            player.slowDown();
        }
        moveAllVehicles(diffSeconds);
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
}