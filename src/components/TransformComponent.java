package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class TransformComponent implements Component {
    private int id = Consts.TRANSFORM;
    private float x, y, rotation, scale = 1;
    private Entity self;
    private float width, height, radius;
    private Ellipse ellipse = new Ellipse(0, 0, 0, 0);
    private Graphics g = new Graphics();

    public TransformComponent(Entity self, float x, float y, float width, float height) throws SlickException {
        this.self = self;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        ellipse.setLocation(x, y);
        setRadii();
    }

    public TransformComponent(Entity self, float x, float y, float width, float height, float scale)
            throws SlickException {
        this.self = self;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        ellipse.setLocation(x, y);
        setRadii();
    }

    public TransformComponent(Entity self, float x, float y, float width, float height, float scale, float rotation)
            throws SlickException {
        this.self = self;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.rotation = rotation;
        ellipse.setLocation(x, y);
        setRadii();
    }

    @Override
    public int getID() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getCenterX() {
        return ellipse.getCenterX();
    }

    public float getCenterY() {
        return ellipse.getCenterY();
    }

    public float getY() {
        return y;
    }

    public void move(float dx, float dy) {
        if (dx != 0 && dy != 0) {
            float angle = (float) Math.atan2(dy, dx);
            this.x += dx * Math.abs(Math.cos(angle));
            this.y += dy * Math.abs(Math.sin(angle));
        } else {
            this.x += dx;
            this.y += dy;
        }

        ellipse.setLocation(x, y);

        // System.out.println("Moved by " + x + ":" + y);
        brdcst();
    }

    // temp
    public void draw() {
        g.draw(ellipse);
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String[] list = null;
        String str = command;
        if (str.matches("move [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(5);
            list = str.split(" ");
            move(Float.parseFloat(list[0]), Float.parseFloat(list[1]));
            return;
        }
        if (str.matches("rescale [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(7);
            list = str.split(" ");
            rescale(Float.parseFloat(list[0]));
            return;
        }
        if (str.matches("rotate [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(7);
            rotate(Float.parseFloat(str));
            return;
        }
        if (str.matches("reposition [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            reposition(Float.parseFloat(list[0]), Float.parseFloat(list[1]));
            return;
        }
        if (str.matches("requestPos")) {
            brdcst();
            return;
        }
    }

    private void setRadii() {
        ellipse.setRadii(scale * width / 2, scale * height / 2);
        radius = ellipse.getBoundingCircleRadius();
    }

    public void reposition(float x, float y) {
        this.x = x;
        this.y = y;
        // System.out.println("Moved to " + x + ":" + y);
        brdcst();
    }

    public void rescale(float scale) {
        this.scale = scale;
        brdcst();
    }

    public void rotate(float rotation) {
        this.rotation += rotation;
        this.rotation %= 360;
        brdcst();
    }

    private void brdcst() {
        // keep this until i encode messages as ints
        SpriteComponent sprite = ((SpriteComponent) self.getComponent(Consts.SPRITE));
        if (sprite != null) {
            sprite.draw(x, y, rotation, scale);
        }

        // self.broadcast("draw " + this.x + " " + this.y + " " + this.rotation
        // + " " + this.scale);
    }

    public Ellipse getShape() {
        return ellipse;
    }

    public float getRadius() {
        return radius;
    }

    public boolean contains(float x, float y) {
        return ellipse.contains(x, y);
    }

    @Override
    public void update() {

    }
}
