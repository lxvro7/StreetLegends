package com.game;

public class Player {

    private final Vehicle playerVehicle;
    private String playerName;
    private boolean turningLeft = false;
    private boolean turningRight = false;

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

    public void driveLeft() {
        playerVehicle.setAlfa(5 * (Math.PI/4));
        turningLeft = true;
    }

    public void driveRight() {
        playerVehicle.setAlfa(7* (Math.PI/4));
        turningRight = true;
    }

    public boolean isTurningRight() {
        return turningRight;
    }

    public void setTurningRight(boolean turningRight) {
        this.turningRight = turningRight;
    }

    public boolean isTurningLeft() {
        return turningLeft;
    }

    public void setTurningLeft(boolean turningLeft) {
        this.turningLeft = turningLeft;
    }
}
