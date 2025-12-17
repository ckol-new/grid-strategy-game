package game.gridstrategygame.Model;

public class EntityDataUtil {
    // knight data
    public static final int KNIGHT_HEALTH = 3;
    public static final int KNIGHT_MOVEMENT_DISTANCE = 2;
    public static final int KNIGHT_DAMAGE = 1;
    public static final int KNIGHT_ATTACK_DISTANCE = 2;
    public static final MovementType KNIGHT_MOVEMENT_TYPE = MovementType.ORTHOGONAL;
    public static final AttackType KNIGHT_ATTACK_TYPE = AttackType.ORTHOGONAL;
    public static final String KNIGHT_TEXTURE_NAME = "knight.png";
    public static final Allegiance KNIGHT_ALLEGIANCE = Allegiance.ALLY;

    // skeleton data
    public static final int SKELETON_HEALTH = 3;
    public static final int SKELETON_MOVEMENT_DISTANCE = 2;
    public static final int SKELETON_DAMAGE = 1;
    public static final int SKELETON_ATTACK_DISTANCE = 1;
    public static final MovementType SKELETON_MOVEMENT_TYPE = MovementType.ORTHOGONAL;
    public static final AttackType SKELETON_ATTACK_TYPE = AttackType.ORTHOGONAL;
    public static final String SKELETON_TEXTURE_NAME = "skeleton.png";
    public static final Allegiance SKELETON_ALLEGIANCE = Allegiance.FOE;


}
