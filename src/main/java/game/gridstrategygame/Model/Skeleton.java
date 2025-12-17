package game.gridstrategygame.Model;

public class Skeleton extends Entity{

    public Skeleton(int health, int movementDistance, MovementType movementType, int attackDistance,AttackType attackType, String textureName, int damage, Allegiance allegiance) {
        super(health, movementDistance, movementType, attackDistance, attackType, textureName, damage, allegiance);
    }

    @Override
    public void updateTurnState() {
        if (this.getTurnState() == TurnState.MOVE) this.setTurnState(TurnState.ATTACK);
        if (this.getTurnState() == TurnState.ATTACK) this.setTurnState(TurnState.TURN_COOLDOWN);
        if (this.getTurnState() == TurnState.TURN_COOLDOWN) this.setTurnState(TurnState.MOVE);
    }
}
