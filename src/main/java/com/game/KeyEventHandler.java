package com.game;

public class KeyEventHandler {
    private final Player player;

    public KeyEventHandler(Player player) {
        this.player = player;
    }

    public void onLeftArrowPressed() {
        player.driveLeft();
    }

    public void onRightArrowPressed() {
        player.driveRight();
    }

    public void onEscapePressed() {
        // TODO: Pause the Game
    }
}