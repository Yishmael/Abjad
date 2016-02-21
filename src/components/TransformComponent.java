package components;

import org.newdawn.slick.SlickException;

import others.Entity;
import others.MessageChannel;

public class TransformComponent implements Component {

    public static int bit = 8;
    private float x, y, rotation, scale;

    public TransformComponent(float x, float y, float rotation, float scale) throws SlickException {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.scale = scale;
    }

    @Override
    public int getBit() {
        return bit;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(Entity sender, float x, float y, float rotation, float scale) {
        if (x == 0 && y == 0) {
            this.scale = scale;
        } else {
            this.scale *= scale;
        }
        this.x += x;
        this.y += y;
        this.rotation += rotation;
        // System.out.println("Moved by " + x + ":" + y + " rotation " +
        // rotation + ", scale " + scale);
        sender.process(
                new MessageChannel(sender, "draw " + this.x + " " + this.y + " " + this.rotation + " " + this.scale));

    }

    public void moveA(Entity sender, float x, float y) {
        this.x = x;
        this.y = y;
        // System.out.println("Moved to " + x + ":" + y);
        sender.process(new MessageChannel(sender, "draw " + this.x + " " + this.y + " " + 0 + " " + 1));

    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
        String str = channel.getCommand();
        String[] list = null;
        if (str.matches("rescale [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(7);
            list = str.split(" ");
            rescale(Integer.parseInt(list[1]));
            return;
        }
        if (str.matches("rotate [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(7);
            list = str.split(" ");
            rotate(Float.parseFloat(list[1]));
            return;
        }
        if (str.matches("moveA [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            list = str.split(" ");
            moveA(channel.getSender(), Float.parseFloat(list[0]), Float.parseFloat(list[1]));
            return;
        }
        if (str.matches("move [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(5);
            list = str.split(" ");
            move(channel.getSender(), Float.parseFloat(list[0]), Float.parseFloat(list[1]), Float.parseFloat(list[2]),
                    Float.parseFloat(list[3]));
            return;
        }
        if (str.matches("move")) {
            move(channel.getSender(), 0, 0, rotation, scale);
            return;
        }
    }

    @Override
    public void update() {

    }

    public void rescale(int scale) {
        this.scale = scale;
    }

    public void rotate(float rotation) {

        this.rotation += rotation;
        this.rotation %= 360;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
