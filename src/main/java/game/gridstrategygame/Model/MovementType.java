package game.gridstrategygame.Model;

public enum MovementType {
    ORTHOGONAL("orthogonal"),
    DIAGNOAL("diagonal"),
    IMMOBILE("immobile");

    final String type;
    MovementType(String type) {
        this.type = type;
    }
}
