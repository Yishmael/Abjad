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

import components.AttributesComponent;
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
import net.java.games.input.Keyboard;

public class MainGame extends BasicGame { // add states

    public static int dt = 0;

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Stars are falling"));
            appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, false);
            appgc.setTargetFrameRate(1500);
            appgc.setIcon("images/icon.png");
            // appgc.setClearEachFrame(false);
            // appgc.setFullscreen(true);
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
    private EntityFactory factory;

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
        factory = new EntityFactory();

        Entity player1 = new Entity("Player");
        player1.addComponent(new SpriteComponent(player1, new SpriteSheet("images/player1.png", 64, 64), 220, false));
        player1.addComponent(new CombatComponent(player1, 25, 0.75f));
        player1.addComponent(new MovementComponent(player1, 2.2f));
        player1.addComponent(new HealthComponent(player1, 300, 300, 3.2f));
        player1.addComponent(new ManaComponent(player1, 150, 150, 9.1f));
        ItemType[] inventory = { ItemType.Axe, ItemType.Sword };
        player1.addComponent(new InventoryComponent(player1, "images/inventory1.png", inventory));
        player1.addComponent(new InputComponent(player1));
        player1.addComponent(new TransformComponent(player1, 220, 200));
        player1.addComponent(new SpellComponent(player1, 75, 40, 1));
        player1.addComponent(new AttributesComponent(player1, 0, 0, 0));
        player1.addComponent(new CollisionComponent(player1));
        ents.add(player1);

        ents.add(factory.getEnemyBlueprint());
        ents.add(factory.getBarrelBlueprint());
        ents.add(factory.getBarrelBlueprint());
        ents.add(factory.getBarrelBlueprint());
        ents.add(factory.getBarrelBlueprint());
        ents.add(factory.getBarrelBlueprint());
        ents.add(factory.getCampfireBlueprint());
        ents.add(factory.getBirdBlueprint());
    }

    @Override
    public void update(GameContainer gc, int unusedDelta) throws SlickException {
        input = gc.getInput();

        dt = getDelta();

        if (dt > 25) {
            dt = 25;
        }
        update(dt);

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
                if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                    ent1.broadcast("KEY " + Input.KEY_ESCAPE);
                }
                if (input.isKeyPressed(Input.KEY_1)) {
                    ent1.broadcast("KEY " + Input.KEY_1);
                }
                if (input.isKeyPressed(Input.KEY_2)) {
                    ent1.broadcast("KEY " + Input.KEY_2);
                }
                if (input.isKeyPressed(Input.KEY_3)) {
                    ent1.broadcast("KEY " + Input.KEY_3);
                }
                if (input.isKeyPressed(Input.KEY_4)) {
                    ent1.broadcast("KEY " + Input.KEY_4);
                }
                if (input.isKeyPressed(Input.KEY_A)) {
                    if (ent1.hasComponent(Consts.COMBAT)) {
                        CombatComponent combat = (CombatComponent) ent1.getComponent(Consts.COMBAT);
                        String action = combat.attack();
                        for (Entity ent2: ents) {
                            if (ent2.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2, Consts.TILE_SIZE)) {
                                    ent2.process(new MessageChannel(ent1, action));
                                }
                            }
                        }
                    }
                }
                if (input.isKeyPressed(Input.KEY_E)) {
                    if (ent1.hasComponent(Consts.SPELL)) {
                        SpellComponent spell = (SpellComponent) (ent1.getComponent(Consts.SPELL));
                        String action = spell.cast();
                        for (Entity ent2: ents) {
                            if (ent2.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2, Consts.TILE_SIZE + 25)) {
                                    ent2.process(new MessageChannel(ent1, action));
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Entity ent1: ents) {
            if (!ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.COMBAT)) {
                CombatComponent combat = (CombatComponent) ent1.getComponent(Consts.COMBAT);
                for (Entity ent2: ents) {
                    if (ent2.hasComponent(Consts.INPUT) && ent2.hasComponent(Consts.HEALTH)) {
                        if (colliding(ent1, ent2, Consts.TILE_SIZE)) {
                            ent2.process(new MessageChannel(ent1, combat.attack()));
                        }
                    }
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

    private boolean colliding(Entity ent1, Entity ent2, int treshold) {
        if (ent1.equals(ent2)) {
            return false;
        }
        if (ent1.hasComponent(Consts.TRANSFORM) && ent2.hasComponent(Consts.TRANSFORM)) {
            TransformComponent trans1 = (TransformComponent) (ent1.getComponent(Consts.TRANSFORM));
            TransformComponent trans2 = (TransformComponent) (ent2.getComponent(Consts.TRANSFORM));
            float dx = trans1.getX() - trans2.getX();
            float dy = trans1.getY() - trans2.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance <= treshold) {
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

        g.setBackground(Color.black);
        mapTile.render(g);

        for (Entity ent1: ents) {
            if (ent1.hasComponent(Consts.MANA)) {
                ent1.getComponent(Consts.MANA).update();
            }
            if (ent1.hasComponent(Consts.HEALTH)) {
                ent1.getComponent(Consts.HEALTH).update();
            }
            if (ent1.hasComponent(Consts.SPRITE)) {
                SpriteComponent sprite = (SpriteComponent) ent1.getComponent(Consts.SPRITE);
                sprite.draw();
                // ent1.broadcast("draw");
            }
            if (ent1.hasComponent(Consts.INVENTORY)) {
                InventoryComponent inv = (InventoryComponent) ent1.getComponent(Consts.INVENTORY);
                inv.receive("drawInv");
            }
        }
        player.render(g);
        g.drawString("Sup, dawg?", player.getxPos(), player.getyPos() - 20);

        if (player.showInventory) {
            player.inventory.render(g);
        }

        if (player.showMenu) {
            player.menu.render(g);
        }

        TransformComponent trans = (TransformComponent) (ents.get(0).getComponent(Consts.TRANSFORM));
        g.drawString("(" + (int) trans.getX() + ":" + (int) trans.getY() + ")", Consts.SCREEN_WIDTH - 150,
                Consts.SCREEN_HEIGHT - 50);
    }

}
