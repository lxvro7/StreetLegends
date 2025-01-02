package com.game;

import javafx.scene.image.Image;

import java.util.Objects;

// Don't change anything here, only in the method initializeAttributes()

public class Vehicle {

    public enum playerType { PLAYER, NPC};
    public enum type { CAR, TRUCK, BIKE}
    public enum color { BLACK, BLUE, GREEN }

    private type vehicleType;
    private color vehicleColor;
    public double x, y;
    public double alfa = 3 * (Math.PI/2);

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
        if(playerType == Vehicle.playerType.PLAYER) {
            velocity = 2;
        }
        else {
            velocity = 100;
        }
    }

    public void initializeAttributes() {
        // Set different attribute based on the selected vehicle type
        switch(vehicleType) {
            // TODO: Fix these values, so the vehicle moves better
            case CAR:
                maxVelocity = 100;
                acceleration = 7;
                brakeForce = 20;
                break;
            case TRUCK:
                maxVelocity = 99;
                acceleration = 7;
                brakeForce = 20;
                break;
            case BIKE:
                maxVelocity = 101;
                acceleration = 7;
                brakeForce = 20;
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
        if(imagePath != null) {
            vehicleImage = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
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
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public float getBrakeForce() {
        return brakeForce;
    }

    public void setBrakeForce(float brakeForce) {
        this.brakeForce = brakeForce;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }
    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
