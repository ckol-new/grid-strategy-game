module game.gridstrategygame {
    requires javafx.controls;
    requires javafx.fxml;


    opens game.gridstrategygame to javafx.fxml;
    exports game.gridstrategygame;
}