package com.game;

import java.util.ArrayList;


public class GameLogic {
    private final GameManager gameManager;

    public GameLogic(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public ArrayList<Vehicle> getCollision() {
        ArrayList<Vehicle> collisions = new ArrayList<>();
        Player player = gameManager.getPlayer();
        for(NPC npc : gameManager.getAllNpcs()) {
            Vehicle npcVehicle = npc.getNpcVehicle();

            // Check if the vehicles touch each other
            double dist = player.getPlayerVehicle().getRadius() + npcVehicle.getRadius();
            double dx = Math.abs(player.getPlayerVehicle().getX() - npcVehicle.getX());
            double dy = Math.abs(player.getPlayerVehicle().getY() - npcVehicle.getY());

            if(dx<dist && dy<dist) {
                if(dx*dx+dy*dy < dist*dist) {
                    collisions.add(npcVehicle);
                }
            }
        }
        return collisions;
    }

    public void handleCollisions() {
        ArrayList<Vehicle> collisions = getCollision();
        // If the collision list is not empty, stop the game
        if(!collisions.isEmpty()) {
            // TODO: Call stop game in GameEngine
            System.out.println("Game terminated");
        }
    }
}

