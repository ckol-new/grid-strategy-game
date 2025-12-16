package game.gridstrategygame.Controller;

import game.gridstrategygame.Game;
import game.gridstrategygame.Model.Entity;
import game.gridstrategygame.Model.EntityMap;
import game.gridstrategygame.View.EffectType;
import game.gridstrategygame.View.GridView;

import java.util.ArrayList;

public class ActionsController {
    // attack: e1 attacks e2
    public static void attack(EffectType effectType, Entity e1, ArrayList<int[]> locations, Entity e2, EntityMap em, GameController gameController) {
        // null check
        if (e2 == null) {
            return;
        }

        // get entity positions
        int[] e1pos = e1.getPosition();
        int[] e2pos = e2.getPosition();

        // get e1 damage
        int damage = e1.getDamage();

        // deal damage, update health, render attack
        int e2health = e2.getHealth();
        int e2NewHealth = e2health - damage;
        e2.setHealth(e2NewHealth);
        // render attack
        locations.add(e2pos);
        gameController.drawEffects(effectType, locations);

        // check if e2 is alive.
        if (e2NewHealth <= 0) {
            em.killEntity(e2);
        }
    }

    // miss
    public static void miss(EffectType type, ArrayList<int[]> locations,GameController gc) {
        gc.drawEffects(type, locations);
    }

}
