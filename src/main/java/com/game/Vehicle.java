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
        // Maybe need to change this
        velocity = 100;

    }

    public void initializeAttributes() {
        // Set different attribute for the player, based on the selected vehicle type
        switch(vehicleType) {
            case CAR:
                maxVelocity = 100;
                radius = 50;
                break;
            case TRUCK:
                maxVelocity = 80;
                radius = 70;
                break;
            case BIKE:
                maxVelocity = 500;
                radius = 30;
                break;
        }
    }

    // Associates the corresponding image path with a specific vehicle type and color combination
    public void bindVehicleToPng() {
        if(vehicleType == type.CAR) {
            switch (vehicleColor) {
                case GREEN:
                    imagePath = "/images/car/green.png";
                    break;
                case BLUE:
                    imagePath = "/images/car/blue.png";
                    break;
                case BLACK:
                    imagePath = "/images/car/black.png";
            }
        }
        if(vehicleType == type.TRUCK) {
            switch (vehicleColor) {
                case GREEN:
                    imagePath = "/images/truck/green.png";
                    break;
                case BLUE:
                    imagePath = "/images/truck/blue.png";
                    break;
                case BLACK:
                    imagePath = "/images/truck/black.png";
            }
        }
        if(vehicleType == type.BIKE) {
            switch (vehicleColor) {
                case GREEN:
                    imagePath = "/images/bike/green.png";
                    break;
                case BLUE:
                    imagePath = "/images/bike/blue.png";
                    break;
                case BLACK:
                    imagePath = "/images/bike/black.png";
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
        System.out.println("Speed: " + velocity + ", diffSeconds: " + diffSeconds);
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
}
