package com.game;

import javafx.scene.image.Image;
import java.util.Objects;

public class Vehicle {

    public enum playerType { PLAYER, NPC};
    public enum type { CAR, TRUCK, BIKE}
    public enum color { BLACK, BLUE, GREEN }

    private final type vehicleType;
    private final color vehicleColor;
    private double x, y;
    private double alfa = 3 * (Math.PI/2);
    private double radius;

    // Refers to a percentage value
    private float velocity;
    private float maxVelocity;
    private String imagePath;
    private Image vehicleImage;

    public Vehicle(double x, double y, type vehicleType, color vehicleColor, playerType playerType) {
        this.x = x;
        this.y = y;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
        initializeAttributes();
        bindVehicleToPng();
        if (playerType == playerType.PLAYER) {
            velocity = maxVelocity;
        } else {
            velocity = 100;
        }

    }

    public void initializeAttributes() {
        switch(vehicleType) {
            case CAR:
                maxVelocity = GameConstants.CAR_MAX_VELOCITY;
                radius = GameConstants.CAR_RADIUS;
                break;
            case TRUCK:
                maxVelocity = GameConstants.TRUCK_MAX_VELOCITY;
                radius = GameConstants.TRUCK_RADIUS;
                break;
            case BIKE:
                maxVelocity = GameConstants.BIKE_MAX_VELOCITY;
                radius = GameConstants.BIKE_RADIUS;
                break;
        }
    }
    public void bindVehicleToPng() {
        if (vehicleType == type.CAR) {
            switch (vehicleColor) {
                case GREEN:
                    imagePath = GameConstants.CAR_GREEN_IMAGE;
                    break;
                case BLUE:
                    imagePath = GameConstants.CAR_BLUE_IMAGE;
                    break;
                case BLACK:
                    imagePath = GameConstants.CAR_BLACK_IMAGE;
            }
        }
        if (vehicleType == type.TRUCK) {
            switch (vehicleColor) {
                case GREEN:
                    imagePath = GameConstants.TRUCK_GREEN_IMAGE;
                    break;
                case BLUE:
                    imagePath = GameConstants.TRUCK_BLUE_IMAGE;
                    break;
                case BLACK:
                    imagePath = GameConstants.TRUCK_BLACK_IMAGE;
            }
        }
        if (vehicleType == type.BIKE) {
            switch (vehicleColor) {
                case GREEN:
                    imagePath = GameConstants.BIKE_GREEN_IMAGE;
                    break;
                case BLUE:
                    imagePath = GameConstants.BIKE_BLUE_IMAGE;
                    break;
                case BLACK:
                    imagePath = GameConstants.BIKE_BLACK_IMAGE;
            }
        }
        // Check if image path exists
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

    // Moves an object corresponding to alfa direction
    public void move(double diffSeconds) {
        x += Math.cos(alfa) * velocity * diffSeconds;
        y += Math.sin(alfa) * velocity * diffSeconds;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Image getVehicleImage() {
        return vehicleImage;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x=x;
    }

    public double getRadius() {
        return radius;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }
    public double getWidth() {
        return radius * 2;  // Breite entspricht dem Durchmesser des Fahrzeugs
    }

    public double getHeight() {
        return radius * 2;  // Höhe entspricht dem Durchmesser des Fahrzeugs
    }

}
