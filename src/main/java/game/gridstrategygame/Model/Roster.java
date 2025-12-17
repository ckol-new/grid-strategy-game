package game.gridstrategygame.Model;

/*
Set of entities (on one team)
 */

import java.util.ArrayList;
import java.util.Collections;

public class Roster {
    private ArrayList<Entity> roster;

    public Roster() {
        roster = new ArrayList<>();
    }

    // add to roster
    public void add(Entity... entities) {
        Collections.addAll(roster, entities);
    }

    // remove from roster
    public void remove(Entity... entities) {
        for (Entity e : entities) {
            roster.remove(e);
        }
    }

    // get from roster
    public Entity get(int index) {
        return roster.get(index);
    }

    // get size
    public int getSize() {
        return roster.size();
    }



}
