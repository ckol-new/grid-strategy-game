package game.gridstrategygame.Model;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TerrainMap {
    // fields
    Tile[][] terrainMatrix;
    int[] mapSize;

    // constructor
    public TerrainMap(String mapName) {
        // generate map
        terrainMatrix = convertToTerrainMatrix(mapName);

        // DEBUG
        // DEBUG_DISPLAY();
    }

    // convert map file to terrain matrix
    // set class field of mapSize to its size
    private Tile[][] convertToTerrainMatrix(String mapName) {
        Path mapPath;
        List<String> mapList;

        String resourcePath = "/game/gridstrategygame/maps/" + mapName;

        try (InputStream is = TerrainMap.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalAccessException("Map resource not found: " + resourcePath);
            }
            mapList = new BufferedReader(new InputStreamReader(is)).lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // first line of map list is size
        findMapSize(mapList);

        Tile[][] matrix = new Tile[mapSize[0]][mapSize[1]];

        // for each line -> convert to proper tile
        //  (skip first line)
        for (int i = 1; i <= mapSize[0]; i++) {
            String line = mapList.get(i);

            // split into string array
            String[] strArr = line.strip().split("");

            for (int j = 0; j < mapSize[1]; j++) {
                String c = strArr[j];

                // switch statement for each type of tile
                switch (c) {
                    case ".":
                        matrix[i - 1][j] = Tile.STONE_FLOOR;
                        break;
                    case "w":
                        matrix[i - 1][j] = Tile.STONE_WALL;
                        break;
                    default:
                        throw new RuntimeException("ERROR IN MAP CONFIG, NOT CORRECT CHARACTER AT: " + mapName + " lines: " + i + " " + j);
                }
            }
        }

        return matrix;
    }

    // get map size (first line of mapList)
    private void findMapSize(List<String> mapList) {
        String size = mapList.getFirst();
        String[] strSize = size.strip().split(" ");
        int[] intSize = new int[]{Integer.parseInt(strSize[0]), Integer.parseInt(strSize[1])};
        mapSize = intSize;
    }

    // getters
    public Tile[][] getTerrainMatrix() {
        return terrainMatrix;
    }
    public int[] getMapSize() { return mapSize; }

    //DEBUG display
    public void DEBUG_DISPLAY() {
        for (int i = 0; i < mapSize[0]; i++) {
            for (int j = 0; j < mapSize[1]; j++) {
                System.out.print(terrainMatrix[i][j].getSymbol() + " ");
            }
            System.out.println();
        }
    }
}