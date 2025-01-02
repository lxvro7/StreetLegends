package com.game;

public class KeyEventHandler {
    private final Player player;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean reversePressed = false;

    public KeyEventHandler(Player player) {
        this.player = player;
    }

    public void onUpArrowPressed() {
        upPressed = true;
        player.accelerate();
    }

    public void onDownArrowPressed() {
        downPressed = true;
        player.brake();
    }

    public void onLeftArrowPressed() {
        leftPressed = true;
        player.driveLeft(0.016);
    }

    public void onRightArrowPressed() {
        rightPressed = true;
        player.driveRight(0.016);
    }

    public void onQPressed() {
        reversePressed = true;
        player.reverse();
    }

    public void onKeyReleased(String key) {
        switch (key) {
            case "UP":
                upPressed = false;
                break;
            case "DOWN":
                downPressed = false;
                break;
            case "LEFT":
                leftPressed = false;
                break;
            case "RIGHT":
                rightPressed = false;
                break;
            case "Q":
                reversePressed = false;
                break;
        }
    }

    public boolean isAnyKeyPressed() {
        return upPressed || downPressed || leftPressed || rightPressed || reversePressed;
    }
}
