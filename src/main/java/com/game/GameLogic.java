package com.game;

import java.util.ArrayList;


public class GameLogic {
    private Player player;
    private NPC npc;
    private GameWorld gameWorld;
    private GameHandler gameHandler;
    public GameLogic(Player player, NPC npc, GameWorld gameWorld, GameHandler gameHandler) {
        this.player = player;
        this.npc = npc;
        this.gameHandler=gameHandler;
        this.gameWorld=gameWorld;
    }


    // TODO Alton: Page 53 Script, work with this. This is only a template.
    public ArrayList<Vehicle> getCollision(Vehicle vehicle) {
        ArrayList<Vehicle> result = new ArrayList<>();
        // Calculate the size of all Vehicles in Game
        int len = gameWorld.getAllVehicles().size();
        for(int i = 0; i < len; i++) {
            Vehicle vehicle2 = gameWorld.getAllVehicles().get(i);
            if(vehicle==vehicle2) continue;
            // check if they vehicles touch each other
            double dist = vehicle.getRadius() + vehicle2.getRadius();
            double dx = Math.abs(vehicle.getX() - vehicle2.getX());
            double dy = Math.abs(vehicle.getY() - vehicle2.getY());
            if(dx<dist && dy<dist) {
                if(dx*dx+dy*dy < dist*dist) {
                    result.add(vehicle2);
                }
            }
        }
        return result;
    }

    public void handleCollisions(ArrayList<Vehicle> allVehicles) {
        Vehicle playerVehicle = player.getPlayerVehicle();

        for (NPC npc : gameWorld.getAllNpcs()) {
            Vehicle npcVehicle = npc.getNpcVehicle();

            double dx = Math.abs(playerVehicle.getX() - npcVehicle.getX());
            double dy = Math.abs(playerVehicle.getY() - npcVehicle.getY());

            if (dx < 55 && dy < 55) {
                System.out.println("Player collided with NPC!");
                gameHandler.stopGame(); // Spiel beenden
                break;
            }
        }

    }
}

