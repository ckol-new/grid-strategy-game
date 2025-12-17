module game.gridstrategygame {
    requires javafx.controls;
    requires javafx.fxml;

    opens game.gridstrategygame to javafx.fxml;
    exports game.gridstrategygame;

    opens game.gridstrategygame.Controller to javafx.fxml;
    exports game.gridstrategygame.Controller;

    opens game.gridstrategygame.Model to javafx.fxml;
    exports game.gridstrategygame.Model;
}