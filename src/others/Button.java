package others;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

public class Button {
    private Image image;
    private Shape shape;
    private String description, command;
    private float x, y, centerX, centerY, width, height, imageWidth, imageHeight;

    public Button(Image image, Shape shape, String description, String command) {
        this.image = image;
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
        this.shape = shape;
        this.x = shape.getX();
        this.y = shape.getY();
        this.centerX = shape.getCenterX();
        this.centerY = shape.getCenterY();
        this.width = shape.getWidth();
        this.height = shape.getHeight();
        this.description = description;
        this.command = command;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, x + width, y + height, 0, 0, imageWidth, imageHeight);
        g.draw(shape);
    }

    public void drawDescription(Graphics g) {
        g.drawString(description, centerX, centerY);
    }

    public boolean contains(float x, float y) {
        return shape.contains(x, y);
    }

    public String getDescription() {
        return description;
    }

    public String getCommand() {
        return command;
    }

}
