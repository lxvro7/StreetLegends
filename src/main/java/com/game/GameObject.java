package com.game;

import javafx.scene.image.Image;

import java.util.Objects;

public abstract class GameObject {
    protected double x;
    protected double y;
    protected double radius;
    protected String imagePath;
    protected Image image;
    private final double imageScalingFactor;


    public GameObject(double x, double y, String imagePath, double radius, double imageScalingFactor) {
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;
        this.radius = radius;
        System.out.println(radius);
        this.imageScalingFactor = imageScalingFactor;
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            // Scale the Image
            this.image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getRadius() {
        return radius;
    }

    public double getImageScalingFactor() {
        return imageScalingFactor;
    }

}

