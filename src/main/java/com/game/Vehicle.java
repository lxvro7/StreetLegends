package com.game;

import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehicle {

    public enum PlayerType { PLAYER, NPC}
    public enum VehicleType { AMBULANCE, AUDI, BLACK_VIPER, MUSTANG, PICKUP, VAN, POLICE, TAXI, TRUCK}

    private final VehicleType vehicleType;
    private final PlayerType playerType;

    private double alfa = GameConstants.ROTATION_270_RAD;
    private double x, y;
    private double radius;

    // Refers to a percentage value
    private float velocity;
    private String imagePath;
    private Image vehicleImage;

    private final List<Circle> collisionCircles = new ArrayList<>();

    public double getRadius() {
        return radius;
    }

    public Vehicle(double x, double y, VehicleType vehicleType, PlayerType playerType) {
        this.x = x;
        this.y = y;
        this.vehicleType = vehicleType;
        this.playerType = playerType;

        initializeVelocity();
        initializeAttributes();
        initializeCollisionCircles();

    }

    private void initializeCollisionCircles() {
        double offset = radius / 2;
        collisionCircles.add(new Circle(x, y - offset, radius));
        collisionCircles.add(new Circle(x, y + offset, radius));
    }

    public void updateCollisionCircles() {
        double offset = radius / 2;
        collisionCircles.get(0).setCenterX(x);
        collisionCircles.get(0).setCenterY(y - offset);
        collisionCircles.get(1).setCenterX(x);
        collisionCircles.get(1).setCenterY(y + offset);
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
        updateCollisionCircles();
    }

    // Sets the radius and image path, based off the vehicle type
    public void initializeAttributes() {
        switch(vehicleType) {
            case AMBULANCE:
                radius = GameConstants.AMBULANCE_RADIUS;
                imagePath = GameConstants.AMBULANCE_IMAGE_PATH;
                break;
            case AUDI:
                radius = GameConstants.AUDI_RADIUS;
                imagePath = GameConstants.AUDI_IMAGE_PATH;
                break;
            case BLACK_VIPER:
                radius = GameConstants.BLACK_VIPER_RADIUS;
                imagePath = GameConstants.BLACK_VIPER_IMAGE_PATH;
                break;
            case MUSTANG:
                radius = GameConstants.MUSTANG_RADIUS;
                imagePath = GameConstants.MUSTANG_IMAGE_PATH;
                break;
            case PICKUP:
                radius = GameConstants.PICKUP_RADIUS;
                imagePath = GameConstants.PICKUP_IMAGE_PATH;
                break;
            case POLICE:
                radius = GameConstants.POLICE_RADIUS;
                imagePath = GameConstants.POLICE_IMAGE_PATH;
                break;
            case TAXI:
                radius = GameConstants.TAXI_RADIUS;
                imagePath = GameConstants.TAXI_IMAGE_PATH;
                break;
            case TRUCK:
                radius = GameConstants.TRUCK_RADIUS;
                imagePath = GameConstants.TRUCK_IMAGE_PATH;
                break;
            case VAN:
                radius = GameConstants.VAN_RADIUS;
                imagePath = GameConstants.VAN_IMAGE_PATH;
                break;
        }
        checkImagePath(imagePath);
    }

    private void checkImagePath(String imagePath) {
        try {
            vehicleImage = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setVehicleImage(Image vehicleImage) {
        this.vehicleImage = vehicleImage;
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

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public List<Circle> getCollisionCircles() {
        return collisionCircles;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
