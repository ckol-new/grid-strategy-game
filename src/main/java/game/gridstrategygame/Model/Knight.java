package game.gridstrategygame.Model;

public class Knight extends Entity{
    // fields

    public Knight(int health, int movementDistance, MovementType movementType, int attackDistance, AttackType attackType, String textureName, int damage, Allegiance allegiance) {
        super(health, movementDistance, movementType, attackDistance, attackType, textureName, damage, allegiance);
    }

    @Override
    public void updateTurnState() {
        if (this.getTurnState() == TurnState.MOVE) this.setTurnState(TurnState.ATTACK);
        else if (this.getTurnState() == TurnState.ATTACK) this.setTurnState(TurnState.TURN_COOLDOWN);
        else if (this.getTurnState() == TurnState.TURN_COOLDOWN) this.setTurnState(TurnState.MOVE);
    }

}
