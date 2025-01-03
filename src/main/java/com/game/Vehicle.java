package com.game;

import javafx.scene.image.Image;
import java.util.Objects;

public class Vehicle {



    public enum playerType { PLAYER, NPC};
    public enum type { CAR, TRUCK, BIKE}
    public enum color { BLACK, BLUE, GREEN }

    private type vehicleType;
    private color vehicleColor;
    private double x, y;
    private double alfa = 3 * (Math.PI/2);
    private double radius;

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    // Refers to a percentage value
    private float acceleration;
    private float velocity;
    private float maxVelocity;
    private float brakeForce;
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
        // Set different attribute based on the selected vehicle type
        switch(vehicleType) {
            // TODO Alton: Play with these radius values
            case CAR:
                maxVelocity = 100;
                acceleration = 7;
                brakeForce = 20;
                radius = 1;
                break;
            case TRUCK:
                maxVelocity = 101;
                acceleration = 7;
                brakeForce = 20;
                radius = 1;
                break;
            case BIKE:
                maxVelocity = 500;
                acceleration = 7;
                brakeForce = 20;
                radius = 1;
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
    }
    public color getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(color vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public type getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(type vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getImagePath() {
        return imagePath;
    }
    public Image getVehicleImage() {
        return vehicleImage;
    }

    public float getBrakeForce() {
        return brakeForce;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public float getAcceleration() {
        return acceleration;
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

}
