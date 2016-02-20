package components;

import org.newdawn.slick.SlickException;

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
    public void process(MessageChannel channel) {
        String str = channel.getCommand();
        if (str.length() >= 8) {
            if (str.substring(0, 6).equals("resize")) { // use startsWith()
                resize(Integer.parseInt(str.split(" ")[1]));
                return;
            } else {
                if (str.substring(0, 6).equals("rotate")) { // use startsWith()
                    rotate(Integer.parseInt(str.split(" ")[1]));
                    return;
                }
            }
        }
        if (str.length() >= 6) {
            if (str.substring(0, 4).equals("move")) { // use startsWith()
                move(Integer.parseInt(str.split(" ")[1]), Integer.parseInt(str.split(" ")[2]));
            }
        }

    }

    public void rotate(int rotation) {

        this.rotation += rotation;
        this.rotation %= 360;
    }

    public void resize(int scale) {
        this.scale = scale;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Moved to " + x + ":" + y);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
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

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void update() {
    }

}
