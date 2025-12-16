package game.gridstrategygame.Model;

public abstract class Entity {
    // field
    private int health;
    private int movementDistance;
    private int damage;
    private MovementType movementType;
    private TurnState turnState = TurnState.MOVE; // basic
    private String textureName;
    private Allegiance allegiance;
    private int[] position;

    public Entity(int health, int movementDistance, MovementType movementType, String textureName) {
        this.health = health;
        this.movementDistance = movementDistance;
        this.movementType = movementType;
        this.textureName = textureName;
        damage = 0; // if not declared
    }
    public Entity(int health, int movementDistance, MovementType movementType, String textureName, int damage, Allegiance allegiance) {
        this.health = health;
        this.movementDistance = movementDistance;
        this.movementType = movementType;
        this.textureName = textureName;
        this.damage = damage;
        this.allegiance = allegiance;
    }
    // for impassable
    public Entity() {

    }

    // override in sub classes
    public abstract void updateTurnState();

    // getters
    public int getHealth() { return health; }
    public int getMovementDistance() { return movementDistance; }
    public int getDamage() { return damage; }
    public MovementType getMovementType() { return movementType; }
    public TurnState getTurnState() { return turnState; }
    public String getTextureName() { return textureName; }
    public Allegiance getAllegiance() { return allegiance; }
    public int[] getPosition() { return position; }

    // setters
    public void setHealth(int health) { this.health = health; }
    public void setMovementDistance(int movementDistance) { this.movementDistance= movementDistance; }
    public void setDamage(int damage) { this.damage = damage; }
    public void setMovementType(MovementType movementType) { this.movementType = movementType; }
    public void setTurnState(TurnState turnState) { this.turnState= turnState; }
    public void setAllegiance(Allegiance allegiance) { this.allegiance = allegiance; }
    public void setPosition(int[] position) { this.position = position; }


}
