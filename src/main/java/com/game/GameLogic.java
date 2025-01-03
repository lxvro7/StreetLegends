package com.game;

import java.util.ArrayList;

// TODO: You need to work with this class, so that we get collisions between Player.Vehicles and NPC.Vehicles
//  NOT NPC.Vehicles and NPC.Vehicles

public class GameLogic {
    private Player player;
    private NPC npc;
    private GameWorld gameWorld;
    public GameLogic(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
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
    // TODO Alton: If you fixed all other tasks, you need to call this method in gameWorld.update()
    //  but let this task rest first
    public void handleCollisions(ArrayList<Vehicle> allVehicles) {
        // TODO Alton: Care here, because the collisions now happen even if a NPC vehicle collides with another NPC vehicle
        //  We need to fix gameWorld.spawnNpcVehicles() first.
        for(Vehicle vehicle : allVehicles) {
            ArrayList<Vehicle> collisions = getCollision(vehicle);
            if(!collisions.isEmpty()) {
                // TODO Alton: If collision happened, something happens
                System.out.println("Collision happened!");
            }
        }
    }
}

