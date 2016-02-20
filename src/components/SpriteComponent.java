package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import others.MessageChannel;

public class SpriteComponent implements Component {
    public static int bit = 7;
    private Image image;
    private Graphics g;

    public SpriteComponent(String imagePath) throws SlickException {
        image = new Image(imagePath);
        g = new Graphics();
    }

    @Override
    public int getBit() {
        return bit;
    }
    
    @Override
    public void process(MessageChannel channel) {
        String str = channel.getCommand();
        if (str.length() >= 6) {
            if (str.substring(0, 4).equals("draw")) { // use startsWith()
                draw(Integer.parseInt(str.split(" ")[1]), Integer.parseInt(str.split(" ")[2]));
            }
        }
    }
    
    public void draw(int x, int y) {
        g.drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), 0, 0, 64, 64);
    }

    public void update() {
        // if (image != null) {
        // g.drawImage(image, x, y, (x + image.getWidth() * scale),
        // (y + image.getHeight() * scale), 0, 0, image.getWidth(),
        // image.getHeight());
        // g.rotate(x, y, rotation);
        // }
    }
}
