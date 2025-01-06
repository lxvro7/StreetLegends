package com.game;

/**
 * This class defines various game constants used across the application.
 * These include vehicle properties, game mechanics constants, rendering parameters,
 * and file paths for vehicle images.
 * @author lxvro7
 * @author bekoal01
 */

// TODO: Jede Konstante mit einem Kommentar erklären (Drüber)

public class GameConstants {

    // Vehicle properties: Maximum velocity and radius for different vehicle types
    public static final int CAR_MAX_VELOCITY = 100;
    public static final int CAR_RADIUS = 30;
    public static final int TRUCK_MAX_VELOCITY = 200;
    public static final int TRUCK_RADIUS = 30;
    public static final int BIKE_MAX_VELOCITY = 250;
    public static final int BIKE_RADIUS = 30;

    // World properties: Boundaries and initial player position
    public static final int SCROLL_BOUNDS = 600;
    public static final double INITIAL_PLAYER_Y = 11000;
    public static final double INITIAL_WORLD_PART_Y = INITIAL_PLAYER_Y - 100;
    public static final double MIN_X_COORDINATE = 540;
    public static final double MAX_X_COORDINATE = 1400;
    public static final double SPAWN_TRIGGER_TOLERANCE = 10;
    public static final double ROTATION_270_RAD = 3 * Math.PI / 2;
    public static final double[] LANES = {600, 720, 840, 960};

    // Game mechanics constants: NPC spawning limits and turning angles
    public static final int MAX_NPC_ATTEMPTS = 100;
    public static final int MIN_NPC_QUANTITY = 2;
    public static final int EASY_DIFFICULTY_NPC_AMOUNT = 10;
    public static final int MIDDLE_DIFFICULTY_NPC_AMOUNT = 14;
    public static final int HARD_DIFFICULTY_NPC_AMOUNT = 20;
    public static final double SPAWN_OFFSET = 50;
    public static final double SPAWN_RANGE = 700;
    public static final double NPC_COLLISION_PADDING = 100;
    public static final int MAX_NPC_QUANTITY = 5;
    public static final double TURN_ANGLE = Math.PI / 4;

    // Rendering parameters: Frames per second (FPS) and frame delay
    public static final int FPS = 60;
    public static final int FRAME_DELAY = 1000 / FPS;

    // File paths for vehicle images
    public static final String CAR_BLUE_IMAGE = "/images/car/blue.png";
    public static final String CAR_GREEN_IMAGE = "/images/car/green.png";
    public static final String CAR_BLACK_IMAGE = "/images/car/black.png";
    public static final String TRUCK_BLUE_IMAGE = "/images/truck/blue.png";
    public static final String TRUCK_GREEN_IMAGE = "/images/truck/green.png";
    public static final String TRUCK_BLACK_IMAGE = "/images/truck/black.png";
    public static final String BIKE_BLUE_IMAGE = "/images/bike/blue.png";
    public static final String BIKE_GREEN_IMAGE = "/images/bike/green.png";
    public static final String BIKE_BLACK_IMAGE = "/images/bike/black.png";

    // File path for game background image
    public static final String BACKGROUND_IMAGE = "/images/street/road.png";

    // Log messages


}
