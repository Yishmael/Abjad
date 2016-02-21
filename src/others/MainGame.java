package others;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import components.AttackComponent;
import components.HealthComponent;
import components.InputComponent;
import components.InventoryComponent;
import components.ManaComponent;
import components.MovementComponent;
import components.SpriteComponent;
import components.TransformComponent;
import data.KeyHandler;
import data.MapTile;
import data.Player;

public class MainGame extends BasicGame {

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Stars are falling"));
            appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, false);
            appgc.setTargetFrameRate(200);
            appgc.setIcon("images/icon.png");
            // appgc.setAlwaysRender(true);
            // appgc.setClearEachFrame(false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private KeyHandler keyHandler;
    private MapTile mapTile;
    private Player player;
    private ArrayList<others.Entity> ents;
    private MessageChannel channel;
    private Input input;

    public MainGame(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        mapTile = new MapTile(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
        player = new Player(new Image("images/ifrit.png"), "Ifrit", Consts.SCREEN_WIDTH / 2, Consts.SCREEN_HEIGHT / 2,
                100, 200f, mapTile);
        keyHandler = new KeyHandler(gc, player);
        ents = new ArrayList<Entity>();

        // Entity player = new Entity("Player");
        // player.addComponent(...);
        Entity player1 = new Entity("Player");
        player1.addComponent(new AttackComponent(16, 4));
        player1.addComponent(new HealthComponent(100, 200));
        player1.addComponent(new InputComponent());
        int[] matrix = { 0, 0, 102, 0 };
        player1.addComponent(new InventoryComponent("images/inventory.png", matrix));
        player1.addComponent(new SpriteComponent("images/ifrit.png"));
        player1.addComponent(new TransformComponent(220, 300 - 100, 0f, 1f));

        Entity enemy1 = new Entity("Enemy bot");
        enemy1.addComponent(new AttackComponent(36, 1));
        enemy1.addComponent(new MovementComponent(50));
        enemy1.addComponent(new HealthComponent(78, 100));
        enemy1.addComponent(new ManaComponent(0, 1000));
        enemy1.addComponent(new TransformComponent(300, 200, 0f, 1f));
        enemy1.addComponent(new SpriteComponent("images/bahamut.png"));
        enemy1.addComponent(new InventoryComponent("images/shield1.png", new int[0]));

        ents.add(player1);
        ents.add(enemy1);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {

        input = gc.getInput();
        mapTile.render(g);
        player.render(g);

        if (player.showInventory) {
            player.inventory.render(g);
        }

        if (player.showMenu) {
            player.menu.render(g);
        }

        for (Entity ent1: ents) {
            if (ent1.hasComponent(Consts.INPUT)) {
                if (input.isKeyDown(Input.KEY_UP)) {
                    ent1.process(new MessageChannel(ent1, "handleKey KEY_UP"));
                }
                if (input.isKeyDown(Input.KEY_DOWN)) {
                    ent1.process(new MessageChannel(ent1, "handleKey KEY_DOWN"));
                }
                if (input.isKeyDown(Input.KEY_LEFT)) {
                    ent1.process(new MessageChannel(ent1, "handleKey KEY_LEFT"));
                }
                if (input.isKeyDown(Input.KEY_RIGHT)) {
                    ent1.process(new MessageChannel(ent1, "handleKey KEY_RIGHT"));
                }
                if (input.isKeyPressed(Input.KEY_I)) {
                    ent1.process(new MessageChannel(ent1, "handleKey KEY_I"));
                }
            }
        }
        if (!input.isKeyDown(Input.KEY_LSHIFT) && input.isKeyDown(Input.KEY_2)) {
            for (Entity ent1: ents) {
                if (!ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.TRANSFORM)
                        && ent1.hasComponent(Consts.SPRITE)) {
                    channel = new MessageChannel(ent1, "move 0.001 0 0 0.99");
                    ent1.process(channel);
                }
            }
        }
        if (input.isKeyDown(Input.KEY_LSHIFT) && input.isKeyDown(Input.KEY_2)) {
            for (Entity ent1: ents) {
                if (!ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.TRANSFORM)
                        && ent1.hasComponent(Consts.SPRITE)) {
                    channel = new MessageChannel(ent1, "move 0.001 0 0 1.01");
                    ent1.process(channel);
                }
            }
        } else if (input.isKeyPressed(Input.KEY_1)) {
            for (Entity ent1: ents) {
                if (ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.ATTACK)) {
                    for (Entity ent2: ents) {
                        if (ent2 != ent1 && ent2.hasComponent(Consts.HEALTH)) {
                            channel = new MessageChannel(ent1, "heal 7");
                            ent2.process(channel);
                        }
                    }
                }
            }
        }

        input.clearKeyPressedRecord();

        for (Entity ent1: ents) {
            if (ent1.hasComponent(Consts.SPRITE) && ent1.hasComponent(Consts.TRANSFORM)) {
                ent1.process(new MessageChannel(ent1, "move"));
            }
            if (ent1.hasComponent(Consts.INVENTORY)) {
                ent1.process(new MessageChannel(ent1, "drawI"));
            }
        }
        // bit 0 reserved
        // long mask = 2;
        //
        // for (Entity ent1: ents) {
        // if ((ent1.getID() & mask) == mask) {
        // channel = new MessageChannel(ents.get(0), "damage 20");
        // ents.get(1).process(channel);
        // for (Entity ent2: ents) {
        // if ((ent2.getID() & 8) == 8) {
        // ent2.process(channel);
        // }
        // }
        // }
        // }
        //
        // mask = 2;
        // // has attack
        // for (Entity ent: ents) {
        // if ((ent.getID() & mask) == mask) {
        // channel = new MessageChannel(ent, "strike");
        // ent.Process(channel);
        // // System.out.print("Has attack! ");
        // // System.out.println(Long.toString(ent.getID(), 2));
        // }
        // }
        //
        // return;
        //
        // mask = 8;
        // // has health
        // for (Entity ent: ents) {
        // if ((ent.getID() & mask) == mask) {
        // channel = new MessageChannel(ent, "heal 33");
        // ent.Process(channel);
        // // System.out.print("Has health! ");
        // // System.out.println(Long.toString(ent.getID(), 2));
        // }
        // }
        //
        // mask = 4;
        // // has defend
        // for (Entity ent: ents) {
        // if ((ent.getID() & mask) == mask) {
        // channel = new MessageChannel(ent, "uuuuuuuu 33");
        // ent.Process(channel);
        // // System.out.print("Has defend! ");
        // // System.out.println(Long.toString(ent.getID(), 2));
        // }
        // }
        // mask = 16;
        // // has inventory
        // for (Entity ent: ents) {
        // if ((ent.getID() & mask) == mask) {
        // channel = new MessageChannel(ent, "hhhhhhh 33");
        // ent.Process(channel);
        // // System.out.print("Has inventory! ");
        // // System.out.println(Long.toString(ent.getID(), 2));
        // }
        // }
        // mask = 32;
        // // has movement
        // for (Entity ent: ents) {
        // if ((ent.getID() & mask) == mask) {
        // channel = new MessageChannel(ent, "tttttt 33");
        // ent.Process(channel);
        // // System.out.print("Has movement! ");
        // // System.out.println(Long.toString( ent.getID(), 2));
        // }
        // }
        // mask = 64;
        // // has transformation
        // for (Entity ent: ents) {
        // if ((ent.getID() & mask) == mask) {
        // channel = new MessageChannel(ent, "nnnnnnnn 33");
        // ent.Process(channel);
        // // System.out.print("Has transformation! ");
        // // System.out.println(Long.toString(ent.getID(), 2));
        // }
        // }
        // mask = 128;
        // // has sprite
        // for (Entity ent: ents) {
        // if ((ent.getID() & mask) == mask) {
        // channel = new MessageChannel(ent, "lllll 33");
        // ent.Process(channel);
        // // System.out.print("Has sprite! ");
        // // System.out.println(Long.toString(ent.getID(), 2));
        // }
        // }

        // for (Component comp: colliBox.getComponents()) {
        // comp.update();
        // }
        // for (Component comp: enemy.getComponents()) {
        // comp.update();
        // }

    }

    @Override
    public void update(GameContainer gc, int dt) throws SlickException {
        keyHandler.update();
        player.update(dt);

        if (player.showInventory) {
            player.inventory.update();
        }

        if (player.showMenu) {
            player.menu.update();
        }

    }

}
