package game.gridstrategygame.Model;

public class EntityDataUtil {
    // knight data
    static final int KNIGHT_HEALTH = 3;
    static final int KNIGHT_MOVEMENT_DISTANCE = 2;
    static final int KNIGHT_DAMAGE = 1;
    static final int KNIGHT_ATTACK_DISTANCE = 2;
    static final MovementType KNIGHT_MOVEMENT_TYPE = MovementType.ORTHOGONAL;
    static final AttackType KNIGHT_ATTACK_TYPE = AttackType.ORTHOGONAL;
    static final String KNIGHT_TEXTURE_NAME = "knight.png";
    static final Allegiance KNIGHT_ALLEGIANCE = Allegiance.ALLY;

    // skeleton data
    static final int SKELETON_HEALTH = 3;
    static final int SKELETON_MOVEMENT_DISTANCE = 2;
    static final int SKELETON_DAMAGE = 1;
    static final int SKELETON_ATTACK_DISTANCE = 1;
    static final MovementType SKELETON_MOVEMENT_TYPE = MovementType.ORTHOGONAL;
    static final AttackType SKELETON_ATTACK_TYPE = AttackType.ORTHOGONAL;
    static final String SKELETON_TEXTURE_NAME = "skeleton.png";
    static final Allegiance SKELETON_ALLEGIANCE = Allegiance.FOE;


}
