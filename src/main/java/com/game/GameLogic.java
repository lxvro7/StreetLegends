package com.game;
import javafx.geometry.BoundingBox;

import java.util.ArrayList;


public class GameLogic {
    private final GameManager gameManager;
    private int distanceTraveled = 0;

    public GameLogic(GameManager gameManager) {
        this.gameManager = gameManager;
    }

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

    public void update() {
        distanceTraveled += 1;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    // TODO: Velocity should get bigger, if the player distance is f.e a specified point
}