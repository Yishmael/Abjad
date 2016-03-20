package spells.projectile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Bouncer extends Projectile {
    private float damage, amount;
    private String command = "arcanedmg";
    private int bounces = 0;

    public Bouncer(Image image, String creator, Vector2f position, float angle, float speed, float range, float damage,
            float areaOfEffect) throws SlickException {
        super(image, creator, position, angle, speed, range, areaOfEffect);
        this.damage = damage;
        this.amount = damage;
    }

    @Override
    public String getMessage() {
        return command + " " + amount;
    }

    public void trigger() {
        if (!finished) {
            switch (bounces) {
            case 0:
                command = "heal";
                amount = damage * 0.5f;
                super.setTargets("friendly");
                super.setSpeed(super.getSpeed() * 0.6f);
                super.setAngle((float) (Math.PI + super.getAngle()));
                break;
            case 1:
                finished = true;
                // TODO fix this
                // command = "";
                break;
            default:
                break;
            }

            bounces++;
        }
    }

}
