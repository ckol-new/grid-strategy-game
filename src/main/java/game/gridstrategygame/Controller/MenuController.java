package game.gridstrategygame.Controller;

import game.gridstrategygame.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MenuController {
    // field
    Game gameInstance;

    // fxml fields
    @FXML
    BorderPane root;
    @FXML
    Label title;
    @FXML
    Button startButton;
    @FXML
    Button optionsButton;
    @FXML
    Button quitButton;

    // cosntructor
    public MenuController() {}

    // intialize
    @FXML
    public void initialize() {
        setUIEventHandlers();
    }

    // set event handlers
    public void setUIEventHandlers() {
        startButton.setOnAction(evt -> gameInstance.setSceneGame());
        quitButton.setOnAction(evt -> gameInstance.closeGame());
    }

    // set game isntance
    public void setGameInstance(Game game) { gameInstance = game; }
}
