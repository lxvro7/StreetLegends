package com.game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

// TODO Alton: Create a Scoreboard button, and the layout!

public class GameUI extends Application {
    private Scene scene;
    private StackPane root;
    private String difficulty = "Easy";
    private String playerName = "";
    private double windowWidth;
    private double windowHeight;
    private Vehicle.color selectedColor = Vehicle.color.BLUE;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();
        background();
        Node entryLayout = createEntry();
        root.getChildren().add(entryLayout);
        fadeIn(entryLayout);
        readFromCss(scene);
        primaryStage.setTitle("Street Legends");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        windowHeight = primaryStage.getHeight();
        windowWidth = primaryStage.getWidth();
    }

    // Sets the background image and overlay for the application, adjusting it to the screen size.
    public void background() {
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        String imagePath = Objects.requireNonNull(getClass().getResource("/images/banner.jpg")).toExternalForm();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imagePath, screenWidth, screenHeight, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        Rectangle overlay = new Rectangle(screenWidth, screenHeight, Color.BLACK);
        overlay.setOpacity(0.4);
        root.getChildren().add(overlay);
        root.setBackground(new Background(backgroundImage));
        scene = new Scene(root, screenWidth, screenHeight);
    }

    // Creates the layout for the player's name entry screen, including labels, text fields, and a button.
    public Node createEntry() {

        Label welcomeLabel = new Label("Welcome to Street Legends");
        welcomeLabel.getStyleClass().add("title");

        TextField nameField = new TextField();
        nameField.getStyleClass().add("name-field");
        nameField.setPromptText("Please enter your name...");

        Button startButton = new Button("Start");
        startButton.getStyleClass().add("menu-button");

        fadeIn(startButton);

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        startButton.setOnAction(event -> {
            playerName = nameField.getText().trim(); // save name
            if (playerName.isEmpty()) {
                errorLabel.setText("Please enter your Username!");
                errorLabel.setVisible(true);
            } else {
                switchScene(createMenu(playerName));
            }
        });
        VBox entryLayout = new VBox(20);
        entryLayout.getStyleClass().add("vbox-layout");
        entryLayout.getChildren().addAll(welcomeLabel, nameField,errorLabel ,startButton );
        return entryLayout;
    }

    // Applies a fade-in transition effect to make the specified node gradually appear on the screen.
    public void fadeIn(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    // Creates the main menu layout with buttons for playing the game, changing difficulty, getting help, and exiting.
    public BorderPane createMenu(String playerName) {
        Label title = new Label("Street Legends");
        title.getStyleClass().add("title");

        Label playerLabel = new Label("Welcome: " + playerName);
        playerLabel.getStyleClass().add("player-label");

        Label difficultyLabel = new Label("Difficulty: " + difficulty);
        difficultyLabel.getStyleClass().add("difficulty-label");

        Label color = new Label("Color: " + capitalize(selectedColor.toString()));
        color.getStyleClass().add("color-label");

        Button playButton = new Button("Play");
        Button difficultyButton = new Button("Difficulty");
        Button colorButton = new Button("Select color");
        Button helpButton = new Button("Help");
        Button backButton = new Button("Back");
        Button exitButton = new Button("Exit");

        playButton.getStyleClass().add("menu-button");
        difficultyButton.getStyleClass().add("menu-button");
        colorButton.getStyleClass().add("menu-button");
        helpButton.getStyleClass().add("menu-button");
        backButton.getStyleClass().add("menu-button");
        exitButton.getStyleClass().add("menu-button");

        difficultyButton.setOnAction(e -> switchScene(DifficultyMenu(difficultyLabel)));
        playButton.setOnAction(e -> {
            root.setBackground(null);
            switchScene((createGame(playerName)));
        });
        exitButton.setOnAction(e -> {
            Platform.exit();
        });
        backButton.setOnAction(e -> {
            switchScene(createEntry());
        });
        colorButton.setOnAction(e -> switchScene(createColorSelection(color)));

        VBox titleBox = new VBox();
        titleBox.getChildren().add(title);
        titleBox.getStyleClass().add("title-box");

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(playButton, difficultyButton, colorButton, helpButton, backButton, exitButton);
        buttonBox.getStyleClass().add("button-box");

        VBox centerLayout = new VBox(30);
        centerLayout.getChildren().addAll(playerLabel, buttonBox);
        centerLayout.setAlignment(Pos.CENTER);

        HBox bottomInfoBox = new HBox(20);
        bottomInfoBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomInfoBox.getChildren().addAll(color, difficultyLabel);

        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(new VBox(10, titleBox));
        menuLayout.setCenter(centerLayout);
        menuLayout.setBottom(bottomInfoBox);

        return menuLayout;
    }
    private Node createColorSelection(Label color) {
        Label colorLabel = new Label("Select a color:");
        colorLabel.getStyleClass().add("color-label");

        Button blackButton = new Button("Black");
        Button blueButton = new Button("Blue");
        Button greenButton = new Button("Green");
        blackButton.setOnAction(e -> {
            selectedColor = Vehicle.color.BLACK;
            color.setText("Color: " + selectedColor);
            switchScene(createMenu(playerName));
        });
        blueButton.setOnAction(e -> {
            selectedColor = Vehicle.color.BLUE;
            color.setText("Color: " + selectedColor);
            switchScene(createMenu(playerName));
        });
        greenButton.setOnAction(e -> {
            selectedColor = Vehicle.color.GREEN;
            color.setText("Color: " + selectedColor);
            switchScene(createMenu(playerName));
        });
        VBox colorBox = new VBox(20, colorLabel, blackButton, blueButton, greenButton);
        colorBox.setAlignment(Pos.CENTER);
        colorBox.getStyleClass().add("vbox-layout");

        return colorBox;
    }

    private String capitalize(String text){
        if(text==null || text.isEmpty()) return text;
        return text.substring(0,1).toUpperCase()+text.substring(1).toLowerCase();
    }

    // Creates a menu layout for selecting the difficulty level (Easy, Medium, Hard).
    public Node DifficultyMenu(Label difficultyLabel) {
        Label Diff = new Label("Please select:");
        Diff.getStyleClass().add("Diff");

        Button easyButton = new Button("Easy");
        easyButton.getStyleClass().add("button");
        Button mediumButton = new Button("Medium");
        mediumButton.getStyleClass().add("button");
        Button hardButton = new Button("Hard");
        hardButton.getStyleClass().add("button");

        easyButton.setOnAction(e -> {
            difficulty = "Easy";
            difficultyLabel.setText("Difficulty: " + difficulty);
            switchScene(createMenu(playerName));
        });
        mediumButton.setOnAction(e -> {
            difficulty = "Medium";
            difficultyLabel.setText("Difficulty: " + difficulty);
            switchScene(createMenu(playerName));
        });
        hardButton.setOnAction(e -> {
            difficulty = "Hard";
            difficultyLabel.setText("Difficulty: " + difficulty);
            switchScene(createMenu(playerName));

        });

        VBox difficultyBox = new VBox(10, Diff,easyButton, mediumButton, hardButton);
        difficultyBox.getStyleClass().add("difficulty-box");
        return difficultyBox;
    }


    public Node createGame(String playerName) {
        String roadImagePath = Objects.requireNonNull(getClass().
                getResource("/images/street/road.png")).toExternalForm();

        double canvasWidth = windowWidth;
        double canvasHeight = windowHeight;
        GameHandler gameHandler = new GameHandler(playerName, selectedColor, difficulty,this);

        Canvas backgroundCanvas = new Canvas(canvasWidth, canvasHeight);
        Canvas vehicleCanvas = new Canvas(canvasWidth, canvasHeight);

        GraphicsContext backgroundGraphicsContext = backgroundCanvas.getGraphicsContext2D();
        GraphicsContext vehicleGraphicsContext = vehicleCanvas.getGraphicsContext2D();

        StackPane canvasContainer = new StackPane();
        canvasContainer.getChildren().addAll(backgroundCanvas, vehicleCanvas);
        canvasContainer.setAlignment(Pos.CENTER);

        VBox gameLayout = new VBox();
        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.getChildren().addAll(canvasContainer);

        gameHandler.getGameWorld().drawBackground(backgroundGraphicsContext, roadImagePath, canvasWidth, canvasHeight);
        gameHandler.setUpdateCallback(vehicles -> gameHandler.getGameWorld().drawVehicles(vehicles, vehicleGraphicsContext,
                canvasWidth, canvasHeight));

        gameHandler.startGameLoop();

        // Check if any key is pressed
        scene.setOnKeyPressed(keyEvent -> {
            gameHandler.processUserInput(keyEvent.getCode());
        });

        // Check if any key is released
        scene.setOnKeyReleased(keyEvent ->  {
            gameHandler.processKeyRelease(keyEvent.getCode());
        });

        return gameLayout;
    }
    public static void showGameOverWindow(String playerName, GameUI gameUI) {
        System.out.println("Game Over Window wird angezeigt...");

        Stage gameOverStage = new Stage();
        gameOverStage.setTitle("Game Over");

        Stage highscore =new Stage();
        highscore.setTitle("Highscore");

        Label gameOverLabel = new Label("Game Over, " + playerName + "!" );
        gameOverLabel.getStyleClass().add("game-over-label");

        Button restartButton = new Button("Restart");
        restartButton.getStyleClass().add("game-over-button");

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("game-over-button");

        restartButton.setOnAction(e -> {
            gameOverStage.close();
            gameUI.restartGame();
        });

        exitButton.setOnAction(e -> Platform.exit());

        VBox layout = new VBox(20, gameOverLabel, restartButton, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("game-over-box");

        Scene scene = new Scene(layout, 400, 300);
        gameUI.readFromCss(scene);
        gameOverStage.setScene(scene);
        gameOverStage.requestFocus();
        gameOverStage.show();
    }
    private void restartGame() {
        GameHandler gameHandler = new GameHandler(playerName, selectedColor, difficulty, this);
        gameHandler.getGameWorld().reset();
        switchScene(createGame(playerName));
    }

    // Switches to a new scene by clearing the existing layout and adding the new layout.
    private void switchScene(Node newScene) {
        // Clear the current content
        root.getChildren().clear();
        // Add the new layout
        root.getChildren().add(newScene);
    }

    // Loads the CSS stylesheet to apply custom styling to the application.
    private void readFromCss(Scene scene) {
        try {
            URL cssUrl = getClass().getResource("/styles.css");
            if (cssUrl == null) {
                System.err.println("styles.css not found!");
            } else {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("CSS loaded: " + cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
