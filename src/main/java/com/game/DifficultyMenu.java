package com.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DifficultyMenu {
    private final UserInterface userInterface;
    private final SoundManager soundManager;
    private String difficulty = "Easy";

    public DifficultyMenu(UserInterface userInterface, SoundManager soundManager) {
        this.userInterface = userInterface;
        this.soundManager = soundManager;
    }

    public Node createMenu(Label difficultyLabel) {
        Label diffTitle = new Label("Please select:");
        diffTitle.getStyleClass().add("Diff");

        Button easyButton = new Button("Easy");
        easyButton.getStyleClass().add("button");
        Button mediumButton = new Button("Medium");
        mediumButton.getStyleClass().add("button");
        Button hardButton = new Button("Hard");
        hardButton.getStyleClass().add("button");

        highlightSelectedDifficulty(easyButton, mediumButton, hardButton);

        easyButton.setOnAction(e -> {
            difficulty = "Easy";
            userInterface.setDifficulty(difficulty);
            userInterface.getGameEngine().setGameDifficulty(difficulty);

            userInterface.switchSceneWithFade(userInterface.getMainMenu().createMenu(userInterface.getPlayerName()));
        });

        mediumButton.setOnAction(e -> {
            difficulty = "Medium";
            userInterface.setDifficulty(difficulty);
            userInterface.getGameEngine().setGameDifficulty(difficulty);

            userInterface.switchSceneWithFade(userInterface.getMainMenu().createMenu(userInterface.getPlayerName()));
        });

        hardButton.setOnAction(e -> {
            difficulty = "Hard";
            userInterface.setDifficulty(difficulty);
            userInterface.getGameEngine().setGameDifficulty(difficulty);

            userInterface.switchSceneWithFade(userInterface.getMainMenu().createMenu(userInterface.getPlayerName()));
        });

        VBox difficultyBox = new VBox(20, diffTitle, easyButton, mediumButton, hardButton);
        difficultyBox.getStyleClass().add("difficulty-box");
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyBox.setPadding(new Insets(20));

        return difficultyBox;
    }

    private void highlightSelectedDifficulty(Button easy, Button medium, Button hard) {
        easy.getStyleClass().remove("selected-difficulty");
        medium.getStyleClass().remove("selected-difficulty");
        hard.getStyleClass().remove("selected-difficulty");

        switch (difficulty) {
            case "Easy":
                easy.getStyleClass().add("selected-difficulty");
                break;
            case "Medium":
                medium.getStyleClass().add("selected-difficulty");
                break;
            case "Hard":
                hard.getStyleClass().add("selected-difficulty");
                break;
        }
    }
}