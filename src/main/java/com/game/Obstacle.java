package com.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Obstacle extends GameObject {

    public enum ObstacleType { COIN, CONE }

    private final ObstacleType obstacleType;
    private final List<Circle> collisionCircles = new ArrayList<>();

    public Obstacle(double x, double y, ObstacleType obstacleType) {
        super(x, y, getImagePath(obstacleType), getRadius(obstacleType), 0.04);
        this.obstacleType = obstacleType;
        initializeCollisionCircles();
    }

    private static String getImagePath(ObstacleType type) {
        return switch (type) {
            case CONE -> GameConstants.CONE_IMAGE_PATH;
            case COIN -> GameConstants.COIN_IMAGE_PATH;
        };
    }

    private void initializeCollisionCircles() {
        double offset = radius / 2;
        collisionCircles.add(new Circle(x, y - offset, radius));
        collisionCircles.add(new Circle(x, y + offset, radius));
    }

    private static double getRadius(ObstacleType type) {
        return switch (type) {
            case CONE -> GameConstants.CONE_RADIUS;
            case COIN -> GameConstants.COIN_RADIUS;
        };
    }

    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    public List<Circle> getCollisionCircles() {
        return collisionCircles;
    }

}

