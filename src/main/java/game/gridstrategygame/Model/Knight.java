package game.gridstrategygame.Model;

public class Knight extends Entity{
    // fields
    Allegiance ALLEGIANCE = Allegiance.ALLY;

    public Knight(int health, int movementDistance, MovementType movementType, String textureName, int damage) {
        super(health, movementDistance, movementType, textureName, damage);
    }

    // getter
    public Allegiance getALLEGIANCE() { return ALLEGIANCE; }
}
