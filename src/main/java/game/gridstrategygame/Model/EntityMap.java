package game.gridstrategygame.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EntityMap {
    // named constants
    static final int IMMPASSABLE_CONSTANT = -1;

    // field
    int localDifficulty;
    Entity[][] entityMatrix;
    ArrayList<Entity> activeEntitiesList = new ArrayList<>();
    int[] mapSize;

    // constructor
    public EntityMap(TerrainMap terrainMap) {
        // set field
        this.localDifficulty = localDifficulty;

        // get map size
        mapSize = findMapSize(terrainMap);

        // get entity matrix
        entityMatrix = convertToEntityMatrix(terrainMap);

        //DEBUG
        randPos();
        //DEBUG
        DEBUG_DISPLAY();
    }

    // get map size (From terrain map)
    private int[] findMapSize(TerrainMap tm) {
        return tm.getMapSize();
    }

    // convert to terrain map type
    // if not walkable -> IMPASSABLE ENTITY
    // if walkable -> null
    private Entity[][] convertToEntityMatrix(TerrainMap tm) {
        Tile[][] tMatrix = tm.getTerrainMatrix();
        Entity[][] eMatrix = new Entity[mapSize[0]][mapSize[1]];

        for (int y = 0; y < mapSize[0]; y++) {
            for (int x = 0; x < mapSize[1]; x++) {
                Tile tile = tMatrix[y][x];

                // check type (if not walkable -> IMPASSABLE)
                if (!tile.isWalkable()) eMatrix[y][x] = new IMPASSABLE();
                // else -> leave null
            }
        }

        return eMatrix;
    }

    // TEMP function, will be replaced
    // random entity position(
    private void randPos() {
        Random rand = new Random();
        int[] randPos = new int[2];
        boolean isGenerated = false;
        while(!isGenerated) {
            randPos = new int[]{rand.nextInt(0, mapSize[0]), rand.nextInt(0, mapSize[1])};
            Entity entityAtPos = getEntityAtPos(randPos);

            if (entityAtPos == null) {
                isGenerated = true;
                continue;
            }

            isGenerated = false;
        }

        placeKnight(randPos[0], randPos[1]);
    }

    // getters
    public boolean isInBound(int y, int x) {
        if ((y < 0 || y >= mapSize[0]) || (x < 0 || x >= mapSize[1])) return false;
        else return true;
    }
    public boolean isInBound(int[] pos) {
        if ((pos[0] < 0 || pos[0] >= mapSize[0]) || (pos[1] < 0 || pos[1] >= mapSize[1])) return false;
        else return true;
    }

    public Entity getEntityAtPos(int y, int x) {
        if (!isInBound(y, x)) throw new RuntimeException("OUT OF BOUNDS: " + y + " " + x);

        return entityMatrix[y][x];
    }
    public Entity getEntityAtPos(int[] pos) {
        if (!isInBound(pos)) throw new RuntimeException("OUT OF BOUNDS: " + Arrays.toString(pos));

        return entityMatrix[pos[0]][pos[1]];
    }
    public boolean isWalkable(int r, int c) {
        if (!isInBound(r, c)) throw new RuntimeException("OUT OF BOUNDS: " + r + " " + c);

        if (entityMatrix[r][c] == null) return true;
        return false;
    }
    public boolean isWalkable(int[] pos) {
        if (!isInBound(pos)) throw new RuntimeException("OUT OF BOUNDS: " + Arrays.toString(pos));

        if (entityMatrix[pos[0]][pos[1]] == null) return true;
        return false;
    }
    public int[] getMapSize() {
        return mapSize;
    }
    public boolean isEntityAlive(Entity entity) {
        for (Entity entityAlive : activeEntitiesList) {
            if (entityAlive.equals(entity)) return true;
        }

        return false;
    }

    // setters
    public void moveEntity(Entity entity, int[] location) {
        // check if alive
        if (!isEntityAlive(entity)) throw new RuntimeException("tried to move dead entity: " + entity.toString()); // not alive -> do nothing

        // check if walkable
        if (!isInBound(location)) throw new RuntimeException("tried to move entity: " + entity.toString() +" to an out of bound position: " + Arrays.toString(location));
        if (!isWalkable(location)) return; // not walkable do nothing

        // move entity
        entityMatrix[location[0]][location[1]] = entity;

        // clear old positions
        int[] oldPos = entity.getPosition();
        entityMatrix[oldPos[0]][oldPos[1]] = null;

        // update state
        entity.setPosition(location);
    }

    // DEBUG place knight enemy
    private void placeKnight(int r, int c) {
        Knight knight = new Knight(3, 2, MovementType.ORTHOGONAL, "knight.png", 1, Allegiance.ALLY);
        // add to matrix and active entity array
        entityMatrix[r][c] = knight;
        activeEntitiesList.add(knight);

        // update knight position variable
        knight.setPosition(new int[]{r, c});
    }

    //DEBUG display
    public void DEBUG_DISPLAY() {
        for (Entity[] row : entityMatrix) {
            for (Entity e : row) {
                if (e == null) System.out.print(". ");
                else if (e instanceof IMPASSABLE) System.out.print("X ");
                else if (e instanceof Knight) System.out.print("P ");
            }
            System.out.println();
        }
    }
}
