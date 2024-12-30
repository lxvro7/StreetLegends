module com.game.street_legends {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.game.street_legends to javafx.fxml;
    exports com.game.street_legends;
}