package com.game;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import java.util.Objects;

public class Vehicle {

    // TODO Lovro: Change radius to rectangle bounding box.

    public enum PlayerType { PLAYER, NPC}
    public enum VehicleType { AMBULANCE, AUDI, BLACK_VIPER, MUSTANG, PICKUP, VAN, POLICE, TAXI, TRUCK}

    private final VehicleType vehicleType;
    private final PlayerType playerType;

    private double alfa = GameConstants.ROTATION_270_RAD;
    private double x, y;
    private double radius;
    private double height;
    private double width;
    private double top;
    private double bottom;
    private double left;
    private double right;

    // Refers to a percentage value
    private float velocity;
    private String imagePath;
    private Image vehicleImage;

    public Vehicle(double x, double y, VehicleType vehicleType, PlayerType playerType) {
        this.x = x;
        this.y = y;
        this.vehicleType = vehicleType;
        this.playerType = playerType;

        initializeVelocity();
        initializeAttributes();
        createBoundingBox();

    }

    public void initializeVelocity() {
        if(playerType == PlayerType.PLAYER) {
            velocity = GameConstants.PLAYER_CAR_START_VELOCITY;
        }
        else {
            velocity = GameConstants.NPC_CAR_VELOCITY;
        }
    }

    // Moves an object corresponding to alfa direction
    public void move(double diffSeconds) {
        x += Math.cos(alfa) * velocity * diffSeconds;
        y += Math.sin(alfa) * velocity * diffSeconds;
        createBoundingBox();
    }

    // Sets the radius and image path, based off the vehicle type
    public void initializeAttributes() {
        switch(vehicleType) {
            case AMBULANCE:
                height = GameConstants.AMBULANCE_HEIGHT;
                width = GameConstants.AMBULANCE_WIDTH;
                imagePath = GameConstants.AMBULANCE_IMAGE_PATH;
                break;
            case AUDI:
                height = GameConstants.AUDI_HEIGHT;
                width = GameConstants.AUDI_WIDTH;
                imagePath = GameConstants.AUDI_IMAGE_PATH;
                break;
            case BLACK_VIPER:
                height = GameConstants.BLACK_VIPER_HEIGHT;
                width = GameConstants.BLACK_VIPER_WIDTH;
                imagePath = GameConstants.BLACK_VIPER_IMAGE_PATH;
                break;
            case MUSTANG:
                height = GameConstants.MUSTANG_HEIGHT;
                width = GameConstants.MUSTANG_WIDTH;
                imagePath = GameConstants.MUSTANG_IMAGE_PATH;
                break;
            case PICKUP:
                height = GameConstants.PICKUP_HEIGHT;
                width = GameConstants.PICKUP_WIDTH;
                imagePath = GameConstants.PICKUP_IMAGE_PATH;
                break;
            case POLICE:
                height = GameConstants.POLICE_HEIGHT;
                width = GameConstants.POLICE_WIDTH;
                imagePath = GameConstants.POLICE_IMAGE_PATH;
                break;
            case TAXI:
                height = GameConstants.TAXI_HEIGHT;
                width = GameConstants.TAXI_WIDTH;
                imagePath = GameConstants.TAXI_IMAGE_PATH;
                break;
            case TRUCK:
                height = GameConstants.TRUCK_HEIGHT;
                width = GameConstants.TRUCK_WIDTH;
                imagePath = GameConstants.TRUCK_IMAGE_PATH;
                break;
            case VAN:
                height = GameConstants.VAN_HEIGHT;
                width = GameConstants.VAN_WIDTH;
                imagePath = GameConstants.VAN_IMAGE_PATH;
                break;
        }
        checkImagePath(imagePath);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void createBoundingBox() {
        top = y - height/2;
        bottom = y + height/2;
        left = x - width/2;
        right = x + width/2;
    }

    private void checkImagePath(String imagePath) {
        try {
            vehicleImage = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Image getVehicleImage() {
        return vehicleImage;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

}
