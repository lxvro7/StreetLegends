package com.game;

public class CanvasContext {
    private static double canvasWidth;
    private static double canvasHeight;

    public static void initialize(double width, double height) {
        canvasWidth = width;
        canvasHeight = height;
    }

    public static double getCanvasWidth() {
        return canvasWidth;
    }

    public static double getCanvasHeight() {
        return canvasHeight;
    }
}
