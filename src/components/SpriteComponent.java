package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import others.MessageChannel;

public class SpriteComponent implements Component {
    public static int bit = 7;
    private Image image;
    private float width, height;
    private Graphics g;

    public SpriteComponent(String imagePath) throws SlickException {
        if (imagePath != null && imagePath.length() >= 12) {
            image = new Image(imagePath);
        } else {
            image = null;
        }
        this.width = image.getWidth();
        this.height = image.getHeight();
        g = new Graphics();
    }

    public void draw(float x, float y, float rotation, float scale) {
        if (image != null) {
            g.drawImage(image, x, y, x + width * scale, y + height * scale, 0, 0, width, height);
            // g.rotate(x + image.getWidth(), y + image.getHeight(), rotation);
        }
    }

    @Override
    public int getBit() {
        return bit;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
        String[] list = null;
        String str = channel.getCommand();
        if (str.matches("draw [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(5);
            list = str.split(" ");
            draw(Float.parseFloat(list[0]), Float.parseFloat(list[1]), Float.parseFloat(list[2]),
                    Float.parseFloat(list[3]));
        }
    }

    @Override
    public void update() {
        // if (image != null) {
        // g.drawImage(image, x, y, (x + image.getWidth() * scale),
        // (y + image.getHeight() * scale), 0, 0, image.getWidth(),
        // image.getHeight());
        // g.rotate(x, y, rotation);
        // }
    }
}
