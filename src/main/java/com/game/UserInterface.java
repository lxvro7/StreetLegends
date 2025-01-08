package com.game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
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

public class UserInterface extends Application {
    private Scene scene;
    private StackPane root;
    private String difficulty = "Easy";
    private String playerName = "";
    private double windowWidth;
    private double windowHeight;
    private GraphicsContext backgroundGraphicsContext;
    private GraphicsContext vehicleGraphicsContext;
    private final SoundManager soundManager = new SoundManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        soundManager.startMenuSound();
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
        String imagePath = Objects.requireNonNull(Objects.requireNonNull(getClass().
                getResource(GameConstants.MENU_BACKGROUND_IMAGE_PATH)).toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imagePath, screenWidth, screenHeight, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        Rectangle overlay = new Rectangle(screenWidth, screenHeight, Color.BLACK);
        overlay.setOpacity(0.4);
        root.setBackground(new Background(backgroundImage));
        root.getChildren().add(overlay);
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
        VBox entryLayout = new VBox(40);
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

        Button playButton = new Button("Play");
        Button difficultyButton = new Button("Difficulty");
        Button soundButton =new Button("Sounds");
        Button helpButton = new Button("Help");
        Button backButton = new Button("Back");
        Button exitButton = new Button("Exit");
        Button menuSoundButton=new Button();
        Button gameSoundButton=new Button();

        playButton.getStyleClass().add("menu-button");
        difficultyButton.getStyleClass().add("menu-button");
        soundButton.getStyleClass().add("menu-button");
        helpButton.getStyleClass().add("menu-button");
        backButton.getStyleClass().add("menu-button");
        exitButton.getStyleClass().add("menu-button");
        menuSoundButton.getStyleClass().add("sound-button");
        gameSoundButton.getStyleClass().add("sound-button");

        difficultyButton.setOnAction(e -> switchScene(DifficultyMenu(difficultyLabel)));
        playButton.setOnAction(e -> {
            root.setBackground(null);
            switchScene((createGame(playerName)));
        });
        exitButton.setOnAction(e -> {
            Platform.exit();
        });
        backButton.setOnAction(e -> {switchScene(createEntry());});
        soundButton.setOnAction(e -> switchScene(createSoundSettingsMenu()));

        VBox titleBox = new VBox();
        titleBox.getChildren().add(title);
        titleBox.getStyleClass().add("title-box");

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(playButton, difficultyButton, soundButton,helpButton, backButton, exitButton);
        buttonBox.getStyleClass().add("button-box");

        VBox centerLayout = new VBox(30);
        centerLayout.getChildren().addAll(playerLabel, buttonBox);
        centerLayout.setAlignment(Pos.CENTER);

        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(new VBox(10, titleBox));
        menuLayout.setCenter(centerLayout);

        return menuLayout;
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

        highlightSelectedDifficulty(easyButton, mediumButton, hardButton);

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

    public Node createGame(String playerName) {
        soundManager.stopMenuSound();
        soundManager.startGameSound();
        double canvasWidth = windowWidth;
        double canvasHeight = windowHeight;
        GameEngine gameEngine = new GameEngine(playerName, difficulty,this, canvasHeight, canvasWidth);

        Canvas backgroundCanvas = new Canvas(canvasWidth, canvasHeight);
        Canvas vehicleCanvas = new Canvas(canvasWidth, canvasHeight);

        backgroundGraphicsContext = backgroundCanvas.getGraphicsContext2D();
        vehicleGraphicsContext = vehicleCanvas.getGraphicsContext2D();

        StackPane canvasContainer = new StackPane();
        canvasContainer.getChildren().addAll(backgroundCanvas, vehicleCanvas);
        canvasContainer.setAlignment(Pos.CENTER);

        Label meterLabel = new Label("Meter: 0");
        meterLabel.getStyleClass().add("meter-label");
        meterLabel.setPadding(new Insets(10));

        canvasContainer.getChildren().add(meterLabel);
        StackPane.setAlignment(meterLabel, Pos.TOP_LEFT);


        gameEngine.setCanvasHeight(canvasHeight);
        gameEngine.setCanvasWidth(canvasWidth);
        gameEngine.renderBackground(backgroundGraphicsContext);
        gameEngine.setUpdateCallback(vehicles -> gameEngine.renderVehicles(vehicleGraphicsContext, vehicles));

        gameEngine.startGameLoop();

        AnimationTimer meterUpdater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int meters = gameEngine.getDistanceTraveled();
                meterLabel.setText("Meter: " + meters);

            }
        };
        meterUpdater.start();


        // Check if any key is pressed
        scene.setOnKeyPressed(keyEvent -> {
            gameEngine.processUserInput(keyEvent.getCode());
        });

        // Check if any key is released
        scene.setOnKeyReleased(keyEvent ->  {
            gameEngine.processKeyRelease(keyEvent.getCode());
        });

        return canvasContainer;
    }

    private Node createSoundSettingsMenu() {
        VBox soundSettingsLayout = new VBox(20);
        soundSettingsLayout.setAlignment(Pos.CENTER);
        soundSettingsLayout.setPadding(new Insets(20));

        Label soundSettingsTitle = new Label("Sound Settings");
        soundSettingsTitle.getStyleClass().add("title");
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
            soundManager.setGameSoundMuted((!isMuted));
            gameSoundToggleButton.setText(isMuted ? "In-Game Sound: On" : "In-Game Sound: Off");
        });

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("sound-button");
        backButton.setOnAction(e -> switchScene(createMenu(playerName)));

        soundSettingsLayout.getChildren().addAll(soundSettingsTitle, menuSoundToggleButton, gameSoundToggleButton, backButton);

        return soundSettingsLayout;
    }

    public void showGameOverWindow(int finalDistance) {

        Label gameOverLabel = new Label("GAME OVER, " + playerName + "!");
        gameOverLabel.getStyleClass().add("game-over-label");

        Label restartInstruction = new Label("Press 'R' to Restart");
        restartInstruction.getStyleClass().add("game-over-instruction");

        Label menuInstruction = new Label("Press 'Q' to go back to Menu");
        menuInstruction.getStyleClass().add("game-over-instruction");

        Label quitGame=new Label("Press ESC to Quit the Game");
        quitGame.getStyleClass().add("game-over-instruction");

        Label meterLabel = new Label("Klasse, Sie sind " + finalDistance + " Meter gefahren!");
        meterLabel.getStyleClass().add("meter-display");

        VBox layout = new VBox(30, gameOverLabel,meterLabel, restartInstruction, menuInstruction,quitGame);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        if (root.getChildren().size() > 1) {
            root.getChildren().remove(1);
        }

        root.getChildren().add(layout);

        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case R -> restartGame();
                case Q -> switchScene(createMenu(playerName));
                case ESCAPE -> Platform.exit();
            }
        });
    }

    private void restartGame() {
        switchScene(createGame(playerName));
    }

    // Switches to a new scene by clearing the existing layout and adding the new layout.
    private void switchScene(Node newScene) {

        if (root.getChildren().size() > 1) {
            root.getChildren().remove(1);
        }
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
    public GraphicsContext getBackgroundGraphicsContext() {
        return backgroundGraphicsContext;
    }

    public GraphicsContext getVehicleGraphicsContext() {
        return vehicleGraphicsContext;
    }
}
