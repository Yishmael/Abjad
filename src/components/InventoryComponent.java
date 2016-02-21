package components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import others.MessageChannel;

public class InventoryComponent implements Component {
    public static int bit = 5;
    private Image image;
    private float width, height;
    private Graphics g;
    private boolean shown = false;
    private int[] matrix;

    public InventoryComponent(String imagePath, int[] matrix) throws SlickException {
        this.image = new Image(imagePath);
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.matrix = matrix;
        g = new Graphics();
    }

    private void draw() throws SlickException {
        g.drawImage(image, 0, 320, 640, 512, 0, 0, width, height, new Color(255, 150, 150, 150));
        for (int i = 0, j = 0; i < matrix.length; i++) {
            if (i == 10 || i == 20) {
                j++;
            }
            switch (matrix[i]) {
            case 101:
                g.drawImage(new Image("images/axe1.png"), 64 * (i - 10 * j), 320 + 64 * j);
                break;
            case 102:
                g.drawImage(new Image("images/axe2.png"), 64 * (i - 10 * j), 320 + 64 * j);
                break;
            case 201:
                g.drawImage(new Image("images/shield1.png"), 64 * (i - 10 * j), 320 + 64 * j);
                break;
            case 202:
                g.drawImage(new Image("images/shield2.png"), 64 * (i - 10 * j), 320 + 64 * j);
                break;
            case 501:
                g.drawImage(new Image("images/boulder1.png"), 64 * (i - 10 * j), 320 + 64 * j);
                break;
            default:
                break;

            }
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
        String str = channel.getCommand();
        if (str.matches("drawI")) {
            if (shown) {
                try {
                    draw();
                } catch (SlickException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }

        }
        if (str.matches("drawIT")) {
            shown = !shown;
            return;
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
