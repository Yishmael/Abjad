package others;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

import loaders.FontLoader;

public class Button {
    private Image image;
    private Shape shape;
    private String description, command;
    private float x, y, centerX, centerY, width, height, imageWidth, imageHeight;
    private ArrayList<String> descriptionList;

    public Button(Image image, Shape shape, String description, String command) {
        this.image = image;
        if (image != null) {
            this.imageWidth = image.getWidth();
            this.imageHeight = image.getHeight();
        }
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
        if (image != null) {
            g.drawImage(image, x, y, x + width, y + height, 0, 0, imageWidth, imageHeight);
        }
        g.draw(shape);
    }

    public void drawDescription(Graphics g) {
        g.drawString(description, centerX, centerY);
    }

    public void drawDescription(Graphics g, FontLoader font) {
        font.draw(centerX, centerY, description);
    }

    public boolean contains(float x, float y) {
        return shape.contains(x, y);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setDescriptionList(ArrayList<String> descriptionList) {
        this.descriptionList = descriptionList;
    }
    
    public ArrayList<String> getDescriptionList() {
        return descriptionList;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setImage(Image image) {
        this.image = image;
        if (image != null) {
            this.imageWidth = image.getWidth();
            this.imageHeight = image.getHeight();
        }
    }

    public Image getImage() {
        return image;
    }

    public Shape getShape() {
        return shape;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
