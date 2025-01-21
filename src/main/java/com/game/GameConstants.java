package com.game;

import java.util.Map;
import static com.game.Vehicle.VehicleType;

/**
 * This class defines various game constants used across the application.
 * These include object properties, game mechanics constants, rendering parameters,
 * and file paths for vehicle images.
 * @author lxvro7
 * @author bekoal01
 */

public class GameConstants {

    // Object properties: Velocity and radius for different vehicle types
    public static final int NPC_SOUTH_CAR_VELOCITY = 600;
    public static final int NPC_NORTH_CAR_VELOCITY = 200;
    public static final int CONE_RADIUS = 50;
    public static final int COIN_RADIUS = 10;

    // Game mechanics constants:
    public static final int PLAYER_CAR_MAX_VELOCITY = 2000;
    public static final int PLAYER_CAR_START_VELOCITY = 500;
    public static final int INITIAL_VELOCITY = PLAYER_CAR_START_VELOCITY;
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

    // File paths for player image
    public static final String PLAYER_IMAGE_PATH = "/images/vehicles/player/mustang.png";
    public static final String PLAYER_RIGHT_IMAGE_PATH = "/images/vehicles/player/mustang_right.png";
    public static final String PLAYER_LEFT_IMAGE_PATH = "/images/vehicles/player/mustang_left.png";
    // File paths for npc south

    // File path for game background images
    public static final String GAME_BACKGROUND_IMAGE_PATH = "/images/background/game_background.jpg";
    public static final String MENU_BACKGROUND_IMAGE_PATH = "/images/background/menu_background.jpg";

    // File paths for audios
    public static final String MENU_AUDIO = "/audio/menu.mp3";
    public static final String GAME_AUDIO = "/audio/marioKart.mp3";
    public static final String CRASH_AUDIO = "/audio/crash.mp3";
    public static final String COUNTDOWN_AUDIO = "/audio/mariostart.mp3";


    // Images for obstacles
    public static final String CONE_IMAGE_PATH = "/images/icon/falsch.png";
    public static final String COIN_IMAGE_PATH = "/images/icon/coin.png";


    // Scaling Factor for easy-medium and hard
    public static final double EASY_COLLISION_SCALING_FACTOR = 8.0;
    public static final double MEDIUM_COLLISION_SCALING_FACTOR = 6.5;
    public static final double HARD_COLLISION_SCALING_FACTOR = 5.0;
    public static final double FALLBACK_COLLISION_SCALING_FACTOR = 7.5;

    public static Map<Vehicle.VehicleType, String> NPC_NORTH_IMAGES() {
        return Map.of(
                VehicleType.AMBULANCE,   "/images/vehicles/npc_north/ambulance.png",
                VehicleType.AUDI,        "/images/vehicles/npc_north/audi.png",
                VehicleType.BLACK_VIPER, "/images/vehicles/npc_north/black_viper.png",
                VehicleType.POLICE,      "/images/vehicles/npc_north/police.png",
                VehicleType.TAXI,        "/images/vehicles/npc_north/taxi.png",
                VehicleType.TRUCK,       "/images/vehicles/npc_north/truck.png",
                VehicleType.PICKUP,      "/images/vehicles/npc_north/pickup.png",
                VehicleType.VAN,         "/images/vehicles/npc_north/van.png"
        );
    }

    public static Map<VehicleType, String> NPC_SOUTH_IMAGES() {
        return Map.of(
                VehicleType.AMBULANCE,   "/images/vehicles/npc_south/ambulance.png",
                VehicleType.AUDI,        "/images/vehicles/npc_south/audi.png",
                VehicleType.BLACK_VIPER, "/images/vehicles/npc_south/black_viper.png",
                VehicleType.POLICE,      "/images/vehicles/npc_south/police.png",
                VehicleType.TAXI,        "/images/vehicles/npc_south/taxi.png",
                VehicleType.TRUCK,       "/images/vehicles/npc_south/truck.png",
                VehicleType.PICKUP,      "/images/vehicles/npc_south/pickup.png",
                VehicleType.VAN,         "/images/vehicles/npc_south/van.png"
        );
    }

    public static Map<VehicleType, Double> VEHICLE_RADIUS() {
        return Map.of(
                VehicleType.AMBULANCE,   40.0,
                VehicleType.AUDI,        40.0,
                VehicleType.BLACK_VIPER, 40.0,
                VehicleType.POLICE,      40.0,
                VehicleType.TAXI,        40.0,
                VehicleType.TRUCK,       40.0,
                VehicleType.PICKUP,      40.0,
                VehicleType.VAN,         40.0
        );
    }
}

