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
import java.net.URL;



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
        scene = new Scene(root, 600, 400);
        // CSS-Data
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
       /* try {
            URL cssUrl = getClass().getResource("/styles.css");
            if (cssUrl == null) {
                System.err.println("styles.css not found!");
            } else {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("CSS loaded: " + cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        primaryStage.setTitle("Street Legends");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public Node createEntry() {
        // TODO: Create a welcome screen with a text field where the user can enter their name.
        // If button clicked, because name is fine, switch to menu scene otherwise you will get a error -> switchScene(createMenu()).


        Label welcomeLabel = new Label("Willkommen zu Street Legends!");
        welcomeLabel.getStyleClass().add("title");
        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.setPromptText("Please enter your name");

        Button startButton = new Button("Start");
        startButton.getStyleClass().add("menu-button");

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        startButton.setOnAction(event -> {
            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                errorLabel.setText("Please enter your Username!");
                errorLabel.setVisible(true);
            } else {
                switchScene(createMenu());
            }
        });

        VBox layout = new VBox(20);
        layout.getStyleClass().add("vbox-layout");
        layout.getChildren().addAll(welcomeLabel, nameField, startButton, errorLabel);

        return layout;
    }

    public BorderPane createMenu() {
        Label title = new Label("Street Legends");
        title.getStyleClass().add("title");

        Button playButton = new Button("Play");
        Button difficultyButton = new Button("Difficulty");
        Button helpButton = new Button("Help");
        Button exitButton = new Button("Exit");

        // CSS-Klasse für die Buttons
        playButton.getStyleClass().add("menu-button");
        difficultyButton.getStyleClass().add("menu-button");
        helpButton.getStyleClass().add("menu-button");
        exitButton.getStyleClass().add("menu-button");

        // Layout für die Buttons
        HBox titleBox = new HBox();
        titleBox.getChildren().add(title);
        titleBox.getStyleClass().add("title-box");

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(playButton, difficultyButton, helpButton, exitButton);
        buttonBox.getStyleClass().add("button-box");

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
