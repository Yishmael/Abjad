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
import components.DefendComponent;
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

    public static final int SCREEN_WIDTH = 640; // 16 : 9
    public static final int SCREEN_HEIGHT = 512;
    public static final int TILE_HEIGHT = 64;
    public static final int TILE_WIDTH = 64;

    private static final int ATTACK = AttackComponent.bit;
    private static final int DEFEND = DefendComponent.bit;
    private static final int INPUT = InputComponent.bit;
    private static final int INVENTORY = InventoryComponent.bit;
    private static final int TRANSFORM = TransformComponent.bit;
    private static final int SPRITE = SpriteComponent.bit;
    private static final int MOVEMENT = MovementComponent.bit;
    private static final int HEALTH = HealthComponent.bit;
    private static final int MANA = ManaComponent.bit;

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Stars are falling"));
            appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
            appgc.setTargetFrameRate(2000);
            appgc.setIcon("images/icon.png");
            // appgc.setAlwaysRender(true);
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

    public MainGame(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        mapTile = new MapTile(SCREEN_WIDTH, SCREEN_HEIGHT);
        player = new Player(new Image("images/ifrit.png"), "Ifrit", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 100, 200f,
                mapTile);
        keyHandler = new KeyHandler(gc, player);

        ents = new ArrayList<>();
        ents.add(new Entity("Player"));
        ents.get(0).addComponent(new AttackComponent(16, 4));
        ents.get(0).addComponent(new HealthComponent(100, 200));
        ents.get(0).addComponent(new InputComponent());
        // ents.get(0).addComponent(new MovementComponent(25f));
        // ents.get(0)
        // .addComponent(new TransformationComponent(0f, 0f, 0f, 1.25f));
        // ents.get(0).addComponent(
        // new SpriteComponent("images/bahamut.png", "Enemy"));

        ents.add(new Entity("Enemy bot"));
        ents.get(1).addComponent(new HealthComponent(35, 100));
        ents.get(1).addComponent(new ManaComponent(0, 1000));
        ents.get(1).addComponent(new SpriteComponent("images/boulder1.png"));
        ents.get(1).addComponent(new TransformComponent(200, 200, 0f, 1f));

        // ents.get(1).addComponent(new HealthComponent(100, 120));
        // ents.get(1)
        // .addComponent(new TransformationComponent(20f, 200f, 30f, .5f));

    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        Input input = gc.getInput();

         mapTile.render(g);
        player.render(g);

        if (player.showInventory) {
            player.inventory.render(g);
        }

        if (player.showMenu) {
            player.menu.render(g);
        }

        if (input.isKeyDown(Input.KEY_LSHIFT)) {
            if (input.isKeyPressed(Input.KEY_4)) {
                for (Entity ent1: ents) {
                    if (ent1.hasComponent(INPUT)) {
                        if (ent1.hasComponent(ATTACK)) {
                            for (Entity ent2: ents) {
                                if (ent2 != ent1) {
                                    if (ent2.hasComponent(HEALTH)) {
                                        channel = new MessageChannel(ent1, "heal 7");
                                        ent2.process(channel);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (input.isKeyPressed(Input.KEY_1)) {
                for (Entity ent1: ents) {
                    if (ent1.hasComponent(INPUT)) {
                        if (ent1.hasComponent(ATTACK)) {
                            for (Entity ent2: ents) {
                                if (ent2 != ent1) {
                                    if (ent2.hasComponent(HEALTH)) {
                                        channel = new MessageChannel(ent1, "heal 7");
                                        ent2.process(channel);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (input.isKeyPressed(Input.KEY_2)) {
                for (Entity ent1: ents) {
                    if (ent1.hasComponent(INPUT)) {
                        if (ent1.hasComponent(ATTACK)) {
                            for (Entity ent2: ents) {
                                if (ent2 != ent1) {
                                    if (ent2.hasComponent(TRANSFORM)) {
                                        channel = new MessageChannel(ent1, "move 11 20");
                                        ent2.process(channel);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (input.isKeyDown(Input.KEY_3)) {
                for (Entity ent1: ents) {
                    if (ent1.hasComponent(INPUT)) {
                        if (ent1.hasComponent(ATTACK)) {
                            for (Entity ent2: ents) {
                                if (ent2 != ent1) {
                                    if (ent2.hasComponent(SPRITE)) {
                                        channel = new MessageChannel(ent1, "draw 12 444");
                                        ent2.process(channel);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        input.clearKeyPressedRecord();

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
