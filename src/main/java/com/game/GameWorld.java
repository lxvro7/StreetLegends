package com.game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    private final Player player;
    private final ArrayList<NPC> npcs;
    private GameHandler gameHandler;


    public GameWorld(Player player, GameHandler gameHandler) {
        this.player = player;
        this.gameHandler=gameHandler;
        npcs = spawnTheNpcVehicles();
    }

    // Draw the vehicle images on the canvas
    public void drawVehicles(ArrayList<Vehicle> vehicles,
                             GraphicsContext vehicleGraphicsContext, double canvasWidth, double canvasHeight) {
        // Clear the canvas for each frame
        vehicleGraphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
        for(Vehicle vehicle : vehicles) {
            vehicleGraphicsContext.drawImage(vehicle.getVehicleImage(), vehicle.getX(), vehicle.getY());
        }
    }

    // Draw the background on the canvas
    public void drawBackground(GraphicsContext backgroundGraphicsContext, String roadImagePath, double canvasWidth, double canvasHeight) {
        backgroundGraphicsContext.drawImage(new Image(roadImagePath), 0, 0, canvasWidth, canvasHeight);
    }

    public void update(double diffSeconds) {
        moveAllVehicles(diffSeconds);
        cameraMoveEffect();
        gameHandler.getGameLogic().handleCollisions(getAllVehicles());
    }

    public void cameraMoveEffect() {
        // TODO Lovro: Let the background move, depending on the player vehicle coordinates
    }

    // Spawns NPC vehicles
    public ArrayList<NPC> spawnTheNpcVehicles() {
        ArrayList<NPC> npcs = new ArrayList<>();
        Random random = new Random();
        double[] randomArray = new double[4];

        int quantity = random.nextInt(8) + 8; // Number of NPCs to be spawned

        while (npcs.size() < quantity) {
            boolean placed = false; // Flag to check if NPC has been placed
            int attempts = 0;

            // Try placing the NPC up to 100 times to avoid endless loops
            while (!placed && attempts < 100) {
                double x = random.nextDouble() * 220 + 540;
                double x2 = random.nextDouble() * 200 + 760;
                double x3 = random.nextDouble() * 180 + 990;
                double x4 = random.nextDouble() * 170 + 1200;

                randomArray[0] = x;
                randomArray[1] = x2;
                randomArray[2] = x3;
                randomArray[3] = x4;

                double randomXValue = randomArray[random.nextInt(4)]; // Randomly select one of the lanes
                double y = random.nextDouble() * 1100 + 300; // Random Y-coordinate on the road

                // Create a new vehicle with the generated position and random type/color
                Vehicle.type vehicleType = Vehicle.type.values()[random.nextInt(Vehicle.type.values().length)];
                Vehicle.color vehicleColor = Vehicle.color.values()[random.nextInt(Vehicle.color.values().length)];
                Vehicle newVehicle = new Vehicle(randomXValue, y, vehicleType, vehicleColor, Vehicle.playerType.NPC);

                boolean collision = false;

                for (NPC existingNpc : npcs) {
                    //This line gets the Vehicle object of the NPC.
                    // We need it to know the X and Y positions of the existing vehicle.
                    Vehicle existingVehicle = existingNpc.getNpcVehicle();
                    double dx = Math.abs(newVehicle.getX() - existingVehicle.getX());
                    double dy = Math.abs(newVehicle.getY() - existingVehicle.getY());
                    if (dx < 200 && dy < 200) {
                        collision = true; // Collision detected
                        break;
                    }
                }
                if (!collision) {
                    // If no collision, add the new NPC to the list
                    System.out.println("NPC spawned at X: " + randomXValue + ", Y: " + y);
                    NPC npc = new NPC(newVehicle);
                    npcs.add(npc);
                    placed = true; // NPC successfully placed
                }

                attempts++;
            }
            if (attempts == 100) {
                System.out.println("Maximum number of attempts reached, NPC was not placed");
            }
        }

        return npcs;
    }

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> allVehicles = new ArrayList<>();
        allVehicles.add(player.getPlayerVehicle());
        for (NPC npc : npcs) {
            allVehicles.add(npc.getNpcVehicle());
        }
        return allVehicles;
    }
    public ArrayList<NPC> getAllNpcs() {
        return npcs;
    }
    public void reset() {
        npcs.clear();
        npcs.addAll(spawnTheNpcVehicles());
    }
    public void moveAllVehicles(double diffSeconds) {
        player.getPlayerVehicle().move(diffSeconds);
        for(NPC npc : npcs) {
            npc.move(diffSeconds);
        }
    }
}
