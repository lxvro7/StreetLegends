package com.game;

public class Obstacle extends GameObject {

    public enum ObstacleType { COIN, CONE }

    private final ObstacleType obstacleType;

    public Obstacle(double x, double y, ObstacleType obstacleType) {
        super(x, y, getImagePath(obstacleType), getRadius(obstacleType), 0.05);
        this.obstacleType = obstacleType;
    }

    private static String getImagePath(ObstacleType type) {
        return switch (type) {
            case CONE -> GameConstants.CONE_IMAGE_PATH;
            case COIN -> GameConstants.COIN_IMAGE_PATH;
        };
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
}

