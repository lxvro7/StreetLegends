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

    public void brake() {
        velocity -= (brakeForce / 100) * velocity;
        if (velocity < 0) {
            velocity = 0;
        }

        playerVehicle.setVelocity(velocity);
    }
    public void reverse() {
        if (velocity > -maxVelocity / 2) {
            velocity -= (acceleration / 100) * maxVelocity;
            if (velocity < -maxVelocity / 2) {
                velocity = -maxVelocity / 2;
            }
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

    public void slowDown() {
        if (Math.abs(velocity) > 0) {
            float deceleration = brakeForce / 100;

            if (velocity > 0) {
                velocity -= deceleration * maxVelocity;
                if (velocity < 0) velocity = 0;
            }
            else if (velocity < 0) {
                velocity += deceleration * maxVelocity;
                if (velocity > 0) velocity = 0;
            }

            playerVehicle.setVelocity(velocity);
        }
    }


    public void driveLeft(double diffSeconds) {
        // TODO alpha winkel ändern
        Vehicle vehicle = getPlayerVehicle();
        double direction = velocity < 0 ? 1 : -1;
        vehicle.setX(vehicle.getX() + direction * vehicle.getVelocity() * diffSeconds);
    }


    public void driveRight(double diffSeconds) {
        // TODO alpha winkel ändern
        Vehicle vehicle = getPlayerVehicle();
        double direction = velocity < 0 ? -1 : 1;
        vehicle.setX(vehicle.getX() + direction * vehicle.getVelocity() * diffSeconds);
    }
}
