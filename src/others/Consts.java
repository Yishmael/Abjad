package others;

import components.AttackComponent;
import components.DefendComponent;
import components.HealthComponent;
import components.InputComponent;
import components.InventoryComponent;
import components.ManaComponent;
import components.MovementComponent;
import components.SpriteComponent;
import components.TransformComponent;

public final class Consts {

    public static final int SCREEN_WIDTH = 640; // 16 : 9
    public static final int SCREEN_HEIGHT = 512;
   
    public static final int TILE_HEIGHT = 64;

    // TODO make them enums
    public static final int ATTACK = AttackComponent.bit;
    public static final int DEFEND = DefendComponent.bit;
    public static final int INPUT = InputComponent.bit;
    public static final int INVENTORY = InventoryComponent.bit;
    public static final int TRANSFORM = TransformComponent.bit;
    public static final int SPRITE = SpriteComponent.bit;
    public static final int MOVEMENT = MovementComponent.bit;
    public static final int HEALTH = HealthComponent.bit;
    public static final int MANA = ManaComponent.bit;
    
    
    public Consts() {
        throw new AssertionError("Can't instantiate this object!");
    }
}
