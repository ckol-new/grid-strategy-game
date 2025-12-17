package game.gridstrategygame.Controller;

import game.gridstrategygame.Model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class LevelController {
    // field
    TerrainMap tm;
    EntityMap em;
    ArrayList<Entity> allyRoster;
    ArrayList<Entity> enemyRoster;


    // constructor
    public LevelController(ArrayList<Entity> allyRoster, String mapFile, int localDifficulty) {
        this.allyRoster = allyRoster;

        // generate terrain map
        tm = new TerrainMap(mapFile);

        // generate entity map
        em = new EntityMap(tm, localDifficulty);

        // generate enemy roster
        enemyRoster = generateEnemyRoster(localDifficulty);

        // place enemies
        placeEnemies();

        // place players
        placePlayers();

    }

    // generate enemy roster
    private ArrayList<Entity> generateEnemyRoster(int localDifficulty) {
        ArrayList<Entity> roster = new ArrayList<>();
        int points = localDifficulty;

        // types of enemies
        EnemyType[] entityTypes = {EnemyType.SKELETON};

        Random rand = new Random();
        while (points > 0) {
            // get random enemy type
            EnemyType type = entityTypes[rand.nextInt(0, entityTypes.length)];
            points =  points - type.cost;

            switch (type) {
                case SKELETON:
                    Skeleton entity = new Skeleton(
                            EntityDataUtil.SKELETON_HEALTH,
                            EntityDataUtil.SKELETON_MOVEMENT_DISTANCE,
                            EntityDataUtil.SKELETON_MOVEMENT_TYPE,
                            EntityDataUtil.SKELETON_ATTACK_DISTANCE,
                            EntityDataUtil.SKELETON_ATTACK_TYPE,
                            EntityDataUtil.SKELETON_TEXTURE_NAME,
                            EntityDataUtil.SKELETON_DAMAGE,
                            EntityDataUtil.SKELETON_ALLEGIANCE
                    );
                    // add to roster
                    roster.add(entity);
                    break;
            }
        }

        return roster;
    }

    // place enemies
    private void placeEnemies() {
        for (Entity e : enemyRoster) {
            em.placeEntities(e);
        }
    }
    // place players
    private void placePlayers() {
        for (Entity e : allyRoster) {
            em.placeEntities(e);
        }
    }


    // getters
    public EntityMap getEntityMap() { return em; }
    public TerrainMap getTerrainMap() { return tm; }
}
