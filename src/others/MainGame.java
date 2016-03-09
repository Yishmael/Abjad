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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Vector2f;

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
import enums.ItemType;
import enums.PassiveType;
import enums.SpellType;
import spells.Spell;
import spells.ground.BurningGround;
import spells.projectiles.Fireball;
import spells.selfcast.Heal;

public class MainGame extends BasicGame { // add states

    public static int dt = 0;

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Stars are falling"));
            appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, Consts.FULLSCREEN);
            appgc.setTargetFrameRate(2000);
            appgc.setIcon("images/ui/icon.png");
            // appgc.setClearEachFrame(false);
            appgc.setAlwaysRender(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Map map;
    private ArrayList<Entity> ents;
    private Input input;
    private EntityFactory factory;
    private Sound sound;
    private Image iface1;
    private Image[] spellIcons;
    private SpellType currentSpell = SpellType.values()[0];
    private Vector2f direction;
    private Vector2f facing;
    private Button[] buttons;
    private float mouseX, mouseY;
    private long lastTime = 0, lastAttackTime = 0, now = 0;
    private ArrayList<Spell> spells = new ArrayList<Spell>();

    public MainGame(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setMouseCursor(new Image("images/ui/cursor2.png"), 0, 0);
        map = new Map();
        ents = new ArrayList<Entity>();
        factory = new EntityFactory();
        iface1 = new Image("images/ui/iface1.png");
        direction = new Vector2f(1, 0);
        facing = new Vector2f(1, 0);
        buttons = new Button[12];

        initButtons();

        spellIcons = new Image[SpellType.values().length - 1];

        for (int i = 0; i < spellIcons.length; i++) {
            spellIcons[i] = new Image(SpellType.values()[i].getIconPath());
        }

        // TODO make map with coords where enemies should spawn
        Entity entity = new Entity("Player");
        entity.addComponent(
                new SpriteComponent(entity, new SpriteSheet("images/player1.png", 64, 64), 220, false, 64, 64));
        entity.addComponent(new CombatComponent(entity));
        entity.addComponent(new MovementComponent(entity, 2f));
        entity.addComponent(new HealthComponent(entity, 100, 100, 0f));
        entity.addComponent(new ManaComponent(entity, 150, 150, 3.1f));
        ItemType[] inventory = { ItemType.Axe, ItemType.Sword };
        entity.addComponent(new InventoryComponent(entity, "images/ui/inventory2.png", inventory));
        entity.addComponent(new InputComponent(entity));
        entity.addComponent(
                new TransformComponent(entity, Consts.SCREEN_WIDTH / 2 - 32, Consts.SCREEN_HEIGHT / 2 - 32, 1));
        entity.addComponent(new SpellComponent(entity));
        entity.addComponent(new AttributesComponent(entity, 0, 0, 0));
        entity.addComponent(new LevelComponent(entity, 1));
        entity.addComponent(new CollisionComponent(entity));
        ents.add(entity);

        ents.add(factory.getEnemyBlueprint(20, 300));
        ents.add(factory.getEnemyBlueprint(100, 400));
        ents.add(factory.getEnemyBlueprint(250, 400));
        // for (int i = 0; i < 3; i++) {
        // for (int j = 0; j < 3; j++) {
        // ents.add(factory.getEnemyBlueprint(500 + 530 * i, 500 + 730 * j));
        // }
        // }

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

        mouseX = input.getMouseX();
        mouseY = input.getMouseY();

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            for (Button button: buttons) {
                if (button.getShape().contains(mouseX, mouseY)) {
                    System.out.println("Clicked on " + button.getDescription());
                    for (Entity ent: ents) {
                        if (ent.hasComponent(Consts.INPUT)) {
                            ent.broadcast(button.getCommand());
                        }
                    }
                }
            }
            System.out.println(mouseX + ":" + mouseY);
        }

        for (Entity ent1: ents) {
            if (ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.HEALTH)) {
                if (((HealthComponent) ent1.getComponent(Consts.HEALTH)).isAlive()) {
                    float speed = ((MovementComponent) ent1.getComponent(Consts.MOVEMENT)).getSpeed();
                    if (input.isKeyDown(Input.KEY_UP)) {
                        direction.y = -dt / 1000f * speed * Consts.TILE_SIZE;
                        facing.y = -1;
                        facing.x = 0;
                    } else if (input.isKeyDown(Input.KEY_DOWN)) {
                        direction.y = dt / 1000f * speed * Consts.TILE_SIZE;
                        facing.y = 1;
                        facing.x = 0;
                    }
                    if (input.isKeyDown(Input.KEY_LEFT)) {
                        direction.x = -dt / 1000f * speed * Consts.TILE_SIZE;
                        facing.x = -1;
                        facing.y = 0;
                    } else if (input.isKeyDown(Input.KEY_RIGHT)) {
                        direction.x = dt / 1000f * speed * Consts.TILE_SIZE;
                        facing.x = 1;
                        facing.y = 0;
                    }
                    SpriteComponent sprite = (SpriteComponent) ent1.getComponent(Consts.SPRITE);
                    if (sprite.getFacing().getX() != facing.getX() || sprite.getFacing().getY() != facing.getY()) {
                        sprite.setFacing(facing);
                    }
                }
            }
        }
        for (Iterator<Entity> iterator = ents.iterator(); iterator.hasNext();) {
            Entity ent1 = iterator.next();
            if (ent1.hasComponent(Consts.INPUT)) {
                if (input.isKeyPressed(Input.KEY_1)) {
                    ent1.broadcast("KEY " + Input.KEY_1);
                }
                if (input.isKeyPressed(Input.KEY_2)) {
                    ent1.broadcast("KEY " + Input.KEY_2);
                    SpellComponent spell = (SpellComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = spell.getCurrentSpell();
                }
                // TODO separate Minimap from Map
                if (input.isKeyPressed(Input.KEY_Z)) {
                    map.zoomIn();
                }
                if (input.isKeyPressed(Input.KEY_V)) {
                    map.zoomOut();
                }
                if (input.isKeyPressed(Input.KEY_I)) {
                    ent1.broadcast("KEY " + Input.KEY_I);
                }
                if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                    ent1.broadcast("KEY " + Input.KEY_ESCAPE);
                }
                if (input.isKeyPressed(Input.KEY_F9)) {
                    ent1.broadcast("AS 50");
                    ent1.broadcast("DMG 100");
                    ent1.broadcast("MS 100");
                    ent1.broadcast("LS 100");
                    ent1.broadcast("MPcap 500");
                    ent1.broadcast("MPregen 100");
                }
                if (input.isKeyDown(Input.KEY_A)) {
                    if (ent1.hasComponent(Consts.COMBAT)) {
                        CombatComponent combat = (CombatComponent) ent1.getComponent(Consts.COMBAT);
                        now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                        if (now - lastAttackTime >= combat.getCooldownMillis() * 0.99f) {
                            lastAttackTime = now;
                            String action = combat.attack();
                            for (Entity ent2: ents) {
                                if (ent2.hasComponent(Consts.HEALTH)) {
                                    if (colliding(ent1, ent2)) {
                                        boolean aliveBefore = ((HealthComponent) ent2.getComponent(Consts.HEALTH))
                                                .isAlive();
                                        ent2.process(new MessageChannel(ent1, action));
                                        boolean aliveAfter = ((HealthComponent) ent2.getComponent(Consts.HEALTH))
                                                .isAlive();
                                        if (action != null) {
                                            if (aliveBefore && !aliveAfter) {
                                                if (ent2.hasComponent(Consts.LEVEL)) {
                                                    LevelComponent level = (LevelComponent) ent2
                                                            .getComponent(Consts.LEVEL);
                                                    ent1.process(new MessageChannel(ent2,
                                                            "exp " + level.getExperienceBounty()));
                                                }
                                            }
                                            if (aliveBefore) {
                                                ent1.process(new MessageChannel(ent2,
                                                        "heal " + (Float.parseFloat(action.substring(7))
                                                                * combat.getLifesteal())));
                                            }
                                            if (combat.getCleaveRadius() == 0) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (input.isKeyPressed(Input.KEY_E)) {
                    SpellComponent spell = (SpellComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = spell.getCurrentSpell();
                    String action = spell.cast();
                    if (action != null) {
                        sound = new Sound(currentSpell.getSoundPath());
                        sound.play();
                        TransformComponent trans = (TransformComponent) ent1.getComponent(Consts.TRANSFORM);
                        float x = trans.getX();
                        float y = trans.getY();
                        if (currentSpell == SpellType.Nourish) {
                            spells.add(new Heal(new Image("images/spells/nourish.png"), ent1.getName(), new Vector2f(x, y), facing,
                                    currentSpell.getHealing(), 15));
                        } else if (currentSpell == SpellType.Fireball) {
                            spells.add(new Fireball(new Image("images/spells/fireball-1.png"), ent1.getName(), new Vector2f(x, y),
                                    facing, SpellType.Fireball.getSpeed(), SpellType.Fireball.getRange(),
                                    SpellType.Fireball.getDamage(), 16));
                        } else if (currentSpell == SpellType.BurningGround) {
                            spells.add(new BurningGround(new Image("images/spells/burningground.png"), ent1.getName(),
                                    new Vector2f(x, y), facing, 128, 20, 2));
                        }
                    }

                }
            }
        }

        for (Spell spell: spells) {
            spell.offset(direction.negate());
            spell.update();
        }
        for (Iterator<Spell> iter = spells.iterator(); iter.hasNext();) {
            Spell spell = iter.next();
            if (isOutsideScreen(spell) || spell.finished()) {
                iter.remove();
                // System.out.println(spell.toString() + " destroyed");
            }
        }
        for (Spell spell: spells) {
            String action = spell.getMessage();
            for (Entity ent1: ents) {
                if (!spell.getCreator().matches(ent1.getName())) {
                    if (colliding(ent1, spell)) {
                        if (action != null) {
                            ent1.broadcast(action);
                            sound = new Sound(currentSpell.getDeathSoundPath());
                            sound.play();
                            spell.trigger();
                        }
                    }
                }
            }
        }

        if (input.isKeyDown(Input.KEY_0)) {
            ents.get(0).broadcast("exp 150");
        }

        for (Entity ent1: ents) {
            if (!isOutsideScreen(ent1)) {
                if (!ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.COMBAT)) {
                    CombatComponent combat = (CombatComponent) ent1.getComponent(Consts.COMBAT);
                    for (Entity ent2: ents) {
                        if (ent2.hasComponent(Consts.INPUT) && ent2.hasComponent(Consts.HEALTH)) {
                            if (colliding(ent1, ent2)) {
                                ent2.process(new MessageChannel(ent1, combat.attack()));
                            }
                        }
                    }
                }
            }
        }

        input.clearKeyPressedRecord();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        map.drawMap(g, -direction.getX(), -direction.getY());
        for (Entity ent1: ents) {
            for (components.Component comp: ent1.getComponents()) {
                if (direction.length() != 0) {
                    if (!ent1.hasComponent(Consts.INPUT)) {
                        if (comp.getID() == Consts.TRANSFORM) {
                            ((TransformComponent) comp).move(-direction.getX(), -direction.getY());
                        }
                    } else {
                        ((SpriteComponent) ent1.getComponent(Consts.SPRITE)).animateWalk();
                    }
                }
                if (!isOutsideScreen(ent1)) {
                    comp.update();
                } else {
                    if (comp.getID() != Consts.SPRITE) {
                        comp.update();
                    }
                }
            }
        }
        for (Spell spell: spells) {
            spell.render(g);
        }

        g.draw(((TransformComponent) ents.get(0).getComponent(Consts.TRANSFORM)).getShape());

        direction.x = 0;
        direction.y = 0;

        g.drawImage(iface1, Consts.SCREEN_WIDTH - 240, Consts.SCREEN_HEIGHT - 152, 0, 0, 243, 156,
                new Color(255, 255, 255, 200));
        g.drawImage(spellIcons[currentSpell.ordinal()], Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 147,
                Consts.SCREEN_WIDTH - 176, Consts.SCREEN_HEIGHT - 88, 0, 0, 64, 64);

        AttributesComponent attrs = (AttributesComponent) ents.get(0).getComponent(Consts.ATTRIBUTES);
        LevelComponent lvl = (LevelComponent) ents.get(0).getComponent(Consts.LEVEL);

        for (Button button: buttons) {
            button.draw(g);
        }

        for (Button button: buttons) {
            if (button.getShape().contains(mouseX, mouseY)) {
                button.drawDescription(g);
                break;
            }
        }

        buttons[0].draw(g);
        g.drawString("STR: " + (int) attrs.getStrength(), Consts.SCREEN_WIDTH - 140, Consts.SCREEN_HEIGHT - 145);
        buttons[1].draw(g);
        g.drawString("AGI: " + (int) attrs.getAgility(), Consts.SCREEN_WIDTH - 140, Consts.SCREEN_HEIGHT - 123);
        buttons[2].draw(g);
        g.drawString("INT: " + (int) attrs.getIntelligence(), Consts.SCREEN_WIDTH - 140, Consts.SCREEN_HEIGHT - 101);
        g.drawString("Level " + lvl.getLevel(), Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 72);
        g.drawString("Experience " + (long) lvl.getExperience() + "/" + (long) lvl.getMaxExperience(),
                Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 52);
        g.drawRect(Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 32, 100, 10);
        g.fillRect(Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 32,
                100 * (lvl.getExperience() - lvl.getRequiredExperience(lvl.getLevel()))
                        / (lvl.getMaxExperience() - lvl.getRequiredExperience(lvl.getLevel())),
                10);
        g.setColor(Color.white);

        TransformComponent trans = (TransformComponent) (ents.get(0).getComponent(Consts.TRANSFORM));
        g.drawString("(" + (int) trans.getX() + ":" + (int) trans.getY() + ")", Consts.SCREEN_WIDTH - 120, 10);
    }

    private boolean isOutsideScreen(Entity entity) {
        TransformComponent trans = (TransformComponent) entity.getComponent(Consts.TRANSFORM);
        // TODO change +200 to adapt to actual width/height of the image
        if (trans.getX() + 200 < 0 || trans.getX() - 200 > Consts.SCREEN_WIDTH) {
            return true;
        } else if (trans.getY() + 200 < 0 || trans.getY() - 200 > Consts.SCREEN_HEIGHT) {
            return true;
        }
        return false;
    }

    private boolean isOutsideScreen(Spell spell) {
        // TODO change +200 to adapt to actual width/height of the image
        if (spell.getX() + 200 < 0 || spell.getX() - 200 > Consts.SCREEN_WIDTH) {
            return true;
        } else if (spell.getY() + 200 < 0 || spell.getY() - 200 > Consts.SCREEN_HEIGHT) {
            return true;
        }
        return false;
    }

    private boolean colliding(Entity ent1, Entity ent2) {
        if (ent1.equals(ent2)) {
            return false;
        }
        if (ent1.hasComponent(Consts.TRANSFORM) && ent2.hasComponent(Consts.TRANSFORM)) {
            TransformComponent trans1 = (TransformComponent) (ent1.getComponent(Consts.TRANSFORM));
            TransformComponent trans2 = (TransformComponent) (ent2.getComponent(Consts.TRANSFORM));
            float dx = trans1.getCenterX() - trans2.getCenterX();
            float dy = trans1.getCenterY() - trans2.getCenterY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance <= trans1.getRadius() + trans2.getRadius()) {
                if (trans1.getShape().intersects(trans2.getShape())) {
                    return true;
                } else  if (trans1.getShape().contains(trans2.getShape())){
                   return true;
                }
            }
        }
        return false;
    }

    private boolean colliding(Entity ent1, Spell spell) {
        if (ent1.hasComponent(Consts.TRANSFORM)) {
            TransformComponent trans1 = (TransformComponent) (ent1.getComponent(Consts.TRANSFORM));
            float dx = trans1.getCenterX() - spell.getCenterX();
            float dy = trans1.getCenterY() - spell.getCenterY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            // TODO
            if (distance <= trans1.getRadius() + spell.getRadius()) {
                if (trans1.getShape().intersects(spell.getShape())) {
                    return true;
                } else  if (trans1.getShape().contains(spell.getShape())){
                   return true;
                } else if (spell.getShape().contains(trans1.getShape())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initButtons() throws SlickException {

        // primary stats
        for (int i = 0; i < 3; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(new Image(tempType.getIconPath()),
                    new Rectangle(Consts.SCREEN_WIDTH - 170, Consts.SCREEN_HEIGHT - 145 + 22 * i, 20, 20),
                    tempType.getName(), tempType.getCommand());
        }

        // left column passives
        for (int i = 3; i < 6; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new Rectangle(Consts.SCREEN_WIDTH - 230 + 0 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50),
                    tempType.getName(), tempType.getCommand());

        }

        // middle column passives
        for (int i = 6; i < 9; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new Rectangle(Consts.SCREEN_WIDTH - 230 + 1 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50),
                    tempType.getName(), tempType.getCommand());
        }

        // right column passives
        for (int i = 9; i < 12; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + 2 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }
    }

    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastTime);
        lastTime = time;
        return delta;
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
}
