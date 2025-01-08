package com.game;
import javafx.geometry.BoundingBox;

import java.util.ArrayList;


public class GameLogic {
    private final GameManager gameManager;
    private int distanceTraveled = 0;

    public GameLogic(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // TODO Lovro: Check bounding box for angle
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
        final double startPointY = GameConstants.INITIAL_PLAYER_Y;
        final double startPointX = GameConstants.INITIAL_PLAYER_X;
        double relativePlayerY = gameManager.getPlayer().getPlayerVehicle().getY();
        double relativePlayerX = gameManager.getPlayer().getPlayerVehicle().getX();
        double distanceInPixels = (Math.sqrt(Math.pow(startPointX - relativePlayerX, 2)
                + Math.pow(relativePlayerY - startPointY, 2)));
        double pixelsPerMeter = 10;
        distanceTraveled = (int) (distanceInPixels/pixelsPerMeter);
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    // TODO: Velocity should get bigger, if the player distance is f.e a specified point
}