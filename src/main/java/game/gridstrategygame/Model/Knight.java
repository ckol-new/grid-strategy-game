package game.gridstrategygame.Model;

public class Knight extends Entity{
    // fields

    public Knight(int health, int movementDistance, MovementType movementType, String textureName, int damage, Allegiance allegiance) {
        super(health, movementDistance, movementType, textureName, damage, allegiance);
    }

    @Override
    public void updateTurnState() {
        if (this.getTurnState() == TurnState.MOVE) this.setTurnState(TurnState.ATTACK);
        else if (this.getTurnState() == TurnState.ATTACK) this.setTurnState(TurnState.MOVE);
    }

}
