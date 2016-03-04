package others;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import components.AttributesComponent;
import components.CollisionComponent;
import components.CombatComponent;
import components.HealthComponent;
import components.InputComponent;
import components.InventoryComponent;
import components.LevelComponent;
import components.ManaComponent;
import components.MovementComponent;
import components.SpellComponent;
import components.SpriteComponent;
import components.TransformComponent;
import data.KeyHandler;
import data.MapTile;
import data.Player;
import enums.PassiveType;
import enums.SpellType;

public class MainGame extends BasicGame { // add states

    public static int dt = 0;

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Stars are falling"));
            appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, false);
            appgc.setTargetFrameRate(1500);
            appgc.setIcon("images/ui/icon.png");
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
    private ArrayList<Entity> ents;
    private Input input;
    private EntityFactory factory;
    private ArrayList<Entity> entityQueue;
    private SpellType currentSpell = SpellType.Fireball;
    private Sound sound;

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
        gc.setMouseCursor(new Image("images/ui/cursor.png"), 0, 0);
        mapTile = new MapTile(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
        player = new Player(new Image("images/ifrit.png"), "Ifrit", Consts.SCREEN_WIDTH / 2, Consts.SCREEN_HEIGHT / 2,
                100, 200f, mapTile);
        keyHandler = new KeyHandler(gc, player);
        ents = new ArrayList<Entity>();
        factory = new EntityFactory();
        entityQueue = new ArrayList<Entity>();
        Entity entity = new Entity("Player");
        entity.addComponent(new SpriteComponent(entity, new SpriteSheet("images/player1.png", 64, 64), 220, false));
        entity.addComponent(new CombatComponent(entity, 25, 0.75f));
        entity.addComponent(new MovementComponent(entity, 2.2f));
        entity.addComponent(new HealthComponent(entity, 300, 300, 3.2f));
        entity.addComponent(new ManaComponent(entity, 150, 150, 9.1f));
        ItemType[] inventory = { ItemType.Sword, ItemType.Axe };
        entity.addComponent(new InventoryComponent(entity, "images/ui/inventory2.png", inventory));
        entity.addComponent(new InputComponent(entity));
        entity.addComponent(new TransformComponent(entity, 220, 200));
        entity.addComponent(new SpellComponent(entity, 75, 40, 1));
        entity.addComponent(new AttributesComponent(entity, 0, 0, 0));
        entity.addComponent(new LevelComponent(entity, 1));
        entity.addComponent(new CollisionComponent(entity));
        ents.add(entity);

        ents.add(factory.getEnemyBlueprint(20, 300));
        ents.add(factory.getEnemyBlueprint(100, 400));
        ents.add(factory.getEnemyBlueprint(250, 400));
        ents.add(factory.getEnemyBlueprint(300, 100));
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

        for (Iterator<Entity> iterator = ents.iterator(); iterator.hasNext();) {
            Entity ent1 = (Entity) iterator.next();
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
                    SpellComponent spell = (SpellComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = spell.getCurrentSpell();
                }
                if (input.isKeyPressed(Input.KEY_3)) {
                    ent1.broadcast("KEY " + Input.KEY_3);
                }
                if (input.isKeyPressed(Input.KEY_ENTER)) {
                    ent1.broadcast("KEY " + Input.KEY_ENTER);
                }
                if (input.isKeyPressed(Input.KEY_A)) {
                    if (ent1.hasComponent(Consts.COMBAT)) {
                        CombatComponent combat = (CombatComponent) ent1.getComponent(Consts.COMBAT);
                        String action = combat.attack();
                        for (Entity ent2: ents) {
                            if (ent2.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2, Consts.TILE_SIZE)) {
                                    ent2.process(new MessageChannel(ent1, action));
                                    HealthComponent health = (HealthComponent) ent2.getComponent(Consts.HEALTH);
                                    if (!health.isAlive() && action != null) {
                                        if (ent2.hasComponent(Consts.LEVEL)) {
                                            LevelComponent level = (LevelComponent) ent2.getComponent(Consts.LEVEL);
                                            ent1.process(
                                                    new MessageChannel(ent2, "exp " + level.getExperienceBounty()));
                                        }

                                    } else if (health.isAlive() && action != null) {
                                        ent1.process(new MessageChannel(ent2,
                                                "heal " + (Float.parseFloat(action.substring(7)) * 0.33f)));
                                    }
                                }
                            }
                        }
                    }
                }
                if (input.isKeyPressed(Input.KEY_E)) {
                    if (ent1.hasComponent(Consts.SPELL)) {
                        SpellComponent spell = (SpellComponent) (ent1.getComponent(Consts.SPELL));
                        currentSpell = spell.getCurrentSpell();
                        String action = spell.cast();
                        if (action != null) {
                            if (currentSpell == SpellType.Explosion) {
                                sound = new Sound("sounds/aa.ogg");
                                sound.play();
                            } else if (currentSpell == SpellType.Nourish) {
                                sound = new Sound("sounds/049.wav");
                                sound.play();
                            } else if (currentSpell == SpellType.Fireball) {
                                sound = new Sound("sounds/067.wav");
                                sound.play();
                            }

                            if (currentSpell != SpellType.Fireball) {
                                for (Iterator<Entity> iter = ents.iterator(); iter.hasNext();) {
                                    Entity ent2 = iter.next();
                                    if (ent2.hasComponent(Consts.HEALTH)) {
                                        if (colliding(ent1, ent2, Consts.TILE_SIZE + 25)) {
                                            ent2.process(new MessageChannel(ent1, action));
                                        }
                                    }
                                }
                            } else {
                                TransformComponent trans = (TransformComponent) ent1.getComponent(Consts.TRANSFORM);
                                float x = trans.getX();
                                float y = trans.getY();
                                Entity entity = new Entity("Fireball");
                                entity.addComponent(new SpriteComponent(entity,
                                        new SpriteSheet("images/spells/fireball.png", 82, 83), 80, false));
                                entity.addComponent(new TransformComponent(entity, x + 26, y - 15));
                                entityQueue.add(entity);
                            }
                        }
                    }
                }
            }
        }

        for (Iterator<Entity> iterator = entityQueue.iterator(); iterator.hasNext();) {
            Entity entity = (Entity) iterator.next();
            ents.add(entity);
            iterator.remove();
        }

        for (int i = 0; i < ents.size(); i++) {
            if (ents.get(i).getName() == "Fireball") {
                for (Entity ent2: ents) {
                    if (colliding(ents.get(i), ent2, 25)) {
                        ent2.process(new MessageChannel(ents.get(i), "damage 80"));
                        sound = new Sound("sounds/068.wav");
                        sound.play();
                        ents.remove(ents.remove(i));
                        break;
                    }
                }
            }
        }

        if (input.isKeyDown(Input.KEY_0)) {
            ents.get(0).broadcast("KEY " + Input.KEY_ENTER);
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

        input.clearKeyPressedRecord();
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

        for (Entity ent: ents) {
            if (ent.getName() == "Fireball") {
                TransformComponent trans = (TransformComponent) ent.getComponent(Consts.TRANSFORM);
                trans.move(5.5f * dt / 1000 * Consts.TILE_SIZE, 0);
                if (trans.getX() > Consts.SCREEN_WIDTH) {
                    ents.remove(ent);
                    break;
                }
            }
        }

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
            for (components.Component comp: ent1.getComponents()) {
                comp.update();
            }
        }
        player.render(g);
        g.drawString("Sup, dawg?", player.getxPos(), player.getyPos() - 20);

        g.drawImage(new Image("images/ui/iface1.png"), 400, 360, 0, 0, 243, 156, new Color(255, 255, 255, 200));
        g.drawImage(new Image(currentSpell.getIconPath()), 405, 365, 464, 424, 0, 0, 64, 64);

        AttributesComponent attrs = (AttributesComponent) ents.get(0).getComponent(Consts.ATTRIBUTES);
        LevelComponent lvl = (LevelComponent) ents.get(0).getComponent(Consts.LEVEL);

        g.drawImage(new Image("images/skilltree.png"), Consts.SCREEN_WIDTH - 256, 50, Consts.SCREEN_WIDTH, 370, 0, 0,
                256, 320);
        
        g.drawImage(new Image(PassiveType.Strength.getIconPath()), 470, 367, 490, 387, 0, 0, 64, 64);
        g.drawString("STR: " + (int) attrs.getStrength(), 500, 367);
        g.drawImage(new Image(PassiveType.Agility.getIconPath()), 470, 389, 490, 409, 0, 0, 64, 64);
        g.drawString("AGI: " + (int) attrs.getAgility(), 500, 389);
        g.drawImage(new Image(PassiveType.Intelligence.getIconPath()), 470, 411, 490, 431, 0, 0, 64, 64);
        g.drawString("INT: " + (int) attrs.getIntelligence(), 500, 411);
        g.drawString("Level " + lvl.getLevel(), 405, 440);
        g.drawString("Experience " + (long) lvl.getExperience() + "/" + (long) lvl.getMaxExperience(), 405, 460);
        g.drawRect(405, 480, 100, 10);
        g.fillRect(405, 480, 100 * (lvl.getExperience() - lvl.getRequiredExperience(lvl.getLevel()))
                / (lvl.getMaxExperience() - lvl.getRequiredExperience(lvl.getLevel())), 10);
        g.setColor(Color.white);

        if (player.showInventory) {
            player.inventory.render(g);
        }

        if (player.showMenu) {
            player.menu.render(g);
        }

        TransformComponent trans = (TransformComponent) (ents.get(0).getComponent(Consts.TRANSFORM));
        g.drawString("(" + (int) trans.getX() + ":" + (int) trans.getY() + ")", Consts.SCREEN_WIDTH - 100, 10);
    }

}
