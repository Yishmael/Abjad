package data;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import components.AttackComponent;
import components.Component;
import components.HealthComponent;
import components.MovementComponent;
import components.PositionComponent;
import components.SpriteComponent;
import entities.Entity;

public class MainGame extends BasicGame {

    public static final int SCREEN_WIDTH = 640; // 16
    public static final int SCREEN_HEIGHT = 512;// 9
    public static final int TILE_HEIGHT = 64;
    public static final int TILE_WIDTH = 64;

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Stars are falling"));
            appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
            appgc.setTargetFrameRate(1000);
            appgc.setIcon("images/icon.png");
            // appgc.setAlwaysRender(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    private KeyHandler keyHandler;
    private MapTile mapTile;
    private Player player;
    private entities.Entity colliBox, enemy;
    // private Graphics gg;

    public MainGame(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        mapTile = new MapTile(SCREEN_WIDTH, SCREEN_HEIGHT);
        player = new Player(new Image("images/ifrit.png"), "Ifrit",
                SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 100, 200f, mapTile);
        keyHandler = new KeyHandler(gc, player);

        enemy = new Entity(0);
        enemy.addComponent(new SpriteComponent("images/bahamut.png", "Enemy"));
        enemy.addComponent(new HealthComponent(100, 100));
        enemy.addComponent(new PositionComponent(0, 0));
        enemy.addComponent(new MovementComponent(128f));

        colliBox = new Entity(1);
        colliBox.addComponent(
                new SpriteComponent("images/boulder1.png", "Boulder1"));
        // gg = new Graphics();

    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {

        mapTile.render(g);
        player.render(g);

        if (player.showInventory) {
            player.inventory.render(g);
        }

        if (player.showMenu) {
            player.menu.render(g);
        }
        
        for (Component comp: colliBox.getComponents()) {
            comp.update();
        }
        for (Component comp: enemy.getComponents()) {
            comp.update();
        }

    }

    public void update(GameContainer gc, int dt) throws SlickException {
        keyHandler.update();
        player.update(dt);

        if (player.showInventory) {
            player.inventory.update();
        }

        if (player.showMenu) {
            player.menu.update();
        }

        // gg.fillRect(player.xPos + player.getImage().getWidth() / 2 - 3,
        // player.yPos + player.getImage().getHeight() / 2 - 3, 22, 22);
        // gg.setColor(new Color(122, 122, 255));

    }

}
