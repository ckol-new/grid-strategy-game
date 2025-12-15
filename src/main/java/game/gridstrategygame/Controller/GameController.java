package game.gridstrategygame.Controller;

import game.gridstrategygame.Game;
import game.gridstrategygame.Model.Entity;
import game.gridstrategygame.Model.EntityMap;
import game.gridstrategygame.Model.TerrainMap;
import game.gridstrategygame.View.GridView;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class GameController {
    // field
    Game gameInstance;
    AnchorPane root;
    GridView gridView = new GridView(800, 600);
    TerrainMap tm = new TerrainMap();
    EntityMap em = new EntityMap(tm);

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

    // set gridview
    private void setGridView() {
        root.getChildren().add(gridView);
        AnchorPane.setLeftAnchor(gridView, 0.0);
        AnchorPane.setRightAnchor(gridView, 0.0);
        AnchorPane.setTopAnchor(gridView, 20.0);
        AnchorPane.setBottomAnchor(gridView, 0.0);
    }

    // set event handlers
    public void setUIEventHandlers() {
        returnMenuItem.setOnAction(evt -> {gameInstance.setSceneMenu();});
        drawMenuItem.setOnAction(evt -> {
            gridView.drawTerrain(tm);
            gridView.drawEntities(em);
        });
    }

    // set game instance
    public void setGameInstance(Game game) {
        gameInstance = game;
    }
    public void setRoot(AnchorPane root) {
        this.root = root;
        setGridView();
    }

}
