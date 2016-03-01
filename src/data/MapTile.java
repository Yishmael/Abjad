package data;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import others.Consts;

public class MapTile {
    private int[][] matrix;
    // {{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    // { 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
    // { 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
    // { 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
    // { 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
    // { 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
    // { 0, 1, 1, 1, 0, 0, 2, 0, 0, 0 },
    // { 1, 2, 3, 4, 5, 6, 2, 0, 0, 0 },
    // { 0, 1, 0, 0, 0, 0, 2, 0, 0, 0 },
    // { 0, 1, 1, 1, 1, 1, 2, 0, 0, 0 },
    // { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 },
    // { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 },
    // { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 },
    // { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 },
    // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
    private int xMax, yMax;
    private boolean changed = true;

    public MapTile(final int WIDTH, final int HEIGHT) {
        xMax = WIDTH / Consts.TILE_SIZE;
        yMax = HEIGHT / Consts.TILE_SIZE;
        Random dice = new Random();
        matrix = new int[xMax][yMax];
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                int rand = dice.nextInt(100);
                if (i == xMax - 1 || j == yMax - 1)
                    matrix[i][j] = TileType.Sand.ordinal();
                else if (rand < 66)
                    matrix[i][j] = TileType.Grass.ordinal();
                else if (rand >= 66 && rand < 95)
                    matrix[i][j] = TileType.Dirt.ordinal();
                else
                    matrix[i][j] = TileType.Stone.ordinal();
            }
        }

    }

    private Image getImage(int tileIndex) throws SlickException {
        Image image;
        String path = "images/" + TileType.values()[tileIndex] + ".png";

        image = new Image(path);

        return image;
    }

    public TileType getTileType(int xTile, int yTile) {
        if (xTile < 0 || xTile >= xMax || yTile < 0 || yTile >= yMax)
            return TileType.Null;
        return TileType.values()[matrix[xTile][yTile]];
    }

    public int getTileX(int x) {
        return (int) Math.floor((x) / (Consts.TILE_SIZE + 1)); // +1 cause
                                                               // padding
    }

    public int getTileY(int y) {
        return (int) Math.floor((y) / (Consts.TILE_SIZE + 1)); // padding
    }

    public void render(Graphics g) throws SlickException {
        if (!changed) {
            g.fillRect(0, 0, Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, new Image("images/map.png"), 0, 0);
        } else {
            for (int i = 0; i < xMax; i++) {
                for (int j = 0; j < yMax; j++) {
                    g.drawImage(getImage(matrix[i][j]), Consts.TILE_SIZE * i + i, Consts.TILE_SIZE * j + j);
                }
            }
            changed = false;
        }
    }

    public void setTile(int xTile, int yTile, TileType tileType) {
        if (xTile < 0 || xTile >= xMax || yTile < 0 || yTile >= yMax)
            return;

        matrix[xTile][yTile] = tileType.ordinal();
    }

    public void update() {

    }

}
