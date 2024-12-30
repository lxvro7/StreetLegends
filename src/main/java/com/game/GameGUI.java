package com.game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// TODO: Css styles in separate file (styles.css) ->
//  scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

public class GameGUI extends Application {
    private Scene scene;
    private StackPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();

        Node entryLayout = createEntry();
        root.getChildren().add(entryLayout);

        scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Street Legends");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true); // Maybe change?
        primaryStage.show();
    }

    public Node createEntry() {
        // TODO: Create a welcome screen with a text field where the user can enter their name.
        // If button clicked, because name is fine, switch to menu scene -> switchScene(createMenu()).
        return null;
    }

    public BorderPane createMenu() {
        Label title = new Label("Street Legends");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Lucida Handwriting';");

        Button playButton = new Button("Play");
        Button difficultyButton = new Button("Difficulty");
        Button helpButton = new Button("Help");
        Button exitButton = new Button("Exit");

        // Set button size
        playButton.setPrefWidth(300);
        playButton.setPrefHeight(100);
        difficultyButton.setPrefWidth(300);
        difficultyButton.setPrefHeight(100);
        helpButton.setPrefWidth(300);
        helpButton.setPrefHeight(100);
        exitButton.setPrefWidth(300);
        exitButton.setPrefHeight(100);

        // Set button styles
        playButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Verdana';");
        difficultyButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Verdana';");
        helpButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Verdana';");
        exitButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Verdana';");

        HBox titleBox = new HBox();
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(30, 0, 0, 0));

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(playButton, difficultyButton, helpButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 20, 20, 20));

        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(titleBox);
        menuLayout.setCenter(buttonBox);

        return menuLayout;
    }
    // Not tested yet!
    private void switchScene(Node newScene) {
        // Clear the current content
        root.getChildren().clear();
        // Add the new layout
        root.getChildren().add(newScene);
    }
}
