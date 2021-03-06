package others;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import enums.TileType;

public class MapGenerator {

    private BufferedImage dirtImage, grassImage, stoneImage, currentImage, image;

    public BufferedImage generateMap(int[][] matrix) {
        try {
            grassImage = ImageIO.read(getClass().getResource("images/tiles/grass.png"));
            dirtImage = ImageIO.read(getClass().getResource("images/tiles/dirt.png"));
            stoneImage = ImageIO.read(getClass().getResource("images/tiles/stone.png"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        image = new BufferedImage(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == TileType.Grass.ordinal()) {
                    currentImage = grassImage;
                } else if (matrix[i][j] == TileType.Dirt.ordinal()) {
                    currentImage = dirtImage;
                } else {
                    currentImage = stoneImage;
                }
                for (int k = 0; k < currentImage.getWidth(); k++) {
                    for (int l = 0; l < currentImage.getHeight(); l++) {
                        image.setRGB(i * 64 + k, j * 64 + l, currentImage.getRGB(k, l));
                    }
                }
            }
        }
        try {
            ImageIO.write(image, "PNG", new File("D:/mapgen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
