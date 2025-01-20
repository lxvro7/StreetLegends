package com.game;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Vehicle extends GameObject {

    public enum PlayerType { PLAYER, NPC_NORTH, NPC_SOUTH }
    public enum VehicleType { AMBULANCE, AUDI, BLACK_VIPER, PICKUP, VAN, POLICE, TAXI, TRUCK}

    private final VehicleType vehicleType;
    private final PlayerType playerType;

    private double alfa = GameConstants.ROTATION_270_RAD;

    // Refers to a percentage value
    private float velocity;

    private final List<Circle> collisionCircles = new ArrayList<>();

    public Vehicle(double x, double y, VehicleType vehicleType, PlayerType playerType) {
        super(x, y, getImagePath(vehicleType, playerType), getRadius(vehicleType), 0.125);
        this.x = x;
        this.y = y;
        this.vehicleType = vehicleType;
        this.playerType = playerType;

        initializeVelocity();
        initializeCollisionCircles();

    }

    private static String getImagePath(VehicleType vehicleType, PlayerType playerType) {
        return switch (playerType) {
            case NPC_NORTH -> GameConstants.NPC_NORTH_IMAGES().get(vehicleType);
            case NPC_SOUTH -> GameConstants.NPC_SOUTH_IMAGES().get(vehicleType);
            default -> GameConstants.PLAYER_IMAGE_PATH;
        };
    }

    private static double getRadius(VehicleType vehicleType) {
        return GameConstants.VEHICLE_RADIUS().get(vehicleType);
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
        if (playerType == PlayerType.NPC_NORTH) {
            velocity = GameConstants.NPC_NORTH_CAR_VELOCITY;
        }
         if (playerType == PlayerType.NPC_SOUTH) {
            velocity = GameConstants.NPC_SOUTH_CAR_VELOCITY;
            alfa = GameConstants.ROTATION_90_RAD;
        }
    }

    // Moves an object corresponding to alfa direction
    public void move(double diffSeconds) {
        x += Math.cos(alfa) * velocity * diffSeconds;
        y += Math.sin(alfa) * velocity * diffSeconds;
        updateCollisionCircles();
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
}
