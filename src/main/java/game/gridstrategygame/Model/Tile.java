package game.gridstrategygame.Model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum Tile {
    STONE_FLOOR(true, 2, "stone_floor.png", "."),
    STONE_WALL(false, 0, "stone_wall.png", "w");

    // fields
    final boolean IS_WALKABLE;
    final int MOVEMENT_TYPE;
    static final int NORMAL_MOVEMENT = 2;
    static final int SLOW_MOVEMENT = 1;
    static final int NO_MOVEMENT = 0;
    String textureName;
    final String SYMBOL;

    private Tile(boolean isWalkable, int movementType, String textureName, String symbol) {
        IS_WALKABLE = isWalkable;
        MOVEMENT_TYPE = movementType;
        this.textureName = textureName;
        SYMBOL = symbol;
    }




    // getters
    public String getTextureName() { return textureName; }

    public int getMovementType() { return MOVEMENT_TYPE; }
    public boolean isWalkable() { return IS_WALKABLE; }
    public String getSymbol() { return SYMBOL; }
}
