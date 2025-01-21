package com.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public class GameRenderer {
    private final Image image;
    private final GameManager gameManager;

    public GameRenderer(GameManager gameManager) {
        this.gameManager = gameManager;
        image = createImage(GameConstants.GAME_BACKGROUND_IMAGE_PATH);
    }

    public void drawObjects(ArrayList<GameObject> objects,
                            GraphicsContext vehicleGraphicsContext, double canvasWidth, double canvasHeight) {

        vehicleGraphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
        double worldPartY = gameManager.getWorldPartY();
        for (GameObject object : objects) {
            double canvasY = object.getY() - worldPartY;
            if (canvasY >= 0 && canvasY <= canvasHeight && object.getImage() != null) {
                double imageWidth = object.getImage().getWidth();
                double imageHeight = object.getImage().getHeight();

                double scaleFactorX;
                double scaleFactorY;
                scaleFactorX = canvasWidth * object.getImageScalingFactor() / imageWidth;
                scaleFactorY = scaleFactorX;

                double scaledWidth = imageWidth * scaleFactorX;
                double scaledHeight = imageHeight * scaleFactorY;

                double topLeftX = object.getX() - (scaledWidth / 2);
                double topLeftY = canvasY - (scaledHeight / 2);

                vehicleGraphicsContext.drawImage(object.getImage(), topLeftX, topLeftY, scaledWidth, scaledHeight);
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
