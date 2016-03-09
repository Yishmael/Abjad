package others;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

public class Button {
    private Image image;
    private Shape shape;
    private String description, command;

    public Button(Image image, Shape shape, String description, String command) {
        this.image = image;
        this.shape = shape;
        this.description = description;
        this.command = command;
    }

    public void draw(Graphics g) {
        g.drawImage(image, shape.getX(), shape.getY(), shape.getX() + shape.getWidth(),
                shape.getY() + shape.getHeight(), 0, 0, image.getWidth(), image.getHeight());
        g.draw(shape);
    }

    public void drawDescription(Graphics g) {
        g.drawString(description, shape.getCenterX(), shape.getCenterY());
    }

    public Shape getShape() {
        return shape;
    }

    public String getDescription() {
        return description;
    }

    public String getCommand() {
        return command;
    }
    
}
