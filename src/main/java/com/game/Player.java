package com.game;

public class Player {

    private final Vehicle playerVehicle;
    private final String playerName;
    private boolean turningLeft = false;
    private boolean turningRight = false;

    public Vehicle getPlayerVehicle() {
        return playerVehicle;
    }

    public Player(String playerName, Vehicle playerVehicle) {
        this.playerName = playerName;
        this.playerVehicle = playerVehicle;
    }

    public void driveLeft() {
        playerVehicle.setAlfa(5 * GameConstants.TURN_ANGLE);
        turningLeft = true;
    }

    public void driveRight() {
        playerVehicle.setAlfa(7 * GameConstants.TURN_ANGLE);
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

    public String getPlayerName() {
        return playerName;
    }
}
