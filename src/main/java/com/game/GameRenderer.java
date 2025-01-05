package com.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public class GameRenderer {
    private final String roadImagePath = Objects.requireNonNull(getClass().
            getResource("/images/street/road.png")).toExternalForm();
    private final Image image;
    private final GameManager gameManager;
    private double canvasStartY = 10_000;

    public GameRenderer(GameManager gameManager) {
        this.gameManager = gameManager;
        image = new Image(roadImagePath);
    }

    public void drawVehicles(ArrayList<Vehicle> vehicles,
                             GraphicsContext vehicleGraphicsContext, double canvasWidth, double canvasHeight) {

        vehicleGraphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

        double worldPartY = gameManager.getWorldPartY();
        for (Vehicle vehicle : vehicles) {
            double canvasY = vehicle.getY() - worldPartY;
            if (canvasY >= 0 && canvasY <= canvasHeight) {
                vehicleGraphicsContext.drawImage(vehicle.getVehicleImage(), vehicle.getX(), canvasY);
            }
        }
    }

    public double getCanvasStartY() {
        return canvasStartY;
    }

    public void drawBackground(GraphicsContext backgroundGraphicsContext, double canvasWidth, double canvasHeight) {
        // Clear the background canvas
        backgroundGraphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

        double imageHeight = image.getHeight();

        double startY = -gameManager.getWorldPartY() % imageHeight;

        if (startY > 0) {
            startY -= imageHeight;
        }

        for (double y = startY; y < canvasHeight; y += imageHeight) {
            backgroundGraphicsContext.drawImage(image, 0, y, canvasWidth, imageHeight);
        }
    }
}