package com.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HelpMenu {
    private final UserInterface userInterface;
    private final MainMenu mainMenu;

    public HelpMenu(UserInterface userInterface, MainMenu mainMenu) {
        this.userInterface = userInterface;
        this.mainMenu = mainMenu;
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
                "- Can you beat Hard mode? Become a true Street Legend!");
        tipsInfo.getStyleClass().add("help-text");

        Label soundInfo = new Label("🔊 Sound Options:\n" +
                "- You can toggle menu and in-game sounds in the sound settings.");
        soundInfo.getStyleClass().add("help-text");

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("gradient-button");
        backButton.setOnAction(e -> userInterface.switchSceneWithFade(mainMenu.createMenu(userInterface.getPlayerName())));

        VBox helpLayout = new VBox(20, helpTitle, controlsInfo, difficultyInfo, tipsInfo, soundInfo, backButton);
        helpLayout.setAlignment(Pos.CENTER);
        helpLayout.setPadding(new Insets(20));
        helpLayout.getStyleClass().add("help-layout");

        return helpLayout;
    }
}