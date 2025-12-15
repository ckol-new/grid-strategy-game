package game.gridstrategygame.Model;

public abstract class Entity {
    // field
    int health;
    int movementDistance;
    int damage;
    MovementType movementType;
    TurnState turnState = TurnState.IDLE; // basic
    String textureName;

    public Entity(int health, int movementDistance, MovementType movementType, String textureName) {
        this.health = health;
        this.movementDistance = movementDistance;
        this.movementType = movementType;
        this.textureName = textureName;
        damage = 0; // if not declared
    }
    public Entity(int health, int movementDistance, MovementType movementType, String textureName, int damage) {
        this.health = health;
        this.movementDistance = movementDistance;
        this.movementType = movementType;
        this.textureName = textureName;
        this.damage = damage;
    }
    // for impassable
    public Entity() {

    }

    // getters
    public int getHealth() { return health; }
    public int getMovementDistance() { return movementDistance; }
    public int getDamage() { return damage; }
    public MovementType getMovementType() { return movementType; }
    public TurnState getTurnState() { return turnState; }
    public String getTextureName() { return textureName; }

    // setters
    public void setHealth(int health) { this.health = health; }
    public void setMovementDistance(int movementDistance) { this.movementDistance= movementDistance; }
    public void setDamage(int damage) { this.damage = damage; }
    public void setMovementType(MovementType movementType) { this.movementType = movementType; }
    public void setTurnState(TurnState turnState) { this.turnState= turnState; }


}
