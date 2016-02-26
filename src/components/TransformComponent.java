package components;

import org.newdawn.slick.SlickException;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class TransformComponent implements Component {

    private int bit = Consts.TRANSFORM;
    private float x, y, rotation, scale;
    private Entity self;

    public TransformComponent(Entity self, float x, float y, float rotation, float scale) throws SlickException {
        this.self = self;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.scale = scale;
    }

    @Override
    public int getBit() {
        return bit;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(float x, float y) {
        if (x != 0 && y != 0) {
            // TODO fix diagonal speed
        } else {
            this.x += x;
            this.y += y;
        }
        // System.out.println("Moved by " + x + ":" + y);
        update();
    }

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub

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
            list = str.split(" ");
            rotate(Float.parseFloat(list[0]));
            return;
        }
        if (str.matches("reposition [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            reposition(Float.parseFloat(list[0]), Float.parseFloat(list[1]));
            return;
        }
        if (str.matches("requestPos")) {
            update();
            return;
        }
    }

    public void reposition(float x, float y) {
        this.x = x;
        this.y = y;
        // System.out.println("Moved to " + x + ":" + y);
        update();
    }

    public void rescale(float scale) {
        this.scale = scale;
        update();
    }

    public void rotate(float rotation) {
        this.rotation += rotation;
        this.rotation %= 360;
        update();
    }

    @Override
    public void update() {
        self.broadcast("draw " + this.x + " " + this.y + " " + this.rotation + " " + this.scale);
    }
}
