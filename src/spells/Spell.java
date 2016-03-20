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
    protected Vector2f position;
    protected float angle, xProj, yProj;
    protected float areaOfEffect;
    protected Ellipse ellipse;
    protected float radius;
    protected float speed;
    protected String targets;
    protected boolean finished;

    public Spell(Image image, String targets, Vector2f position, float angle, float areaOfEffect) {
        this.image = image;
        this.targets = targets;
        this.position = new Vector2f(position);
        this.angle = angle;
        this.areaOfEffect = areaOfEffect;
        this.ellipse = new Ellipse(position.getX(), position.getY(), areaOfEffect, areaOfEffect);
        this.radius = (ellipse.getRadius1() + ellipse.getRadius2()) / 2;

        image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);

        image.setRotation((float) (angle * 180f / Math.PI));

        xProj = (float) Math.cos(angle);
        yProj = (float) Math.sin(angle);
    }

    public void render(Graphics g) {
        image.drawCentered(position.getX(), position.getY());
        g.draw(ellipse);
        // g.drawImage(image, position.getX(), position.getY());
    }

    public void update() {
        float dx = xProj * MainGame.dt / 1000f * speed * Consts.TILE_SIZE;
        float dy = yProj * MainGame.dt / 1000f * speed * Consts.TILE_SIZE;

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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
        xProj = (float) Math.cos(angle);
        yProj = (float) Math.sin(angle);
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

    public void setTargets(String targets) {
        if (targets == null) {
            return;
        }
        this.targets = targets;
    }

    public String getTargets() {
        return targets;
    }

    public boolean isFinished() {
        return finished;
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

    public abstract void trigger();

}
