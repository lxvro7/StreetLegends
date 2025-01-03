module com.game.street_legends {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens com.game to javafx.fxml;
    exports com.game;
}