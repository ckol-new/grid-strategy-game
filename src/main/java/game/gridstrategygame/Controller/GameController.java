package game.gridstrategygame.Controller;

import game.gridstrategygame.Game;
import game.gridstrategygame.Model.EntityMap;
import game.gridstrategygame.Model.TerrainMap;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class GameController {
    // field
    Game gameInstance;

    // fxml field
    @FXML
    MenuItem returnMenuItem;
    @FXML
    MenuItem drawMenuItem;

    // constructor
    public GameController() {}

    // initialize
    @FXML
    public void initialize() {
        setUIEventHandlers();
    }

    // set event handlers
    public void setUIEventHandlers() {
        returnMenuItem.setOnAction(evt -> {gameInstance.setSceneMenu();});
    }

    // set game instance
    public void setGameInstance(Game game) {
        gameInstance = game;
    }

}
