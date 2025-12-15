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
    Canvas effectCanvas;
    GraphicsContext gcTerrain;
    GraphicsContext gcEntity;
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
        effectCanvas = new Canvas();
        inputOverlay = new Pane();

        terrainCanvas.setHeight(HEIGHT);
        entityCanvas.setHeight(HEIGHT);
        effectCanvas.setHeight(HEIGHT);

        terrainCanvas.setWidth(WIDTH);
        entityCanvas.setWidth(WIDTH);
        effectCanvas.setWidth(WIDTH);

        inputOverlay.setPrefHeight(HEIGHT);
        inputOverlay.setPrefWidth(WIDTH);
        inputOverlay.minHeight(HEIGHT);
        inputOverlay.minWidth(WIDTH);

        StackPane.setAlignment(terrainCanvas, Pos.TOP_LEFT);
        StackPane.setAlignment(entityCanvas, Pos.TOP_LEFT);
        StackPane.setAlignment(effectCanvas, Pos.TOP_LEFT);
        StackPane.setAlignment(inputOverlay, Pos.TOP_LEFT);

        addOverlayHandlers();

        this.getChildren().addAll(terrainCanvas, entityCanvas, effectCanvas, inputOverlay);
    }

    // get graphics context
    private void setGraphicsContext() {
        gcTerrain = terrainCanvas.getGraphicsContext2D();
        gcEntity = entityCanvas.getGraphicsContext2D();
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

    public void setGameControllerInstance(GameController gameController) {
        gameControllerInstance = gameController;
    }

}
