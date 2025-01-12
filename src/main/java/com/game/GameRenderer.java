package com.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public class GameRenderer {
    private final Image image;
    private final GameManager gameManager;

    // TODO Lovro: Add double buffering

    public GameRenderer(GameManager gameManager) {
        this.gameManager = gameManager;
        image = createImage(GameConstants.GAME_BACKGROUND_IMAGE_PATH);
    }


    public void drawVehicles(ArrayList<Vehicle> vehicles,
                             GraphicsContext vehicleGraphicsContext, double canvasWidth, double canvasHeight) {

        vehicleGraphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

        double worldPartY = gameManager.getWorldPartY();

        for (Vehicle vehicle : vehicles) {
            double canvasY = vehicle.getY() - worldPartY;
            if (canvasY >= 0 && canvasY <= canvasHeight) {
                double imageWidth = vehicle.getVehicleImage().getWidth();
                double imageHeight = vehicle.getVehicleImage().getHeight();

                double scaleFactorX = canvasWidth * 0.125 / imageWidth;
                double scaleFactorY = scaleFactorX;
                // if(gameManager.IsHütchenSpawnNeeded) vehicleGraphicsContext.drawImage()
                double scaledWidth = imageWidth * scaleFactorX;
                double scaledHeight = imageHeight * scaleFactorY;
                vehicleGraphicsContext.drawImage(vehicle.getVehicleImage(), vehicle.getX(), canvasY, scaledWidth, scaledHeight);
            }
        }
    }

    public void drawBackground(GraphicsContext backgroundGraphicsContext, double canvasWidth, double canvasHeight) {

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

    public Image createImage(String backgroundImagePath) {
        return new Image(Objects.requireNonNull(getClass().getResource(backgroundImagePath)).toExternalForm());
    }
}

