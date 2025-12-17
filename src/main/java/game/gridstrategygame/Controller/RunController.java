package game.gridstrategygame.Controller;

import game.gridstrategygame.Model.*;

import java.util.ArrayList;

public class RunController {
    private LevelController levelController;
    private static final int NUM_START_ALLY = 2;
    private static final int START_DIFFICULTY = 1;
    private int localDifficulty = START_DIFFICULTY;
    private ArrayList<Entity> allyRoster;

    // constructor
    public RunController() {
        // get initial ally roster
        allyRoster = generatingStartingAllyRoster(NUM_START_ALLY);

        // generate next level
        generateNextLevel();
    }

    // generate starting roster
    // Todo for now just give player two knights, will be randomized in the future
    private ArrayList<Entity> generatingStartingAllyRoster(int numAlly) {
        ArrayList<Entity> roster = new ArrayList<>();

        for (int i = 0; i < numAlly; i++) {
            // get entity
            Knight k = new Knight(
                    EntityDataUtil.KNIGHT_HEALTH,
                    EntityDataUtil.KNIGHT_MOVEMENT_DISTANCE,
                    EntityDataUtil.KNIGHT_MOVEMENT_TYPE,
                    EntityDataUtil.KNIGHT_ATTACK_DISTANCE,
                    EntityDataUtil.KNIGHT_ATTACK_TYPE,
                    EntityDataUtil.KNIGHT_TEXTURE_NAME,
                    EntityDataUtil.KNIGHT_DAMAGE,
                    EntityDataUtil.KNIGHT_ALLEGIANCE
            );

            // place in roster
            roster.add(k);
        }

        return roster;
    }

    // DEBUG (for now just create level, do not generate classic rogue like graph)
    // create new level: local difficulty, reward on completion, etc
    public void generateNextLevel() {
        levelController = null; // set for garbage collection
        levelController = new LevelController(allyRoster, "map1.txt", localDifficulty);
        localDifficulty = localDifficulty + 1;
    }

    // cull roster (if dead  -> remove)
    public void cullAllyRoster() {
        ArrayList<Entity> toBeRemoved = new ArrayList<>();

        // check if dead
        for (Entity e : allyRoster) {
            if (e.getHealth() <= 0) {
                toBeRemoved.add(e);
            }
        }

        // remove all dead
        for (Entity e : toBeRemoved) {
            allyRoster.remove(e);
        }
    }

    // create section level branch

    // intermediaries
    public EntityMap getEntityMap() { return levelController.getEntityMap(); }
    public TerrainMap getTerrainMap() { return levelController.getTerrainMap(); }
    public ArrayList<Entity> getAllyRoster() { return levelController.getAllyRoster(); }
    public boolean isLevelOver() { return levelController.isLevelOver(); }
}
