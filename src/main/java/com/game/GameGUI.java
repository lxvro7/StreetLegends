package com.game;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;

public class GameGUI extends Application {
    private Scene scene;
    private StackPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        root = new StackPane();

        Node entryLayout = createEntry();
        root.getChildren().add(entryLayout);
        scene = new Scene(root, screenWidth, screenHeight);

        readFromCss();

        primaryStage.setTitle("Street Legends");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public Node createEntry() {
        // TODO: Add a Background Image

        Label welcomeLabel = new Label("Welcome to Street Legends");
        welcomeLabel.getStyleClass().add("title");

        TextField nameField = new TextField();
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

        // TODO: change the entry like the 1st Discord Pic, see createMenu (BorderPane, VBox, etc.)

        VBox entryLayout = new VBox(20);
        entryLayout.getStyleClass().add("vbox-layout");
        entryLayout.getChildren().addAll(welcomeLabel, nameField, startButton, errorLabel);

        return entryLayout;
    }

    public BorderPane createMenu() {
        Label title = new Label("Street Legends");
        title.getStyleClass().add("title");

        Button playButton = new Button("Play");
        Button difficultyButton = new Button("Difficulty");
        Button helpButton = new Button("Help");
        Button exitButton = new Button("Exit");

        playButton.getStyleClass().add("menu-button");
        difficultyButton.getStyleClass().add("menu-button");
        helpButton.getStyleClass().add("menu-button");
        exitButton.getStyleClass().add("menu-button");

        HBox titleBox = new HBox();
        titleBox.getChildren().add(title);
        titleBox.getStyleClass().add("title-box");

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(playButton, difficultyButton, helpButton, exitButton);
        buttonBox.getStyleClass().add("button-box");

        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(titleBox);
        menuLayout.setCenter(buttonBox);

        // Check Discord Pic (second one)

        // Tip for these 2 TODOS, Label1 = "Welcome: ", Label2 = (change dynamically), same for the diffPart
        // TODO: Add "Welcome Label, -> Welcome: <Lovro>, <Alton>, ...
        // TODO: Add "Difficulty Label, bottom right -> Difficulty: <Easy>, <Middle>, <Hard>
        // TODO: If difficultyButton pressed, call switchScene(createDifficultyMenu())

        return menuLayout;
    }

    public Node createDifficultyMenu() {
        // TODO: Create the Layout 3rd Discord Pic!
        // TODO: If you selected Easy, Medium or Hard, it goes back to the Menu screen tip: SwitchScene(createMenu)
        //  AND the difficulty level in the menu bottom right changes desired to difficulty level

        return null;
    }

    private void switchScene(Node newScene) {
        // Clear the current content
        root.getChildren().clear();
        // Add the new layout
        root.getChildren().add(newScene);
    }

    private void readFromCss() {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        try {
            URL cssUrl = getClass().getResource("/styles.css");
            if (cssUrl == null) {
                System.err.println("styles.css not found!");
            }
            else {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("CSS loaded: " + cssUrl.toExternalForm());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
