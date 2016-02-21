package data;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import others.Consts;

public class Bot extends Creature {
    private MapTile mapTile;

    public Bot(Image image, String name, int xPos, int yPos, int health, float speed, MapTile mapTile)
            throws SlickException {
        super(image, name, xPos, yPos, health, speed, mapTile);
        // TODO Auto-generated constructor stub
        this.mapTile = mapTile;
    }

    @Override
    public void move(float x, float y) {
        // xPos += speed*x;
        // yPos += speed*y;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, xPos, yPos);
        // TODO Auto-generated method stub

    }

    @Override
    public void update(int dt) {
        delta = (float) dt / 1000;
        if (delta <= 0)
            return;
        int xTile = (int) Math.floor((xPos + 70) / 64);
        int yTile = (int) Math.floor((yPos + 64) / 64);

        // can right and wants right
        if (xPos + 90 < Consts.SCREEN_WIDTH) {

            if (mapTile.getTileType(xTile, yTile).isWalkable())
                xPos += speed * delta;
        }
        xPos %= Consts.SCREEN_WIDTH - 90;
        yPos %= Consts.SCREEN_HEIGHT - 90;
    }

}
