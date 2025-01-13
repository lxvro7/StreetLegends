package com.game;

import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehicle {

    public enum PlayerType { PLAYER, NPC_NORTH, NPC_SOUTH,OBSTACLE}
    public enum VehicleType { AMBULANCE, AUDI, BLACK_VIPER, PICKUP, VAN, POLICE, TAXI, TRUCK,CONE}

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

    public Vehicle(double x, double y, VehicleType vehicleType, PlayerType playerType) {
        if (vehicleType == null) {
            System.err.println("Fehler: VehicleType ist null beim Erstellen eines Fahrzeugs!");
        }
        this.x = x;
        this.y = y;
        this.vehicleType = vehicleType;
        this.playerType = playerType;

        System.out.println("Erzeuge Fahrzeug: Typ = " + vehicleType + ", PlayerType = " + playerType);

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
        System.out.println("Kollisionskreise aktualisiert für Fahrzeugtyp " + vehicleType +
                ": [" + collisionCircles.get(0) + ", " + collisionCircles.get(1) + "]");
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

    // Sets the radius and image path, based off the vehicle type
    public void initializeAttributes() {
        switch(playerType) {
            case PLAYER:
                imagePath = GameConstants.PLAYER_IMAGE_PATH;
                break;
            case NPC_SOUTH:
                switch (vehicleType) {
                    case AMBULANCE:
                        radius = GameConstants.AMBULANCE_RADIUS;
                        imagePath = GameConstants.NPC_SOUTH_AMBULANCE_IMAGE_PATH;
                        break;
                    case AUDI:
                        radius = GameConstants.AUDI_RADIUS;
                        imagePath = GameConstants.NPC_SOUTH_AUDI_IMAGE_PATH;
                        break;
                    case BLACK_VIPER:
                        radius = GameConstants.BLACK_VIPER_RADIUS;
                        imagePath = GameConstants.NPC_SOUTH_BLACK_VIPER_IMAGE_PATH;
                        break;
                    case PICKUP:
                        radius = GameConstants.PICKUP_RADIUS;
                        imagePath = GameConstants.NPC_SOUTH_PICKUP_IMAGE_PATH;
                        break;
                    case POLICE:
                        radius = GameConstants.POLICE_RADIUS;
                        imagePath = GameConstants.NPC_SOUTH_POLICE_IMAGE_PATH;
                        break;
                    case TAXI:
                        radius = GameConstants.TAXI_RADIUS;
                        imagePath = GameConstants.NPC_SOUTH_TAXI_IMAGE_PATH;
                        break;
                    case TRUCK:
                        radius = GameConstants.TRUCK_RADIUS;
                        imagePath = GameConstants.NPC_SOUTH_TRUCK_IMAGE_PATH;
                        break;
                    case VAN:
                        radius = GameConstants.VAN_RADIUS;
                        imagePath = GameConstants.NPC_SOUTH_VAN_IMAGE_PATH;
                        break;
                }
                break;
            case NPC_NORTH:
                switch (vehicleType) {
                    case AMBULANCE:
                        radius = GameConstants.AMBULANCE_RADIUS;
                        imagePath = GameConstants.NPC_NORTH_AMBULANCE_IMAGE_PATH;
                        break;
                    case AUDI:
                        radius = GameConstants.AUDI_RADIUS;
                        imagePath = GameConstants.NPC_NORTH_AUDI_IMAGE_PATH;
                        break;
                    case BLACK_VIPER:
                        radius = GameConstants.BLACK_VIPER_RADIUS;
                        imagePath = GameConstants.NPC_NORTH_BLACK_VIPER_IMAGE_PATH;
                        break;
                    case PICKUP:
                        radius = GameConstants.PICKUP_RADIUS;
                        imagePath = GameConstants.NPC_NORTH_PICKUP_IMAGE_PATH;
                        break;
                    case POLICE:
                        radius = GameConstants.POLICE_RADIUS;
                        imagePath = GameConstants.NPC_NORTH_POLICE_IMAGE_PATH;
                        break;
                    case TAXI:
                        radius = GameConstants.TAXI_RADIUS;
                        imagePath = GameConstants.NPC_NORTH_TAXI_IMAGE_PATH;
                        break;
                    case TRUCK:
                        radius = GameConstants.TRUCK_RADIUS;
                        imagePath = GameConstants.NPC_NORTH_TRUCK_IMAGE_PATH;
                        break;
                    case VAN:
                        radius = GameConstants.VAN_RADIUS;
                        imagePath = GameConstants.NPC_NORTH_VAN_IMAGE_PATH;
                        break;
                }
                break;
            case OBSTACLE:
                radius = 15;
                imagePath = GameConstants.CONE_IMAGE_PATH;
                break;
            default:
                System.err.println("Fehler: Unbekannter PlayerType!");
                break;
        }

        if (radius == 0) {
            System.err.println("Fehler: Radius wurde nicht gesetzt für Fahrzeugtyp: " + vehicleType);
        } else {
            System.out.println("Gesetzter Radius für Fahrzeugtyp " + vehicleType + ": " + radius);
        }

        checkImagePath(imagePath);
    }

    private void checkImagePath(String imagePath) {
        try {
            vehicleImage = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
            System.out.println("Bild erfolgreich geladen: " + imagePath);
        } catch (NullPointerException e) {
            System.err.println("Fehler: Bild konnte nicht geladen werden. Pfad: " + imagePath);
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
    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
