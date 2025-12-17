package game.gridstrategygame.View;

import game.gridstrategygame.Controller.GameController;
import game.gridstrategygame.Model.*;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;

public class GridView extends StackPane {
    // static fields
    Parent root;
    final int WIDTH;
    final int HEIGHT;

    final int TILE_WIDTH;
    final int TILE_HEIGHT;

    static final int GRID_SIZE_SQUARE = 10;

    GameController gameControllerInstance;

    // fields
    Canvas terrainCanvas;
    Canvas entityCanvas;
    Canvas validTurnCanvas;
    Canvas effectCanvas;
    GraphicsContext gcTerrain;
    GraphicsContext gcEntity;
    GraphicsContext gcValidTurn;
    GraphicsContext gcEffects;
    Pane inputOverlay;

    // constructor
    public GridView(int width, int height){
        WIDTH = width;
        HEIGHT = height;
        TILE_HEIGHT = (int) (HEIGHT / GRID_SIZE_SQUARE);
        TILE_WIDTH = (int) (WIDTH/ GRID_SIZE_SQUARE);

        setLayers();
        setGraphicsContext();
    }

    // set canvas
    private void setLayers() {
        terrainCanvas = new Canvas();
        entityCanvas = new Canvas();
        validTurnCanvas = new Canvas();
        effectCanvas = new Canvas();
        inputOverlay = new Pane();

        terrainCanvas.setHeight(HEIGHT);
        entityCanvas.setHeight(HEIGHT);
        validTurnCanvas.setHeight(HEIGHT);
        effectCanvas.setHeight(HEIGHT);

        terrainCanvas.setWidth(WIDTH);
        entityCanvas.setWidth(WIDTH);
        validTurnCanvas.setWidth(WIDTH);
        effectCanvas.setWidth(WIDTH);

        inputOverlay.setPrefHeight(HEIGHT);
        inputOverlay.setPrefWidth(WIDTH);
        inputOverlay.minHeight(HEIGHT);
        inputOverlay.minWidth(WIDTH);

        StackPane.setAlignment(terrainCanvas, Pos.TOP_LEFT);
        StackPane.setAlignment(entityCanvas, Pos.TOP_LEFT);
        StackPane.setAlignment(validTurnCanvas, Pos.TOP_LEFT);
        StackPane.setAlignment(effectCanvas, Pos.TOP_LEFT);
        StackPane.setAlignment(inputOverlay, Pos.TOP_LEFT);

        addOverlayHandlers();

        this.getChildren().addAll(terrainCanvas, entityCanvas, validTurnCanvas, effectCanvas, inputOverlay);
    }

    // get graphics context
    private void setGraphicsContext() {
        gcTerrain = terrainCanvas.getGraphicsContext2D();
        gcEntity = entityCanvas.getGraphicsContext2D();
        gcValidTurn = validTurnCanvas.getGraphicsContext2D();
        gcEffects = effectCanvas.getGraphicsContext2D();
    }

    // add event handlers
    private void addOverlayHandlers() {
        inputOverlay.setOnMouseClicked(evt -> {
            int gx = (int) (evt.getX() / TILE_WIDTH);
            int gy = (int) (evt.getY() / TILE_HEIGHT);


            gameControllerInstance.handleSelection(new int[]{gy, gx});
        });
    }


    // draw terrain map
    public void drawTerrain(TerrainMap terrainMap) {
        // clear first
        gcTerrain.clearRect(0, 0, WIDTH, HEIGHT);

        Tile[][] terrainMatrix = terrainMap.getTerrainMatrix();
        int[] mapSize = terrainMap.getMapSize();

        for (int y = 0; y < mapSize[0]; y++) {
            for (int x = 0; x < mapSize[1]; x++) {
                // get tile
                Tile tile = terrainMatrix[y][x];

                // get img
                Image tileImg = new Image(GridView.class.getResourceAsStream("/game/gridstrategygame/textures/" + tile.getTextureName()));

                // draw image
                gcTerrain.drawImage(tileImg, x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);

            }
        }
    }

    // draw entity map
    public void drawEntities(EntityMap entityMap) {
        // clear first
        gcEntity.clearRect(0, 0, WIDTH, HEIGHT);

        int[] mapSize = entityMap.getMapSize();

        for (int y = 0; y < mapSize[0]; y++) {
            for (int x = 0; x < mapSize[1]; x++) {
                Entity entity = entityMap.getEntityAtPos(y, x);

                // null check and IMPASSABLE
                if ((entity == null) || (entity instanceof IMPASSABLE)) {
                    continue;
                }

                // get image
                Image entityTexture = new Image(GridView.class.getResourceAsStream("/game/gridstrategygame/textures/" + entity.getTextureName()));

                // draw image
                gcEntity.drawImage(entityTexture, x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
            }
        }
    }

    public void drawValidTurns(EffectType type, ArrayList<int[]> locations) {
        // clear effect canvas
        // gcValidTurn.clearRect(0,0, 2000, 2000);

        for (int[] location : locations) {
            Image effectTexture = new Image(GridView.class.getResourceAsStream("/game/gridstrategygame/textures/" + type.textureName));
            gcValidTurn.drawImage(effectTexture, location[1] * TILE_WIDTH, location[0] * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        }
    }
    public void clearValidTurns(ArrayList<int[]> locations) {
        for (int[] location : locations) {
            gcValidTurn.clearRect(location[1] * TILE_WIDTH,location[0] * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        }

    }

    public void drawEffect(EffectType type, ArrayList<int[]> locations) {
        // clear effect canvas
        // gcEffects.clearRect(0,0, 2000, 2000);

        for (int[] location : locations) {
            Image effectTexture = new Image(GridView.class.getResourceAsStream("/game/gridstrategygame/textures/" + type.textureName));
            gcEffects.drawImage(effectTexture, location[1] * TILE_WIDTH, location[0] * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        }

        PauseTransition p = new PauseTransition(Duration.millis(200));
        p.setOnFinished(e -> clearEffects());
        p.play();
    }
    public void clearEffects() {
        gcEffects.clearRect(0,0, 2000, 2000);
    }

    public void setGameControllerInstance(GameController gameController) {
        gameControllerInstance = gameController;
    }

}
