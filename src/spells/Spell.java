package spells;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Vector2f;

import others.Consts;
import others.MainGame;

public abstract class Spell {
    protected Image image;
    protected SpriteSheet sheet;
    protected Vector2f position, facing;
    protected float areaOfEffect;
    protected Ellipse ellipse;
    protected float radius;
    protected float speed;
    protected String creator;

    public Spell(Image image, String creator, Vector2f position, Vector2f facing, float areaOfEffect) {
        this.image = image;
        this.creator  = creator;
        this.position = new Vector2f(position);
        this.facing = new Vector2f(facing);
        this.areaOfEffect = areaOfEffect;
        this.ellipse = new Ellipse(position.getX(), position.getY(), areaOfEffect, areaOfEffect);
        this.radius = (ellipse.getRadius1() + ellipse.getRadius2()) / 2;

        image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);

        if (facing.getX() == 1 && facing.getY() == 0) {
            image.rotate(90 * 0);
        } else if (facing.getX() == 0 && facing.getY() == 1) {
            image.rotate(90 * 1);
        } else if (facing.getX() == -1 && facing.getY() == 0) {
            image.rotate(90 * 2);
        } else if (facing.getX() == 0 && facing.getY() == -1) {
            image.rotate(90 * 3);
        } else if (facing.getX() == 1 && facing.getY() == -1) {
            // diagonal movement?
        }
    }

    public void render(Graphics g) {
        image.drawCentered(position.getX(), position.getY());
        g.draw(ellipse);
        // g.drawImage(image, position.getX(), position.getY());
    }

    public void update() {
        float dx = facing.getX() * MainGame.dt / 1000f * speed * Consts.TILE_SIZE;
        float dy = facing.getY() * MainGame.dt / 1000f * speed * Consts.TILE_SIZE;
        position.x += dx;
        position.y += dy;
        ellipse.setCenterX(position.getX());
        ellipse.setCenterY(position.getY());
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public float getCenterX() {
        return position.getX();
    }

    public float getCenterY() {
        return position.getY();
    }

    public Vector2f getFacing() {
        return facing;
    }

    public float getAreaOfEffect() {
        return areaOfEffect;
    }

    public Ellipse getShape() {
        return ellipse;
    }

    public float getRadius() {
        return radius;
    }
    
    public String getCreator() {
        return creator;
    }

    public void offset(Vector2f direction) {
        if (direction.x != 0 && direction.y != 0) {
            position.x += direction.getX() * Math.sqrt(2) / 2;
            position.y += direction.getY() * Math.sqrt(2) / 2;
        } else {
            position.x += direction.getX();
            position.y += direction.getY();
        }
    }

    public abstract String getMessage();

    public abstract boolean finished();

    public abstract void trigger();

}
