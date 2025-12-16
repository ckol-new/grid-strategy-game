package game.gridstrategygame.Controller;

import game.gridstrategygame.Model.Entity;

public class Selection {
    public int[] pos;
    public Entity entity;

    public Selection(Entity entity, int[] pos) {
        this.entity = entity;
        this.pos = pos;
    }
}
