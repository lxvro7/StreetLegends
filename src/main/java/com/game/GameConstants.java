package com.game;

/**
 * This class defines various game constants used across the application.
 * These include vehicle properties, game mechanics constants, rendering parameters,
 * and file paths for vehicle images.
 * @author lxvro7
 * @author bekoal01
 */

public class GameConstants {

    // Vehicle properties: Maximum velocity and radius for different vehicle types
    public static final int NPC_CAR_VELOCITY = 100;
    public static final int PLAYER_CAR_START_VELOCITY = 300;

    public static final int AMBULANCE_RADIUS = 100;
    public static final int AUDI_RADIUS = 60;
    public static final int BLACK_VIPER_RADIUS = 60;
    public static final int MUSTANG_RADIUS = 60;
    public static final int PICKUP_RADIUS = 60;
    public static final int POLICE_RADIUS = 60;
    public static final int TAXI_RADIUS = 60;
    public static final int TRUCK_RADIUS = 150;
    public static final int VAN_RADIUS = 60;

    // World properties: Boundaries and initial player position
    public static final int SCROLL_BOUNDS = 600;
    public static final double INITIAL_PLAYER_Y = 1_000_000;
    public static final double INITIAL_PLAYER_X = 790;
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
    public static final double SPAWN_OFFSET = 300;
    public static final double SPAWN_RANGE = 700;
    public static final double NPC_COLLISION_PADDING = 100;
    public static final int MAX_NPC_QUANTITY = 5;
    public static final double TURN_ANGLE = Math.PI / 4;

    // Rendering parameters: Frames per second (FPS) and frame delay
    public static final int FPS = 60;
    public static final int FRAME_DELAY = 1000 / FPS;

    // File paths for vehicle images
    public static final String AMBULANCE_IMAGE_PATH = "/images/vehicles/ambulance.png";
    public static final String AUDI_IMAGE_PATH = "/images/vehicles/audi.png";
    public static final String BLACK_VIPER_IMAGE_PATH = "/images/vehicles/black_viper.png";
    public static final String MUSTANG_IMAGE_PATH = "/images/vehicles/mustang.png";
    public static final String POLICE_IMAGE_PATH = "/images/vehicles/police.png";
    public static final String TAXI_IMAGE_PATH = "/images/vehicles/taxi.png";
    public static final String TRUCK_IMAGE_PATH = "/images/vehicles/truck.png";
    public static final String PICKUP_IMAGE_PATH = "/images/vehicles/pickup.png";
    public static final String VAN_IMAGE_PATH = "/images/vehicles/van.png";

    // File path for game background images
    public static final String GAME_BACKGROUND_IMAGE_PATH = "/images/background/game_background.png";
    public static final String MENU_BACKGROUND_IMAGE_PATH = "/images/background/menu_background.jpg";

    // File path for audios
    public static final String MENU_AUDIO = "/audio/menu.mp3";
    public static final String GAME_AUDIO = "/audio/car-engine-loop.wav";

    // Log messages

}

