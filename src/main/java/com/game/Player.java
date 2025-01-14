package com.game;

import javafx.scene.image.Image;

import java.util.Objects;

public class Player {

    private final Vehicle playerVehicle;
    private final String playerName;
    private boolean turningLeft = false;
    private boolean turningRight = false;
    private final Image turnRightImage = new Image(Objects.requireNonNull(getClass().
            getResource(GameConstants.PLAYER_RIGHT_IMAGE_PATH)).toExternalForm());
    private final Image turnLeftImage = new Image(Objects.requireNonNull(getClass().
            getResource(GameConstants.PLAYER_LEFT_IMAGE_PATH)).toExternalForm());
    private final Image defaultImage = new Image(Objects.requireNonNull(getClass().
    getResource(GameConstants.PLAYER_IMAGE_PATH)).toExternalForm());

    public Image getDefaultImage() {
        return defaultImage;
    }

    public Vehicle getPlayerVehicle() {
        return playerVehicle;
    }

    public Player(String playerName, Vehicle playerVehicle) {
        this.playerName = playerName;
        this.playerVehicle = playerVehicle;
    }

    public void driveLeft() {
        playerVehicle.setAlfa(5 * GameConstants.TURN_ANGLE);
        playerVehicle.setImage(turnLeftImage);
        turningLeft = true;
    }

    public void driveRight() {
        playerVehicle.setAlfa(7 * GameConstants.TURN_ANGLE);
        playerVehicle.setImage(turnRightImage);
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
