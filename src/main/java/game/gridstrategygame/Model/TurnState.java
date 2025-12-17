package game.gridstrategygame.Model;

public enum TurnState {
    MOVE("move"),
    MOVE_SLOWED("move_slowed"),
    SKIPPED("skipped"),
    ATTACK("attacked"),
    IDLE("idle"),
    TURN_COOLDOWN("cooldown");

    final String state;
    TurnState(String state) {
        this.state = state;
    }
}
