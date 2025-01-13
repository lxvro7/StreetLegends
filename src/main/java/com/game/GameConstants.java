package com.game;

/**
 * This class defines various game constants used across the application.
 * These include vehicle properties, game mechanics constants, rendering parameters,
 * and file paths for vehicle images.
 * @author lxvro7
 * @author bekoal01
 */

public class GameConstants {

    // Vehicle properties: Velocity and radius for different vehicle types
    public static final int NPC_SOUTH_CAR_VELOCITY = 600;
    public static final int NPC_NORTH_CAR_VELOCITY = 200;
    public static final int PLAYER_CAR_START_VELOCITY = 900;
    public static final int AMBULANCE_RADIUS = 40;
    public static final int AUDI_RADIUS = 40;
    public static final int BLACK_VIPER_RADIUS = 40;
    public static final int MUSTANG_RADIUS = 40;
    public static final int PICKUP_RADIUS = 40;
    public static final int POLICE_RADIUS = 40;
    public static final int TAXI_RADIUS = 40;
    public static final int TRUCK_RADIUS = 40;
    public static final int VAN_RADIUS = 40;

    // Game mechanics constants:
    public static final int PLAYER_CAR_MAX_VELOCITY = 1050;
    public static final int EASY_DIFFICULTY_SPEED_INCREASE = 3;
    public static final int MEDIUM_DIFFICULTY_SPEED_INCREASE = 15;
    public static final int HARD_DIFFICULTY_SPEED_INCREASE = 25;

    // World properties: Boundaries and initial player position
    public static final int SCROLL_BOUNDS = 700;
    public static final double INITIAL_PLAYER_Y = 1_000_000;
    public static final double INITIAL_PLAYER_X = 790;
    public static final double INITIAL_WORLD_PART_Y = INITIAL_PLAYER_Y - 100;
    public static final double MIN_X_COORDINATE = 306;
    public static final double MAX_X_COORDINATE = 900;
    public static final double SPAWN_TRIGGER_TOLERANCE = 10;
    public static final double ROTATION_270_RAD = 3 * Math.PI / 2;
    public static final double ROTATION_90_RAD = Math.PI / 2;

    // Game mechanics constants: NPC spawning limits and turning angles
    public static final int MAX_NPC_ATTEMPTS = 5;
    public static final int MIN_NPC_QUANTITY = 2;
    public static final int EASY_DIFFICULTY_NPC_NORTH_AMOUNT = 3;
    public static final int MIDDLE_DIFFICULTY_NPC_NORTH_AMOUNT = 4;
    public static final int HARD_DIFFICULTY_NPC_NORTH_AMOUNT = 5;
    public static final int EASY_DIFFICULTY_NPC_SOUTH_AMOUNT = 1;
    public static final int MIDDLE_DIFFICULTY_NPC_SOUTH_AMOUNT = 2;
    public static final int HARD_DIFFICULTY_NPC_SOUTH_AMOUNT = 3;
    public static final double SPAWN_OFFSET = 700;
    public static final double SPAWN_RANGE = 600;
    public static final double NPC_COLLISION_PADDING = 100;
    public static final int MAX_NPC_QUANTITY = 5;
    public static final double TURN_ANGLE = Math.PI / 4;

    // Rendering parameters: Frames per second (FPS) and frame delay
    public static final long TARGET_FPS = 60;
    public static final long FRAME_TIME = 1_000_000_000L / TARGET_FPS;

    // File paths for player image
    public static final String PLAYER_IMAGE_PATH = "/images/vehicles/player/mustang.png";
    public static final String PLAYER_RIGHT_IMAGE_PATH = "/images/vehicles/player/mustang_right.png";
    public static final String PLAYER_LEFT_IMAGE_PATH = "/images/vehicles/player/mustang_left.png";
    // File paths for npc south
    public static final String NPC_SOUTH_AMBULANCE_IMAGE_PATH = "/images/vehicles/npc_south/ambulance.png";
    public static final String NPC_SOUTH_AUDI_IMAGE_PATH = "/images/vehicles/npc_south/audi.png";
    public static final String NPC_SOUTH_BLACK_VIPER_IMAGE_PATH = "/images/vehicles/npc_south/black_viper.png";
    public static final String NPC_SOUTH_POLICE_IMAGE_PATH = "/images/vehicles/npc_south/police.png";
    public static final String NPC_SOUTH_TAXI_IMAGE_PATH = "/images/vehicles/npc_south/taxi.png";
    public static final String NPC_SOUTH_TRUCK_IMAGE_PATH = "/images/vehicles/npc_south/truck.png";
    public static final String NPC_SOUTH_PICKUP_IMAGE_PATH = "/images/vehicles/npc_south/pickup.png";
    public static final String NPC_SOUTH_VAN_IMAGE_PATH = "/images/vehicles/npc_south/van.png";
    // File paths for npc north
    public static final String NPC_NORTH_AMBULANCE_IMAGE_PATH = "/images/vehicles/npc_north/ambulance.png";
    public static final String NPC_NORTH_AUDI_IMAGE_PATH = "/images/vehicles/npc_north/audi.png";
    public static final String NPC_NORTH_BLACK_VIPER_IMAGE_PATH = "/images/vehicles/npc_north/black_viper.png";
    public static final String NPC_NORTH_POLICE_IMAGE_PATH = "/images/vehicles/npc_north/police.png";
    public static final String NPC_NORTH_TAXI_IMAGE_PATH = "/images/vehicles/npc_north/taxi.png";
    public static final String NPC_NORTH_TRUCK_IMAGE_PATH = "/images/vehicles/npc_north/truck.png";
    public static final String NPC_NORTH_PICKUP_IMAGE_PATH = "/images/vehicles/npc_north/pickup.png";
    public static final String NPC_NORTH_VAN_IMAGE_PATH = "/images/vehicles/npc_north/van.png";

    // File path for game background images
    public static final String GAME_BACKGROUND_IMAGE_PATH = "/images/background/game_background.jpg";
    public static final String MENU_BACKGROUND_IMAGE_PATH = "/images/background/menu_background.jpg";

    // File path for audios
    public static final String MENU_AUDIO = "/audio/menu.mp3";
    public static final String GAME_AUDIO = "/audio/menu.mp3";
    public static final String CRASH_AUDIO = "/audio/crash.mp3";

    //Image for cone
    public static final String CONE_IMAGE_PATH = "/images/icon/hut.png";


    // Scaling Factor for easy-medium and hard
    public static final double EASY_COLLISION_SCALING_FACTOR = 8.0;
    public static final double MEDIUM_COLLISION_SCALING_FACTOR = 7.0;
    public static final double HARD_COLLISION_SCALING_FACTOR = 5.5;
    public static final double FALLBACK_COLLISION_SCALING_FACTOR = 7.5;


    // Static methods

    // GameConstants.java--calculateCenter
    public static double getRoadCenter(double canvasWidth) {
        return canvasWidth * 0.46;
    }
}

