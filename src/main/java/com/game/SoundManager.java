package com.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

public class SoundManager {
    private MediaPlayer menuMediaPlayer;
    private MediaPlayer gameMediaPlayer;

    public SoundManager() {
        initializeMenuMediaPlayer();
        initializeGameMediaPlayer();
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
