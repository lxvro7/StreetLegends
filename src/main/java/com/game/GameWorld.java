package com.game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    private final Player player;
    private final ArrayList<NPC> npcs;

    public GameWorld(Player player) {
        this.player = player;
        npcs = spawnNpcVehicles();
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
    public void drawBackground(GraphicsContext backgroundGraphicsContext,
                               String roadImagePath, double canvasWidth, double canvasHeight) {
        backgroundGraphicsContext.drawImage(new Image(roadImagePath), 0, 0, canvasWidth, canvasHeight);
    }

    public void update(double diffSeconds) {
        moveAllVehicles(diffSeconds);
        cameraMoveEffect();
    }

    public void cameraMoveEffect() {
        // TODO Lovro: Let the background move, depending on the player vehicle coordinates
    }

    // Spawns NPC vehicles
    public ArrayList<NPC> spawnNpcVehicles() {
        // TODO Alton: Fix this Method to ensure no new vehicle is spawned at the coordinates
        //  another vehicle is currently at, use the formula in gameLogic.getCollision()!
        ArrayList<NPC> npcs = new ArrayList<>();
        Random random = new Random();
        double [] randomArray = new double[4];
        // Change the bound, to spawn more or less cars
        int quantity = random.nextInt(5) + 3;
        // Play with these Attributes
        for(int i = 0; i < quantity; i++) {
            double x = random.nextDouble()  * 220 + 540;
            double x2 = random.nextDouble() * 200 + 760;
            double x3 = random.nextDouble() * 180 + 990;
            double x4 = random.nextDouble() * 170 + 1200;

            randomArray[0] = x;
            randomArray[1] = x2;
            randomArray[2] = x3;
            randomArray[3] = x4;

            double randomXValue = randomArray[random.nextInt(4)];

            double y = random.nextDouble() * 1100 + 300;
            // Creates random vehicle types and colors
            Vehicle.type vehicleType = Vehicle.type.values()[random.nextInt(Vehicle.type.values().length)];
            Vehicle.color vehicleColor = Vehicle.color.values()[random.nextInt(Vehicle.color.values().length)];
            Vehicle vehicle = new Vehicle(randomXValue, y, vehicleType, vehicleColor, Vehicle.playerType.NPC);
            NPC npc = new NPC(vehicle);
            npcs.add(npc);
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

    public void moveAllVehicles(double diffSeconds) {
        player.getPlayerVehicle().move(diffSeconds);
        for(NPC npc : npcs) {
            npc.move(diffSeconds);
        }
    }
}
