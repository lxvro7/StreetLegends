package com.game;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;


public class MainMenu {
    private final UserInterface userInterface;
    private final SoundManager soundManager;
    private String playerName;
    private String difficulty = "Easy";

    public MainMenu(UserInterface userInterface, SoundManager soundManager) {
        this.userInterface = userInterface;
        this.soundManager = soundManager;
    }
    public Node createMenu(String playerName) {
        this.playerName = playerName;

        soundManager.stopGameSound();
        if (!soundManager.isMenuSoundMuted()) {
            soundManager.startMenuSound();
        }

        userInterface.background();

        Label title = new Label("Street Legends");
        title.getStyleClass().add("title");

        Label playerLabel = new Label("Welcome: " + playerName);
        playerLabel.getStyleClass().add("player-label");

        Label difficultyLabel = new Label("Difficulty: " + difficulty);
        difficultyLabel.getStyleClass().add("difficulty-label");

        Button playButton = new Button("Play");
        Button difficultyButton = new Button("Difficulty");
        Button soundButton = new Button("Sounds");
        Button helpButton = new Button("Help");
        Button backButton = new Button("Back");
        Button exitButton = new Button("Exit");

        playButton.getStyleClass().add("menu-button");
        difficultyButton.getStyleClass().add("menu-button");
        soundButton.getStyleClass().add("menu-button");
        helpButton.getStyleClass().add("menu-button");
        backButton.getStyleClass().add("menu-button");
        exitButton.getStyleClass().add("menu-button");

        difficultyButton.setOnAction(e -> userInterface.switchSceneWithFade(userInterface.getDifficultyMenu().createMenu(difficultyLabel)));
        playButton.setOnAction(e -> {
            userInterface.getRoot().setBackground(null);
            userInterface.switchScene(userInterface.createGame(playerName));
        });
        exitButton.setOnAction(e -> Platform.exit());
        helpButton.setOnAction(e -> userInterface.switchSceneWithFade(userInterface.getHelpMenu().createHelpMenu()));
        backButton.setOnAction(e -> userInterface.switchSceneWithFade(userInterface.getEntryScreen().createScreen()));
        soundButton.setOnAction(e -> userInterface.switchSceneWithFade(userInterface.getSoundSettingsMenu().createSoundSetting()));
        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(playButton, difficultyButton, soundButton, helpButton, backButton, exitButton);
        buttonBox.getStyleClass().add("button-box");

        VBox centerLayout = new VBox(30);
        centerLayout.getChildren().addAll(playerLabel, buttonBox);
        centerLayout.setAlignment(Pos.CENTER);

        BorderPane menuLayout = new BorderPane();
        VBox titleBox = new VBox(10, title);
        titleBox.setAlignment(Pos.CENTER);
        menuLayout.setTop(titleBox);
        menuLayout.setCenter(centerLayout);

        return menuLayout;
    }
}