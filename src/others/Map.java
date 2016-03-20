package others;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import enums.TileType;

public class Map {
    private TileType[][] map;
    private int xMax, yMax;
    private Image mapImage;
    private Vector2f offsetVector;
    private float zoom = 1;

    public Map() {
        xMax = Consts.SCREEN_WIDTH / Consts.TILE_SIZE;
        yMax = Consts.SCREEN_HEIGHT / Consts.TILE_SIZE;
        map = new TileType[xMax][yMax];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                float rnd = (float) (Math.random() * 100f);
                if (rnd < 60) {
                    map[i][j] = TileType.Grass;
                } else if (rnd >= 60 && rnd < 95) {
                    map[i][j] = TileType.Dirt;
                } else {
                    map[i][j] = TileType.Stone;
                }
            }
        }

        offsetVector = new Vector2f(0, 0);

        try {
            mapImage = new Image("images/mapbig.png");
        } catch (SlickException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void drawMap(Graphics g) throws SlickException {
        // for (int i = 0; i < map.length; i++) {
        // for (int j = 0; j < map[i].length; j++) {
        // g.drawImage(new Image(map[i][j].getImagePath()), i * 64 + i, j * 64 +
        // j, i * 64 + i + 64,
        // j * 64 + j + 64, 0, 0, 64, 64);
        // }
        // }

        g.drawImage(mapImage, 0, 0);
    }

    public void drawMap(Graphics g, float x, float y, boolean showMinimap) {
        if (x != 0 && y != 0) {
            float angle = (float) Math.atan2(y, x);
            offsetVector.x += x * Math.abs(Math.cos(angle));
            offsetVector.y += y * Math.abs(Math.sin(angle));
        } else {
            offsetVector.x += x;
            offsetVector.y += y;
        }

        g.drawImage(mapImage, offsetVector.getX(), offsetVector.getY());

        if (showMinimap) {
            // minimap is a square, but the screen might not be!
            g.drawImage(mapImage, Consts.SCREEN_WIDTH - 155, 30, Consts.SCREEN_WIDTH - 5, 180,
                    -offsetVector.getX() - Consts.TILE_SIZE * zoom, -offsetVector.getY() - Consts.TILE_SIZE * zoom,
                    -offsetVector.getX() + Consts.TILE_SIZE * 10 + Consts.TILE_SIZE * zoom,
                    -offsetVector.getY() + Consts.TILE_SIZE * 10 + Consts.TILE_SIZE * zoom,
                    new Color(255, 255, 255, 200));
            g.drawString("x", Consts.SCREEN_WIDTH - 80, 105);
        }
    }

    public void zoomIn() {
        zoom = Math.max(zoom - 1, -2);
    }

    public void zoomOut() {
        zoom = Math.min(zoom + 1, 3);
    }

    public Vector2f getOffset() {
        return offsetVector;
    }
}
