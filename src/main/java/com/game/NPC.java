package com.game;

public class NPC {
    private Vehicle npcVehicle;

    public Vehicle getNpcVehicle() {
        return npcVehicle;
    }

    public NPC(Vehicle npcVehicle) {
        this.npcVehicle = npcVehicle;
    }

    public void move(double diffSeconds) {
        npcVehicle.move(diffSeconds);
        // KI in the Future
    }
}
