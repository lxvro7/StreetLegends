package com.game;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class GameUIManager {
    private final StackPane root;
    private final UserInterface userInterface;
    private final SoundManager soundManager;
    private Slider volumeSlider;

    public GameUIManager(UserInterface userInterface, StackPane root, SoundManager soundManager) {
        this.userInterface = userInterface;
        this.root = root;
        this.soundManager = soundManager;
        createVolumeSlider();
    }

    public void startCountdown(Runnable onCountdownFinished) {
        soundManager.stopGameSound();
        soundManager.playCountdownSound();

        Label countdownLabel = new Label();
        countdownLabel.getStyleClass().add("countdown-label");

        Platform.runLater(() -> {
            root.getChildren().add(countdownLabel);
            StackPane.setAlignment(countdownLabel, Pos.CENTER);
        });

        int[] countdownValues = {3, 2, 1};
        Timeline countdownTimeline = new Timeline();

        double durationPerNumber = 1.4;

        for (int i = 0; i < countdownValues.length; i++) {
            int count = countdownValues[i];
            countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i * durationPerNumber), event -> {
                Platform.runLater(() -> {
                    countdownLabel.setText(String.valueOf(count));
                    countdownLabel.setScaleX(1.0);
                    countdownLabel.setScaleY(1.0);
                    countdownLabel.setOpacity(1.0);

                    FadeTransition fade = new FadeTransition(Duration.seconds(1), countdownLabel);
                    fade.setFromValue(1.0);
                    fade.setToValue(0.0);
                    fade.play();
                });
            }));
        }

        // GO! nach dem letzten Countdown-Wert
        countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(countdownValues.length * durationPerNumber), event -> {
            Platform.runLater(() -> {
                countdownLabel.setText("GO!");
                countdownLabel.getStyleClass().remove("countdown-label");
                countdownLabel.getStyleClass().add("go-label");

                FadeTransition fade = new FadeTransition(Duration.seconds(1), countdownLabel);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.play();
            });
        }));

        countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(countdownValues.length * durationPerNumber + 1), event -> {
            Platform.runLater(() -> {
                root.getChildren().remove(countdownLabel);

                soundManager.stopCountdownSound();
                soundManager.startGameSound();
                onCountdownFinished.run();
            });
        }));

        countdownTimeline.play();
    }

    public void showGameOverWindow(int finalDistance, Runnable onRestart, Runnable onBackToMenu) {
        soundManager.stopGameSound();
        soundManager.playCollisionSound();

        Rectangle overlay = new Rectangle();
        overlay.widthProperty().bind(root.widthProperty());
        overlay.heightProperty().bind(root.heightProperty());
        overlay.setFill(Color.GRAY);
        overlay.setOpacity(0.7);
        root.getChildren().add(overlay);

        Label gameOverLabel = new Label("GAME OVER, " + userInterface.getPlayerName() + "!");
        gameOverLabel.getStyleClass().add("game-over-label");

        Label restartInstruction = new Label("Press 'R' to Restart");
        restartInstruction.getStyleClass().add("game-over-instruction");

        Label menuInstruction = new Label("Press 'Q' to go back to Menu");
        menuInstruction.getStyleClass().add("game-over-instruction");

        Label quitGame = new Label("Press ESC to Quit the Game");
        quitGame.getStyleClass().add("game-over-instruction");
        int coinsCollected = userInterface.getCoinCounter();

        Label coinLabel = new Label("You collected " + coinsCollected + " Coins! Therfore you were " + 200*coinsCollected + " meter faster!");
        coinLabel.getStyleClass().add("meter-display");
        coinLabel.setTextAlignment(TextAlignment.CENTER);

        Label meterLabel = new Label("You covered " + finalDistance + " meters!");
        meterLabel.getStyleClass().add("meter-display");
        meterLabel.setTextAlignment(TextAlignment.CENTER);

        VBox layout = new VBox(30, gameOverLabel, coinLabel, meterLabel, restartInstruction, menuInstruction, quitGame);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));

        root.getChildren().add(layout);
        userInterface.isGameOver = true;

        root.getScene().setOnKeyPressed(keyEvent -> {
            if (userInterface.isGameOver) {
                switch (keyEvent.getCode()) {
                    case R -> onRestart.run();
                    case Q -> onBackToMenu.run();
                    case ESCAPE -> Platform.exit();
                }
            }
        });
    }

    private void createVolumeSlider() {
        volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setMajorTickUnit(20);
        volumeSlider.setBlockIncrement(10);
        volumeSlider.setPrefWidth(200);

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100.0;
            soundManager.setVolume(volume);
        });
    }
}