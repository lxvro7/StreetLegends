package com.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class EntryScreen {
    private final UserInterface userInterface;

    public EntryScreen(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public Node createScreen() {
        Label welcomeLabel = new Label("Welcome to Street Legends");
        welcomeLabel.getStyleClass().add("title");

        TextField nameField = new TextField();
        nameField.getStyleClass().add("name-field");
        nameField.setPromptText("Please enter your name...");

        Button startButton = new Button("Start");
        startButton.getStyleClass().add("menu-button");

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        startButton.setOnAction(event -> {
            String playerName = nameField.getText().trim(); // save name
            if (playerName.isEmpty()) {
                errorLabel.setText("Please enter your Username!");
                errorLabel.setVisible(true);
            } else {
                userInterface.setPlayerName(playerName);
                userInterface.switchSceneWithFade(userInterface.getMainMenu().createMenu(playerName));
            }
        });

        VBox entryLayout = new VBox(40);
        entryLayout.getStyleClass().add("vbox-layout");
        entryLayout.getChildren().addAll(welcomeLabel, nameField, errorLabel, startButton);
        entryLayout.setTranslateX(800);

        Timeline slideIn = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(entryLayout.translateXProperty(), 0))
        );
        slideIn.play();

        return entryLayout;
    }
}