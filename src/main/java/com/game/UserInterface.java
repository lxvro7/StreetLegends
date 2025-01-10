package com.game;
import javafx.animation.*;
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
        scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        readFromCss(scene);
        primaryStage.setTitle("Street Legends");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        windowHeight = primaryStage.getHeight();
        windowWidth = primaryStage.getWidth();
        System.out.println(windowWidth);
    }


    // Sets the background image and overlay for the application, adjusting it to the screen size.
    public void background() {
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        String imagePath = Objects.requireNonNull(getClass().getResource(GameConstants.MENU_BACKGROUND_IMAGE_PATH)).toExternalForm();

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imagePath, screenWidth, screenHeight, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(backgroundImage));
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
        entryLayout.setTranslateX(800);
        Timeline slideIn = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(entryLayout.translateXProperty(), 0))
        );
        slideIn.play();
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
        soundManager.stopGameSound();
        if(!soundManager.isMenuSoundMuted()) {
            soundManager.startMenuSound();
        }

        background();
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
        helpButton.setOnAction(e -> switchScene(createHelpMenu()));
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

    // Creates a menu layout for selecting the difficulty level (Easy, Medium, Hard) with animations.
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

        VBox difficultyBox = new VBox(20, Diff, easyButton, mediumButton, hardButton);
        difficultyBox.getStyleClass().add("difficulty-box");
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyBox.setPadding(new Insets(20));

        difficultyBox.setTranslateX(800); // Start position outside the screen
        Timeline slideIn = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(difficultyBox.translateXProperty(), 0))
        );
        slideIn.play();

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
        GameEngine gameEngine = new GameEngine(playerName, difficulty,this, canvasHeight);

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

        root.getChildren().clear();
        startCountdown(() -> gameEngine.startGameLoop());

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
        soundSettingsLayout.getStyleClass().add("sound-settings-layout");
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

        soundSettingsLayout.setTranslateX(800);
        Timeline slideIn = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(soundSettingsLayout.translateXProperty(), 0))
        );
        slideIn.play();
        return soundSettingsLayout;
    }

    private void startCountdown(Runnable onCountdownFinished) {
        Label countdownLabel = new Label();
        countdownLabel.getStyleClass().add("countdown-label");
        Platform.runLater(() -> root.getChildren().add(countdownLabel));
        StackPane.setAlignment(countdownLabel, Pos.CENTER);

        int[] countdownValues = {3, 2, 1};
        Timeline countdownTimeline = new Timeline();

        for (int i = 0; i < countdownValues.length; i++) {
            int count = countdownValues[i];
            countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i), event -> {
                countdownLabel.setText(String.valueOf(count));
                countdownLabel.setScaleX(1.0);
                countdownLabel.setScaleY(1.0);
                countdownLabel.setOpacity(1.0);

                FadeTransition fade = new FadeTransition(Duration.seconds(1), countdownLabel);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.play();
            }));
        }

        countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(countdownValues.length), event -> {
            countdownLabel.setText("GO!");
            countdownLabel.getStyleClass().remove("countdown-label");
            countdownLabel.getStyleClass().add("go-label");

            FadeTransition fade = new FadeTransition(Duration.seconds(1), countdownLabel);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.play();
        }));

        countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(countdownValues.length + 1), event -> {
            root.getChildren().remove(countdownLabel);
            onCountdownFinished.run();
        }));

        countdownTimeline.play();
    }

    public void showGameOverWindow(int finalDistance) {
        soundManager.stopGameSound();
        soundManager.playCollisionSound();

        Rectangle overlay = new Rectangle(windowWidth, windowHeight, Color.GRAY);
        overlay.setOpacity(0.7);
        root.getChildren().add(overlay);

        Label gameOverLabel = new Label("GAME OVER, " + playerName + "!");
        gameOverLabel.getStyleClass().add("game-over-label");

        Label restartInstruction = new Label("Press 'R' to Restart");
        restartInstruction.getStyleClass().add("game-over-instruction");

        Label menuInstruction = new Label("Press 'Q' to go back to Menu");
        menuInstruction.getStyleClass().add("game-over-instruction");

        Label quitGame = new Label("Press ESC to Quit the Game");
        quitGame.getStyleClass().add("game-over-instruction");

        Label meterLabel = new Label("Congrats,you have reached " + finalDistance + " Meter!");
        meterLabel.getStyleClass().add("meter-display");

        VBox layout = new VBox(30, gameOverLabel, meterLabel, restartInstruction, menuInstruction, quitGame);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));

        root.getChildren().add(layout);

        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case R -> restartGame();
                case Q -> switchScene(createMenu(playerName));
                case ESCAPE -> Platform.exit();
            }
        });
    }
    public Node createHelpMenu() {
        Label helpTitle = new Label("HOW TO PLAY STREET LEGENDS");
        helpTitle.getStyleClass().add("title");

        Label controlsInfo = new Label("🕹 Controls:\n" +
                "- Arrow Left: Steer the car to the left\n" +
                "- Arrow Right: Steer the car to the right");
        controlsInfo.getStyleClass().add("help-text");

        Label difficultyInfo = new Label("🎮 Difficulty Levels:\n" +
                "- Easy: Fewer cars, slower speed – perfect for practice!\n" +
                "- Medium: More cars, medium speed – for advanced players.\n" +
                "- Hard: Many cars, fast speed – only for real legends!");
        difficultyInfo.getStyleClass().add("help-text");

        Label tipsInfo = new Label("💡 Pro Tips:\n" +
                "- Stay focused and avoid obstacles.\n" +
                "- Collect power-ups to improve your car (if available).\n" +
                "- Can you beat Hard mode? Become a true Street Legend!");
        tipsInfo.getStyleClass().add("help-text");

        Label soundInfo = new Label("🔊 Sound Options:\n" +
                "- You can toggle menu and in-game sounds in the sound settings.");
        soundInfo.getStyleClass().add("help-text");

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("gradient-button");
        backButton.setOnAction(e -> switchScene(createMenu(playerName)));

        VBox helpLayout = new VBox(20, helpTitle, controlsInfo, difficultyInfo, tipsInfo, soundInfo, backButton);
        helpLayout.setAlignment(Pos.CENTER);
        helpLayout.setPadding(new Insets(20));
        helpLayout.getStyleClass().add("help-layout");

        helpLayout.setTranslateX(800);
        Timeline slideIn = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(helpLayout.translateXProperty(), 0))
        );
        slideIn.play();
        return helpLayout;
    }


    private void restartGame() {
        soundManager.stopCollisionSound();
        switchScene(createGame(playerName));
    }

    // Switches to a new scene by clearing the existing layout and adding the new layout.
    private void switchScene(Node newScene) {
        soundManager.stopCollisionSound();
        root.getChildren().clear();
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