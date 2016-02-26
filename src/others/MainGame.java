package others;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.Sys;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import components.CollisionComponent;
import components.CombatComponent;
import components.HealthComponent;
import components.InputComponent;
import components.InventoryComponent;
import components.ManaComponent;
import components.MovementComponent;
import components.SpellComponent;
import components.SpriteComponent;
import components.TransformComponent;
import data.KeyHandler;
import data.MapTile;
import data.Player;

public class MainGame extends BasicGame {

    public static int dt = 0;

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Stars are falling"));
            appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, false);
            appgc.setTargetFrameRate(5005);
            appgc.setIcon("images/icon.png");
            appgc.setClearEachFrame(false);
            appgc.setAlwaysRender(true);
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

    private Graphics g;
    private long lastTime = 0;

    public MainGame(String gamename) {
        super(gamename);
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastTime);
        lastTime = time;
        return delta;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        mapTile = new MapTile(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
        player = new Player(new Image("images/ifrit.png"), "Ifrit", Consts.SCREEN_WIDTH / 2, Consts.SCREEN_HEIGHT / 2,
                100, 200f, mapTile);
        keyHandler = new KeyHandler(gc, player);
        ents = new ArrayList<Entity>();
        g = new Graphics();
        
        Entity player1 = new Entity("Player");
        player1.addComponent(new CombatComponent(player1, 25, 5, 0.75f));
        player1.addComponent(new MovementComponent(player1, 3));
        player1.addComponent(new SpriteComponent(player1, "images/ifrit.png"));
        player1.addComponent(new HealthComponent(player1, 250, 300, 2));
        player1.addComponent(new ManaComponent(player1, 500, 500, 11));
        int[] invMatrix = { 0, 0, 102, 0 };
        player1.addComponent(new InventoryComponent(player1, "images/inventory1.png", invMatrix));
        player1.addComponent(new InputComponent(player1));
        player1.addComponent(new TransformComponent(player1, 220, 200, 10f, 1f));
        player1.addComponent(new SpellComponent(player1, 75, 40, 0.3f));
        player1.addComponent(new CollisionComponent(player1));

        Entity enemy1 = new Entity("Enemy bot");
        enemy1.addComponent(new SpriteComponent(enemy1, "images/bahamut.png"));
        enemy1.addComponent(new HealthComponent(enemy1, 200, 500, 7));
        enemy1.addComponent(new MovementComponent(enemy1, 1));
        enemy1.addComponent(new ManaComponent(enemy1, 700, 700, 10));
        enemy1.addComponent(new InventoryComponent(enemy1, "images/inventory2.png", new int[0]));
        enemy1.addComponent(new TransformComponent(enemy1, 110, 200, 0f, 1.2f));
        enemy1.addComponent(new CollisionComponent(enemy1));

        Entity prop1 = new Entity("Prop1");
        prop1.addComponent(new SpriteComponent(prop1, "images/barrel.png"));
        prop1.addComponent(new HealthComponent(prop1, 100, 100, 0));
        prop1.addComponent(new TransformComponent(prop1, 550, 400, 0f, 0.6f));

        Entity campfire1 = new Entity("Campfire1");
        campfire1.addComponent(new SpriteComponent(campfire1, new SpriteSheet("images/campfire1.png", 64, 64)));
        campfire1.addComponent(new CombatComponent(campfire1, 1, 10, 1));
        campfire1.addComponent(new HealthComponent(campfire1, 100, 100, 0));
        campfire1.addComponent(new TransformComponent(campfire1, 320, 100, 0f, 1f));

        ents.add(player1);
        ents.add(enemy1);
        ents.add(prop1);
        ents.add(campfire1);

    }

    @Override
    public void update(GameContainer gc, int unusedDelta) throws SlickException {
        dt = getDelta();
        if (dt > 30) {
            dt = 0;
        }
        update(dt);
        g.fillRect(0, 0, Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
        g.setColor(Color.black);
        input = gc.getInput();
        mapTile.render(g);
        player.render(g);

        for (Entity ent1: ents) {
            if (ent1.hasComponent(Consts.INPUT)) {
                if (input.isKeyDown(Input.KEY_UP)) {
                    ent1.broadcast("KEY " + Input.KEY_UP);
                } else if (input.isKeyDown(Input.KEY_DOWN)) {
                    ent1.broadcast("KEY " + Input.KEY_DOWN);
                }
                if (input.isKeyDown(Input.KEY_LEFT)) {
                    ent1.broadcast("KEY " + Input.KEY_LEFT);
                } else if (input.isKeyDown(Input.KEY_RIGHT)) {
                    ent1.broadcast("KEY " + Input.KEY_RIGHT);
                }
                if (input.isKeyPressed(Input.KEY_I)) {
                    ent1.broadcast("KEY " + Input.KEY_I);
                }
                if (input.isKeyPressed(Input.KEY_SPACE)) {
                    ent1.broadcast("KEY " + Input.KEY_SPACE);
                }
                if (input.isKeyPressed(Input.KEY_C)) {
                    ent1.broadcast("KEY " + Input.KEY_C);
                }
            }
        }

        if (input.isKeyPressed(Input.KEY_E))
            for (Entity ent1: ents) {
                for (Entity ent2: ents) {
                    if (colliding(ent1, ent2)) {
                        if (ent1.hasComponent(Consts.COMBAT) && ent2.hasComponent(Consts.HEALTH)) {
                            CombatComponent combat = (CombatComponent) ent1.getComponent(Consts.COMBAT);
                            ent2.process(new MessageChannel(ent1, combat.attack()));
                        }
                    }
                }
            }
        if (input.isKeyPressed(Input.KEY_A)) {
            for (Entity ent1: ents) {
                if (ent1.hasComponent(Consts.TRANSFORM) && ent1.hasComponent(Consts.INPUT)
                        && ent1.hasComponent(Consts.COMBAT)) {
                    CombatComponent combat = (CombatComponent) (ent1.getComponent(Consts.COMBAT));
                    TransformComponent trans1 = (TransformComponent) (ent1.getComponent(Consts.TRANSFORM));
                    for (Entity ent2: ents) {
                        if (ent2 != ent1 && ent2.hasComponent(Consts.HEALTH) && ent2.hasComponent(Consts.TRANSFORM)) {
                            TransformComponent trans2 = (TransformComponent) (ent2.getComponent(Consts.TRANSFORM));
                            float dx = trans1.getX() - trans2.getX();
                            float dy = trans1.getY() - trans2.getY();
                            float distance = (float) Math.sqrt(dx * dx + dy * dy);
                            if (distance < Consts.TILE_SIZE) {
                                channel = new MessageChannel(ent1, combat.attack());
                                ent2.process(channel);
                            }
                        }
                    }
                }
            }
        }
        if (input.isKeyPressed(Input.KEY_K)) {
            for (Entity ent1: ents) {
                if (ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.SPELL)) {
                    SpellComponent spell = (SpellComponent) (ent1.getComponent(Consts.SPELL));
                    for (Entity ent2: ents) {
                        if (ent2 != ent1 && ent2.hasComponent(Consts.HEALTH)) {
                            channel = new MessageChannel(ent1, spell.fireball());
                            ent2.process(channel);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        // if (!input.isKeyDown(Input.KEY_LSHIFT) &&
        // input.isKeyDown(Input.KEY_2)) {
        // for (Entity ent1: ents) {
        // if (!ent1.hasComponent(Consts.INPUT) &&
        // ent1.hasComponent(Consts.TRANSFORM)
        // && ent1.hasComponent(Consts.SPRITE)) {
        // channel = new MessageChannel(ent1, "rescale .99");
        // ent1.process(channel);
        // }
        // }
        // }
        // if (input.isKeyDown(Input.KEY_LSHIFT) &&
        // input.isKeyDown(Input.KEY_2)) {
        // for (Entity ent1: ents) {
        // if (!ent1.hasComponent(Consts.INPUT) &&
        // ent1.hasComponent(Consts.TRANSFORM)
        // && ent1.hasComponent(Consts.SPRITE)) {
        // channel = new MessageChannel(ent1, "rescale 1.01");
        // ent1.process(channel);
        // }
        // }

        // for (Entity ent1: ents) {
        // for (Entity ent2: ents) {
        // if (ent1 != ent2 && ent1.hasComponent(Consts.COLLISION) &&
        // ent2.hasComponent(Consts.COLLISION)) {
        // ent2.process(new MessageChannel(ent1, "collision 2.0 2.0"));
        // }
        // }
        // }

        input.clearKeyPressedRecord();

        for (Entity ent1: ents) {
            if (ent1.hasComponent(Consts.MANA)) {
                ent1.getComponent(Consts.MANA).update();
            }
            if (ent1.hasComponent(Consts.HEALTH)) {
                ent1.getComponent(Consts.HEALTH).update();
            }

            if (ent1.hasComponent(Consts.SPRITE)) {
                ent1.broadcast("draw");
            }
        }

        for (Entity ent1: ents) {
            if (ent1.hasComponent(Consts.INVENTORY)) {
                ent1.broadcast("drawInv");
            }
        }

        if (player.showInventory) {
            player.inventory.render(g);
        }

        if (player.showMenu) {
            player.menu.render(g);
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

    private boolean colliding(Entity ent1, Entity ent2) {
        if (ent1 == ent2) {
            return false;
        }
        if (ent1.hasComponent(Consts.TRANSFORM) && ent2.hasComponent(Consts.TRANSFORM)) {
            TransformComponent trans1 = (TransformComponent) (ent1.getComponent(Consts.TRANSFORM));
            TransformComponent trans2 = (TransformComponent) (ent2.getComponent(Consts.TRANSFORM));
            float dx = trans1.getX() - trans2.getX();
            float dy = trans1.getY() - trans2.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < Consts.TILE_SIZE) {
                return true;
            }
        }
        return false;
    }

    public void update(int dt) throws SlickException {
        keyHandler.update();
        player.update(dt);

        if (player.showInventory) {
            player.inventory.update();
        }

        if (player.showMenu) {
            player.menu.update();
        }

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        // TODO Auto-generated method stub
        
    }

}
