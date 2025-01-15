package com.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SoundSettingMenu {
    private final UserInterface userInterface;
    private final SoundManager soundManager;

    public SoundSettingMenu(UserInterface userInterface, SoundManager soundManager) {
        this.userInterface = userInterface;
        this.soundManager = soundManager;
    }

    public Node createSoundSetting() {
        VBox soundSettingsLayout = new VBox(20);
        soundSettingsLayout.getStyleClass().add("sound-settings-layout");
        soundSettingsLayout.setAlignment(Pos.CENTER);
        soundSettingsLayout.setPadding(new Insets(20));

        Label soundSettingsTitle = new Label("Sound Settings");
        soundSettingsTitle.getStyleClass().add("title");

        Label volumeLabel = new Label("Volume: " + (int) (soundManager.getVolume() * 100) + "%");
        volumeLabel.getStyleClass().add("sound-label");


        Slider volumeSlider = new Slider(0, 1, soundManager.getVolume());
        volumeSlider.getStyleClass().add("volume-slider");
        volumeSlider.setMajorTickUnit(0.1);
        volumeSlider.setBlockIncrement(0.05);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setMaxWidth(300);

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            soundManager.setVolume(newVal.doubleValue());
            volumeLabel.setText("Volume: " + (int) (newVal.doubleValue() * 100) + "%");
        });

        Button menuSoundToggleButton = new Button(soundManager.isMenuSoundMuted() ? "Menu Sound: Off" : "Menu Sound: On");
        menuSoundToggleButton.getStyleClass().add("sound-button");
        menuSoundToggleButton.setOnAction(e -> {
            boolean isMuted = soundManager.isMenuSoundMuted();
            soundManager.setMenuSoundMuted(!isMuted);
            menuSoundToggleButton.setText(isMuted ? "Menu Sound: On" : "Menu Sound: Off");

            if (!isMuted) {
                soundManager.startMenuSound();
            }
        });

        Button gameSoundToggleButton = new Button(soundManager.isGameSoundMuted() ? "In-Game Sound: Off" : "In-Game Sound: On");
        gameSoundToggleButton.getStyleClass().add("sound-button");
        gameSoundToggleButton.setOnAction(e -> {
            boolean isMuted = soundManager.isGameSoundMuted();
            soundManager.setGameSoundMuted(!isMuted);
            gameSoundToggleButton.setText(isMuted ? "In-Game Sound: On" : "In-Game Sound: Off");
        });

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("sound-button");
        backButton.setOnAction(e -> userInterface.switchSceneWithFade(userInterface.getMainMenu().createMenu(userInterface.getPlayerName())));

        soundSettingsLayout.getChildren().addAll(soundSettingsTitle, volumeLabel, volumeSlider, menuSoundToggleButton, gameSoundToggleButton, backButton);

        soundSettingsLayout.setTranslateX(800);
        Timeline slideIn = new Timeline(new KeyFrame(Duration.seconds(0.5), new KeyValue(soundSettingsLayout.translateXProperty(), 0))
        );
        slideIn.play();

        return soundSettingsLayout;
    }
}