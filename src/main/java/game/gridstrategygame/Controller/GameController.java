package game.gridstrategygame.Controller;

import game.gridstrategygame.Game;
import game.gridstrategygame.Model.Entity;
import game.gridstrategygame.Model.EntityMap;
import game.gridstrategygame.Model.TerrainMap;
import game.gridstrategygame.View.EffectType;
import game.gridstrategygame.View.GridView;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;

public class GameController {
    // field
    Game gameInstance;
    AnchorPane root;
    GridView gridView = new GridView(800, 580);
    InputController inputController;
    RunController runController;

    int[] tileSelected;

    // fxml field

    @FXML
    MenuItem returnMenuItem;
    @FXML
    MenuItem drawMenuItem;

    // constructor
    public GameController() {
        // get run controller
        runController = new RunController();
        inputController = new InputController(this, runController.getAllyRoster());
    }

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
            gridView.drawTerrain(runController.getTerrainMap());
            gridView.drawEntities(runController.getEntityMap());
        });
    }

    // main event handler
    public void handleSelection(int[] pos) {
        tileSelected = pos;
        //DEBUG
        System.out.println("selected: " + Arrays.toString(pos));
        Selection selection = new Selection(runController.getEntityMap().getEntityAtPos(pos), pos);

        // check selection
        boolean turnBoolean = inputController.checkSelection(selection, runController.getEntityMap());
        if (turnBoolean) {
            gridView.drawEntities(runController.getEntityMap());
        }
    }

    public void showValidTurns(EffectType type, ArrayList<int[]> validTurns) {
        gridView.drawValidTurns(type, validTurns);
    }
    public void clearValidTurns() { gridView.clearValidTurns(); }
    public void drawEffects(EffectType type, ArrayList<int[]> effectLocations) {
        gridView.drawEffect(type, effectLocations);
    }


    // set game instance
    public void setGameInstance(Game game) {
        gameInstance = game;
    }
    public void setRoot(AnchorPane root) {
        this.root = root;
        setGridView();
        gridView.setGameControllerInstance(this);

        gridView.drawTerrain(runController.getTerrainMap());
        gridView.drawEntities(runController.getEntityMap());
    }

}
