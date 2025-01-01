package com.game;

public class Player {

    private final Vehicle playerVehicle;
    private String playerName;

    private final float brakeForce;

    public Vehicle getPlayerVehicle() {
        return playerVehicle;
    }

    private float velocity;
    private final float maxVelocity;
    private final float acceleration;

    public Player(String playerName, Vehicle playerVehicle) {
        this.playerName = playerName;
        this.playerVehicle = playerVehicle;

        brakeForce = playerVehicle.getBrakeForce();
        velocity = playerVehicle.getVelocity();
        maxVelocity = playerVehicle.getMaxVelocity();
        acceleration = playerVehicle.getAcceleration();
    }

    // TODO Alton: Fix this method, so the car brakes and when stopped, it drives backwards
    public void brake() {
        velocity -= (brakeForce / 100) * velocity;
        if(Math.abs(velocity) < 0.5) {
            // Drive with the car backwards
            playerVehicle.setAlfa(Math.PI/2);
        }
        playerVehicle.setVelocity(velocity);
    }

    public void accelerate() {
        if(velocity < maxVelocity) {
            velocity += (acceleration / 100) * maxVelocity;
            if(velocity > maxVelocity) {
                velocity = maxVelocity;
            }
        }
        playerVehicle.setVelocity(velocity);
    }

    public void noButtonPressed() {
        // TODO Alton: Car should slowly stop, if no button is pressed -> velocity gets smaller
    }

    public void driveLeft() {
        // TODO Alton: Car should drive left -> change alfa in vehicle -> playerVehicle.setAlfa(new degree)
    }

    public void driveRight() {
        // TODO Alton: Car should drive right -> change alfa in vehicle -> playerVehicle.setAlfa(new degree)
    }
}
