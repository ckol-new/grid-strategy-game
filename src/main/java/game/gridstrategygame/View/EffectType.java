package game.gridstrategygame.View;

public enum EffectType {
    VALID_MOVE("valid_move.png"),
    VALID_ATTACK("valid_attack.png"),
    WHITE_SLASH_ATTACK("white_slash_attack.png");

    String textureName;
    EffectType(String textureName) {
        this.textureName = textureName;
    }
}
