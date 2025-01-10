package com.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

public class SoundManager {
    private MediaPlayer menuMediaPlayer;
    private MediaPlayer gameMediaPlayer;
    private MediaPlayer collisionMediaPlayer; // Neuer MediaPlayer für Kollisionssound


    public SoundManager() {
        initializeMenuMediaPlayer();
        initializeGameMediaPlayer();
        initializeCollisionMediaPlayer();
    }

    private void initializeMenuMediaPlayer() {
        String menuSoundPath = GameConstants.MENU_AUDIO;
        menuMediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().
                getResource(menuSoundPath)).toExternalForm()));
        menuMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private void initializeGameMediaPlayer() {
        String gameSoundPath = GameConstants.GAME_AUDIO;
        gameMediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().
                getResource(gameSoundPath)).toExternalForm()));
        gameMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }
    private void initializeCollisionMediaPlayer() {
        String collisionSoundPath = GameConstants.CRASH_AUDIO;
        collisionMediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().
                getResource(collisionSoundPath)).toExternalForm()));
    }

    public void startGameSound() {
        if (!gameMediaPlayer.isMute()) {
            gameMediaPlayer.play();
        }
    }

    public void stopGameSound() {
        if (gameMediaPlayer != null) {
            gameMediaPlayer.pause();
        }
    }
    public void playCollisionSound() {
        if (collisionMediaPlayer != null) {
            collisionMediaPlayer.stop();
            collisionMediaPlayer.seek(Duration.ZERO);

            collisionMediaPlayer.setOnEndOfMedia(() -> collisionMediaPlayer.stop());

            collisionMediaPlayer.play();
        }
    }
    public void stopCollisionSound() {
        if (collisionMediaPlayer != null && collisionMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            collisionMediaPlayer.stop();
        }
    }


    public void startMenuSound() {
        if (!menuMediaPlayer.isMute()) {
            if (menuMediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                menuMediaPlayer.seek(Duration.ZERO);
            }
            menuMediaPlayer.play();
        }
    }

    public void stopMenuSound() {
        if (menuMediaPlayer != null) {
            menuMediaPlayer.stop();
        }
    }

    public boolean isMenuSoundMuted() {
        return menuMediaPlayer.isMute();
    }

    public void setMenuSoundMuted(boolean mute) {
        menuMediaPlayer.setMute(mute);
    }

    public boolean isGameSoundMuted() {
        return gameMediaPlayer.isMute();
    }

    public void setGameSoundMuted(boolean mute) {
        gameMediaPlayer.setMute(mute);
    }

}
