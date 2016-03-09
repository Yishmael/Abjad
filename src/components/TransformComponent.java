package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Vector2f;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class TransformComponent implements Component {
    private int id = Consts.TRANSFORM;
    private float x, y, rotation, scale;
    private Entity self;
    private float width, height, radius;
    private Ellipse ellipse = new Ellipse(0, 0, 0, 0);
    private Graphics g = new Graphics();

    public TransformComponent(Entity self, float x, float y, float rotation, float scale) throws SlickException {
        this.self = self;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.scale = scale;
        ellipse.setLocation(x, y);
    }

    public TransformComponent(Entity self, float x, float y) throws SlickException {
        this.self = self;
        this.x = x;
        this.y = y;
        this.rotation = 0;
        this.scale = 1;
        ellipse.setLocation(x, y);
    }

    public TransformComponent(Entity self, float x, float y, float scale) throws SlickException {
        this.self = self;
        this.x = x;
        this.y = y;
        this.rotation = 0;
        this.scale = scale;
        ellipse.setLocation(x, y);
    }

    @Override
    public int getID() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getCenterX() {
        return x + width / 2;
    }

    public float getCenterY() {
        return y + height / 2;
    }

    public float getY() {
        return y;
    }

    public Vector2f getPoint() {
        return new Vector2f(x, y);
    }

    public void move(float dx, float dy) {
        if (dx != 0 && dy != 0) {
            this.x += dx * Math.sqrt(2) / 2;
            this.y += dy * Math.sqrt(2) / 2;
        } else {
            this.x += dx;
            this.y += dy;
        }

        ellipse.setLocation(x, y);
        g.draw(ellipse);
        // System.out.println("Moved by " + x + ":" + y);
        brdcst();
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
        if (str.matches("width [0-9]+[.]?[0-9]*")) {
            width = Float.parseFloat(str.substring(6));
            setEllipse();
            return;
        }
        if (str.matches("height [0-9]+[.]?[0-9]*")) {
            height = Float.parseFloat(str.substring(6));
            setEllipse();
            return;
        }
    }

    private void setEllipse() {
        ellipse.setRadii(scale * width / 2, scale * height / 2);
        radius = (ellipse.getRadius1() + ellipse.getRadius2()) / 2;
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

    @Override
    public void update() {

    }
}
