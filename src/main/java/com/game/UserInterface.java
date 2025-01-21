package com.game;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    private MainMenu mainMenu;
    private DifficultyMenu difficultyMenu;
    private SoundSettingMenu soundSettingsMenu;
    private EntryScreen entryScreen;
    private GameUIManager gameUIManager;
    private HelpMenu helpMenu;
    private GameEngine gameEngine;
    private GraphicsContext vehicleGraphicsContext;
    private GraphicsContext streetGraphicsContext;
    public boolean isGameOver = false;
    private final SoundManager soundManager = new SoundManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        soundManager.startMenuSound();
        root = new StackPane();
        background();

        mainMenu = new MainMenu(this, soundManager);
        difficultyMenu = new DifficultyMenu(this, soundManager);
        soundSettingsMenu = new SoundSettingMenu(this, soundManager);
        entryScreen = new EntryScreen(this);
        gameUIManager = new GameUIManager(this, root, soundManager);
        helpMenu = new HelpMenu(this, mainMenu);

        this.gameEngine = new GameEngine(playerName, difficulty, this,
                Screen.getPrimary().getBounds().getHeight(),
                Screen.getPrimary().getBounds().getWidth()
        );
        System.out.println("GameEngine initialized with difficulty: " + difficulty);


        Node entryLayout = entryScreen.createScreen();
        root.getChildren().add(entryLayout);
        scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        readFromCss(scene);
        primaryStage.setTitle("Street Legends");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
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

    // Applies a fade-in transition effect to make the specified node gradually appear on the screen.
    public void switchSceneWithFade(Node newScene) {
        soundManager.stopCollisionSound();

        Rectangle overlay = new Rectangle();
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());
        overlay.setFill(Color.BLACK);
        overlay.setOpacity(0.0);

        root.getChildren().add(overlay);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.4), overlay);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(0.8);
        fadeOut.setOnFinished(event -> {
            root.getChildren().clear();
            root.getChildren().add(newScene);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.4), overlay);
            fadeIn.setFromValue(0.6);
            fadeIn.setToValue(0.0);
            fadeIn.setOnFinished(e -> root.getChildren().remove(overlay));
            fadeIn.play();
        });
        fadeOut.play();
    }
    public Canvas createStreetCanvas()  {

        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        return new Canvas(screenWidth, screenHeight);
    }
    public Node createGame(String playerName) {
        soundManager.stopMenuSound();
        soundManager.startGameSound();

        Canvas streetCanvas = createStreetCanvas();
        Canvas vehicleCanvas = new Canvas(streetCanvas.getWidth(), streetCanvas.getHeight());

        vehicleGraphicsContext = vehicleCanvas.getGraphicsContext2D();
        streetGraphicsContext = streetCanvas.getGraphicsContext2D();

        Pane canvasContainer = new Pane();
        canvasContainer.getChildren().addAll(streetCanvas, vehicleCanvas);

        Label meterLabel = new Label("Meter: 0");
        meterLabel.getStyleClass().add("meter-label");
        meterLabel.setLayoutX(20);
        meterLabel.setLayoutY(20);
        meterLabel.setPadding(new Insets(10));

        canvasContainer.getChildren().add(meterLabel);

        this.gameEngine = new GameEngine(playerName, difficulty, this, streetCanvas.getHeight(), streetCanvas.getWidth());
        CanvasContext.initialize(streetCanvas.getWidth(), streetCanvas.getHeight());

        gameEngine.setCanvasHeight(streetCanvas.getHeight());
        gameEngine.setCanvasWidth(streetCanvas.getWidth());
        gameEngine.renderBackground(streetGraphicsContext);
        gameEngine.setUpdateCallback(gameObjects -> gameEngine.renderObjects(vehicleGraphicsContext, gameObjects));

        root.getChildren().clear();
        root.getChildren().add(canvasContainer);
        gameUIManager.startCountdown(() -> gameEngine.startGameLoop());
        AnimationTimer meterUpdater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int meters = gameEngine.getDistanceTraveled();
                meterLabel.setText("Meter: " + meters);
            }
        };
        meterUpdater.start();

        scene.setOnKeyPressed(keyEvent -> {
            gameEngine.processUserInput(keyEvent.getCode());
        });

        scene.setOnKeyReleased(keyEvent -> {
            gameEngine.processKeyRelease(keyEvent.getCode());
        });

        return canvasContainer;
    }
    public void restartGame() {
        soundManager.stopCollisionSound();
        switchScene(createGame(playerName));
    }

    // Switches to a new scene by clearing the existing layout and adding the new layout.
    public void switchScene(Node newScene) {
        isGameOver=false;
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
    public StackPane getRoot() {
        return root;
    }

    public String getPlayerName() {
        return playerName;
    }
    public DifficultyMenu getDifficultyMenu() {
        return difficultyMenu;
    }
    public MainMenu getMainMenu() {
        return mainMenu;
    }
    public SoundSettingMenu getSoundSettingsMenu() {
        return soundSettingsMenu;
    }

    public void setPlayerName(String playerName) {
        this.playerName=playerName;
    }
    public EntryScreen getEntryScreen(){
        return entryScreen;
    }
    public GameEngine getGameEngine(){
        return gameEngine;
    }

    public GameUIManager getGameUIManager() {
        return gameUIManager;
    }
    public HelpMenu getHelpMenu() {
        return helpMenu;
    }
    public GraphicsContext getBackgroundGraphicsContext() {
        return streetGraphicsContext;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty.toLowerCase();
    }
    public GraphicsContext getVehicleGraphicsContext() {
        return vehicleGraphicsContext;
    }
    public Node getScene() {
        return null;
    }
}