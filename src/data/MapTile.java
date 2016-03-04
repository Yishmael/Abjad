package data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import enums.TileType;
import others.Consts;
import others.MapGen;

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
    private MapGen mapgen;
    private Texture mapTex;
    private Image mapImg;

    public MapTile(final int WIDTH, final int HEIGHT) {
        xMax = WIDTH / Consts.TILE_SIZE;
        yMax = HEIGHT / Consts.TILE_SIZE;
        Random dice = new Random();
        matrix = new int[xMax][yMax];
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                int rand = dice.nextInt(100);
                if (rand < 60) {
                    matrix[i][j] = TileType.Grass.ordinal();
                } else if (rand >= 60 && rand < 95) {
                    matrix[i][j] = TileType.Dirt.ordinal();
                } else {
                    matrix[i][j] = TileType.Stone.ordinal();
                }
            }
        }
        mapgen = new MapGen();
    }

    private Image getImage(int tileIndex) throws SlickException {
        Image image;
        String path = "images/tiles/" + TileType.values()[tileIndex] + ".png";

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
            g.drawImage(new Image("images/map.png"), 0, 0, Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, 0, 0,
                    Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
            // mapImg = new Image(mapTex);
            // g.drawImage(mapImg, 0, 0, Consts.SCREEN_WIDTH,
            // Consts.SCREEN_HEIGHT, 0, 0, Consts.SCREEN_WIDTH,
            // Consts.SCREEN_HEIGHT);
        } else {
//            try {
//                mapTex = BufferedImageUtil.getTexture("images/mapgen.png", mapgen.generateMap(matrix));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            changed = false;
        }
    }

    public void setTile(int xTile, int yTile, TileType tileType) {
        if (xTile < 0 || xTile >= xMax || yTile < 0 || yTile >= yMax)
            return;

        if (matrix[xTile][yTile] != tileType.ordinal()) {
            matrix[xTile][yTile] = tileType.ordinal();
            changed = true;
        }
    }

    public void update() {

    }

}
