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
import components.CameraComponent;
import components.CollisionComponent;
import components.CombatComponent;
import components.Component;
import components.ComputerMovementComponent;
import components.HealthComponent;
import components.InputComponent;
import components.InventoryComponent;
import components.LevelComponent;
import components.ManaComponent;
import components.PlayerMovementCompenent;
import components.SpellComponent;
import components.SpriteComponent;
import components.TransformComponent;
import components.YieldComponent;
import components.spells.GuideComponent;
import components.spells.ImpactComponent;
import components.spells.SplitComponent;
import enums.ItemType;
import enums.PassiveType;
import enums.SpellType;
import spells.Spell;
import spells.ground.Decrepify;
import spells.ground.ScorchedEarth;
import spells.ground.Weaken;
import spells.projectile.Bouncer;
import spells.projectile.Fireball;
import spells.projectile.PoisonArrow;
import spells.selfcast.Heal;
import spells.selfcast.HolyShield;
import spells.summon.SummonKirith;

public class MainGame extends BasicGame { // add states

    public static int dt = 0;

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Stars are falling"));
            appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, Consts.FULLSCREEN);
            appgc.setTargetFrameRate(Consts.FPS);
            appgc.setIcon("images/ui/icon.png");
            // appgc.setClearEachFrame(false);
            appgc.setAlwaysRender(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Entity> friendlyEnts = new ArrayList<Entity>();
    private ArrayList<Entity> friendlyEntsQueue = new ArrayList<Entity>();
    private ArrayList<Spell> friendlySpells = new ArrayList<Spell>();

    private ArrayList<Entity> cospells = new ArrayList<Entity>();
    private ArrayList<Entity> cospellsQueue = new ArrayList<Entity>();

    private ArrayList<Entity> enemyEnts = new ArrayList<Entity>();
    private ArrayList<Entity> enemyEntsQueue = new ArrayList<Entity>();
    private ArrayList<Spell> enemySpells = new ArrayList<Spell>();

    private ArrayList<Entity> itemDrops = new ArrayList<Entity>();

    private Map map;
    private Input input;
    private EntityFactory factory;
    private Sound sound;
    private Image iface1;
    private Image[] spellIcons;
    private SpellType currentSpell = SpellType.Null;
    private Vector2f direction;
    private Vector2f facing;
    private Button[] buttons;
    private float mouseX, mouseY;
    private Vector2f playerPos = new Vector2f(Consts.SCREEN_WIDTH / 2 - 32, Consts.SCREEN_HEIGHT / 2 - 32);
    private long lastTime = 0;
    private float decayTime = 2.6f;

    public MainGame(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setMouseCursor(new Image("images/ui/cursor2.png"), 0, 0);
        map = new Map();
        factory = new EntityFactory();
        iface1 = new Image("images/ui/iface1.png");
        direction = new Vector2f(1, 0);
        facing = new Vector2f(1, 0);
        buttons = new Button[12];

        initButtons();

        spellIcons = new Image[SpellType.values().length];

        for (int i = 0; i < spellIcons.length; i++) {
            spellIcons[i] = new Image(SpellType.values()[i].getIconPath());
        }

        // TODO make map with coords where enemies should spawn
        Entity entity = new Entity("Player");
        entity.addComponent(
                new SpriteComponent(entity, new SpriteSheet("images/player1.png", 64, 64), 220, false, 64, 64));
        entity.addComponent(new CombatComponent(entity));
        entity.addComponent(new PlayerMovementCompenent(entity, 2f));
        entity.addComponent(new HealthComponent(entity, 100, 100, 0.34f));
        entity.addComponent(new ManaComponent(entity, 150, 150, 3.1f));
        ItemType[] inventory = { ItemType.Axe, ItemType.Sword };
        entity.addComponent(new InventoryComponent(entity, "images/ui/inventory2.png", inventory));
        entity.addComponent(new InputComponent(entity));
        entity.addComponent(new TransformComponent(entity, playerPos.getX(), playerPos.getY(), 60, 60));
        SpellType[] spells = SpellType.values();
        currentSpell = spells[0];
        entity.addComponent(new SpellComponent(entity, spells));
        entity.addComponent(new AttributesComponent(entity, 0, 0, 0));
        entity.addComponent(new LevelComponent(entity, 1));
        entity.addComponent(new CollisionComponent(entity));
        entity.addComponent(new CameraComponent(entity));
        friendlyEnts.add(entity);

        enemyEnts.add(factory.getEnemyBlueprint1(20, 20));
        enemyEnts.add(factory.getEnemyBlueprint1(30, 225));
        enemyEnts.add(factory.getEnemyBlueprint1(30, 450));
        enemyEnts.add(factory.getEnemyBlueprint1(170, 30));
        enemyEnts.add(factory.getEnemyBlueprint1(15, 350));
        enemyEnts.add(factory.getEnemyBlueprint1(40, 110));
        enemyEnts.add(factory.getEnemyBlueprint1(500, 110));
        enemyEnts.add(factory.getEnemyBlueprint2(280, 40));
        // for (int i = 0; i < 7; i++) {
        // for (int j = 0; j < 5; j++) {
        // enemyEnts.add(factory.getEnemyBlueprint(500 + i * 3 * 64, 400 + j * 3
        // *
        // 64));
        // }
        // }

        enemyEnts.add(factory.getBarrelBlueprint());
        enemyEnts.add(factory.getBarrelBlueprint());
        enemyEnts.add(factory.getBarrelBlueprint());
        enemyEnts.add(factory.getCampfireBlueprint());
        enemyEnts.add(factory.getBirdBlueprint());
    }

    @Override
    public void update(GameContainer gc, int unusedDelta) throws SlickException {
        input = gc.getInput();

        dt = getDelta();

        if (dt > 20) {
            dt = 20;
        }

        mouseX = input.getMouseX();
        mouseY = input.getMouseY();

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            for (Button button: buttons) {
                if (button.contains(mouseX, mouseY)) {
                    System.out.println("Clicked on " + button.getDescription());
                    for (Entity ent: friendlyEnts) {
                        if (ent.hasComponent(Consts.INPUT)) {
                            ent.broadcast(button.getCommand());
                        }
                    }
                }
            }
            System.out.println(mouseX + ":" + mouseY);
        }

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            float angle = (float) Math.atan2(mouseY - playerPos.getY(), mouseX - playerPos.getX());
            if (((PlayerMovementCompenent) friendlyEnts.get(0).getComponent(Consts.PLAYERMOVEMENT)).canMove()) {
                float speed = ((PlayerMovementCompenent) friendlyEnts.get(0).getComponent(Consts.PLAYERMOVEMENT))
                        .getSpeed();
                direction.x = (float) (speed * dt / 1000f * (direction.getX() + Math.cos(angle)) * Consts.TILE_SIZE);
                direction.y = (float) (speed * dt / 1000f * (direction.getY() + Math.sin(angle)) * Consts.TILE_SIZE);
            }
        }

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            gc.exit();
        }
        // TODO separate Minimap from Map
        if (input.isKeyPressed(Input.KEY_Z)) {
            map.zoomIn();
        }
        if (input.isKeyPressed(Input.KEY_V)) {
            map.zoomOut();
        }

        for (Entity ent1: friendlyEnts) {
            if (ent1.hasComponent(Consts.INPUT)) {
                if (((PlayerMovementCompenent) ent1.getComponent(Consts.PLAYERMOVEMENT)).canMove()) {
                    float speed = ((PlayerMovementCompenent) ent1.getComponent(Consts.PLAYERMOVEMENT)).getSpeed();
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
        for (Iterator<Entity> iterator = friendlyEnts.iterator(); iterator.hasNext();) {
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
                if (input.isKeyPressed(Input.KEY_I)) {
                    ent1.broadcast("KEY " + Input.KEY_I);
                }
                if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                    ent1.broadcast("KEY " + Input.KEY_ESCAPE);
                }
                if (input.isKeyPressed(Input.KEY_F9)) {
                    ent1.broadcast("AS 50%");
                    ent1.broadcast("DMG 100%");
                    ent1.broadcast("MS 200%");
                    ent1.broadcast("LS 100%");
                    ent1.broadcast("MPcap 500");
                    ent1.broadcast("MPregen 100");
                }
                if (input.isKeyPressed(Input.KEY_R)) {
                    ent1.broadcast("ress");
                }
                if (input.isKeyDown(Input.KEY_A)) {
                    CombatComponent combat = (CombatComponent) ent1.getComponent(Consts.COMBAT);
                    if (combat.isReady()) {
                        String action = combat.attack();
                        for (Entity ent2: enemyEnts) {
                            if (ent2.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2) || distance(ent1, ent2) <= 64 + 32 * combat.getRangeAdder()) {
                                    boolean aliveBefore = ((HealthComponent) ent2.getComponent(Consts.HEALTH))
                                            .isAlive();
                                    ent2.process(new MessageChannel(ent1, action));
                                    boolean aliveAfter = ((HealthComponent) ent2.getComponent(Consts.HEALTH)).isAlive();
                                    if (action != null) {
                                        if (aliveBefore && !aliveAfter) {
                                            if (ent2.hasComponent(Consts.LEVEL)) {
                                                LevelComponent level = (LevelComponent) ent2.getComponent(Consts.LEVEL);
                                                ent1.process(
                                                        new MessageChannel(ent2, "exp " + level.getExperienceBounty()));
                                            }
                                        }
                                        if (aliveBefore) {
                                            ent1.process(new MessageChannel(ent2, "heal "
                                                    + (Float.parseFloat(action.substring(7)) * combat.getLifesteal())));
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
                if (input.isKeyDown(Input.KEY_E)) {
                    SpellComponent spell = (SpellComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = spell.getCurrentSpell();
                    if (spell.isReady()) {
                        String action = spell.cast();
                        if (action != null) {
                            sound = new Sound(currentSpell.getSoundPath());
                            sound.play();
                            float angle = (float) Math.atan2(mouseY - playerPos.getY() - 32,
                                    mouseX - playerPos.getX() - 32);

                            switch (currentSpell) {
                            case CoFireball:
                                Entity entity1 = new Entity("CoFireball");
                                entity1.addComponent(
                                        new SpriteComponent(entity1, "images/spells/cofireball-1.png", 64, 64));
                                entity1.addComponent(new GuideComponent(entity1, 5.5f, angle));
                                entity1.addComponent(new ImpactComponent(entity1));
                                entity1.addComponent(new SplitComponent(entity1, 3, (float) (Math.PI / 2 - angle)));
                                entity1.addComponent(new TransformComponent(entity1, playerPos.getX() + 32,
                                        playerPos.getY() + 32, 64, 64));
                                cospells.add(entity1);
                                break;
                            case Fireball:
                                friendlySpells.add(new Fireball(new Image(currentSpell.getImagePath()), "enemy",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), angle,
                                        SpellType.Fireball.getSpeed(), SpellType.Fireball.getRange(), spell.getDamage(),
                                        16));
                                break;
                            case HolyShield:
                                friendlySpells.add(new HolyShield(new Image(currentSpell.getImagePath()), "nothing",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), 0, 30, 5));
                                break;
                            case Nourish:
                                friendlySpells.add(new Heal(new Image(currentSpell.getImagePath()), "friend",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), angle,
                                        spell.getHealing(), 15));
                                break;
                            case ScorchedEarth:
                                friendlySpells.add(new ScorchedEarth(new Image(currentSpell.getImagePath()), "enemy",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), angle, 128,
                                        spell.getDamage(), 1.5f));
                                break;
                            case Weaken:
                                friendlySpells.add(new Weaken(new Image(currentSpell.getImagePath()), "enemy",
                                        new Vector2f(mouseX, mouseY), angle, 128, 0.5f));
                                break;
                            case Decrepify:
                                friendlySpells.add(new Decrepify(new Image(currentSpell.getImagePath()), "enemy",
                                        new Vector2f(mouseX, mouseY), angle, 128, 0.5f));
                                break;
                            case PoisonArrow:
                                friendlySpells.add(new PoisonArrow(new Image(currentSpell.getImagePath()), "enemy",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), angle,
                                        SpellType.PoisonArrow.getSpeed(), SpellType.PoisonArrow.getRange(),
                                        spell.getDamage(), 0));
                                break;
                            case Bouncer:
                                friendlySpells.add(new Bouncer(new Image(currentSpell.getImagePath()), "enemy",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), angle,
                                        SpellType.Bouncer.getSpeed(), SpellType.Bouncer.getRange(), spell.getDamage(),
                                        15));
                                break;
                            case MultiBouncer:
                                int count = 5;
                                for (int i = 0; i < count; i++) {
                                    friendlySpells.add(new Bouncer(new Image(SpellType.Bouncer.getImagePath()), "enemy",
                                            new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32),
                                            (float) (2 * Math.PI * i / count), SpellType.Bouncer.getSpeed(),
                                            SpellType.Bouncer.getRange() / count, spell.getDamage(), 15));
                                }
                                break;
                            case SummonKirith:
                                // remove any previous summons
                                friendlySpells.add(new SummonKirith(new Image(currentSpell.getIconPath()),
                                        ent1.getName(), playerPos, angle, 50, 0.5f));
                                Entity entity = new Entity("Kirith");
                                entity.addComponent(new SpriteComponent(entity, "images/kirith.png", 64, 64));
                                // dies too soon?!
                                entity.addComponent(new HealthComponent(entity, 9000, 9000, -900));
                                SpellType[] spells = { SpellType.MultiBouncer };
                                entity.addComponent(new SpellComponent(entity, spells));
                                entity.addComponent(new ManaComponent(entity, 200, 200));
                                entity.addComponent(new ComputerMovementComponent(entity, 0.5f));
                                entity.addComponent(new CombatComponent(entity, currentSpell.getDamage(), 0.7f));
                                entity.addComponent(new TransformComponent(entity, mouseX - 32, mouseY - 32, 64, 64));
                                friendlyEntsQueue.add(entity);
                            default:
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (Iterator<Entity> iter = cospells.iterator(); iter.hasNext();) {
            Entity cospell = (Entity) iter.next();
            if (isOutsideScreen(cospell)) {
                cospell.broadcast("impact");
            }
            for (Component comp: cospell.getComponents()) {
                comp.update();
            }
            if (cospell.hasComponent(Consts.SPLIT)) {
                SplitComponent split = ((SplitComponent) cospell.getComponent(Consts.SPLIT));
                if (split.isSplitting()) {
                    for (int i = 0; i < split.getPiecesCount(); i++) {
                        cospellsQueue.add(split.split());

                        friendlySpells.add(new Heal(new Image(SpellType.Nourish.getImagePath()), "friend",
                                new Vector2f((float) (Math.random() * 600), (float) (Math.random() * 600)), 0, 30, 15));
                    }
                    iter.remove();
                }
            }
        }
        for (Iterator<Entity> iter = cospellsQueue.iterator(); iter.hasNext();) {
            Entity entity = (Entity) iter.next();
            cospells.add(entity);
            iter.remove();
        }
        for (Iterator<Entity> iter = friendlyEntsQueue.iterator(); iter.hasNext();) {
            Entity entity = (Entity) iter.next();
            friendlyEnts.add(entity);
            iter.remove();
        }
        for (Iterator<Entity> iter = enemyEntsQueue.iterator(); iter.hasNext();) {
            Entity entity = (Entity) iter.next();
            enemyEnts.add(entity);
            iter.remove();
        }
        for (Spell spell: friendlySpells) {
            spell.offset(direction.negate());
            spell.update();
        }
        for (Spell spell: enemySpells) {
            spell.offset(direction.negate());
            spell.update();
        }
        for (Iterator<Spell> iter = friendlySpells.iterator(); iter.hasNext();) {
            Spell spell = iter.next();
            if (isOutsideScreen(spell) || spell.finished()) {
                iter.remove();
                // System.out.println(spell.toString() + " destroyed");
            }
        }
        for (Iterator<Spell> iter = enemySpells.iterator(); iter.hasNext();) {
            Spell spell = iter.next();
            if (isOutsideScreen(spell) || spell.finished()) {
                iter.remove();
                // System.out.println(spell.toString() + " destroyed");
            }
        }
        for (Spell spell: friendlySpells) {
            if (spell.getTargets().contains("friend")) {
                for (Entity ent1: friendlyEnts) {
                    if (colliding(ent1, spell)) {
                        String action = spell.getMessage();
                        if (action != null) {
                            ent1.broadcast(action);
                            // TODO add spell sound in classes
                            spell.trigger();
                        }
                    }
                }
            }
            if (spell.getTargets().contains("enemy")) {
                for (Entity ent1: enemyEnts) {
                    if (colliding(ent1, spell)) {
                        String action = spell.getMessage();
                        if (action != null) {
                            ent1.broadcast(action);
                            spell.trigger();
                        }
                    }
                }
            }
        }
        for (Spell spell: enemySpells) {
            if (spell.getTargets().contains("friend")) {
                for (Entity ent1: friendlyEnts) {
                    if (colliding(ent1, spell)) {
                        String action = spell.getMessage();
                        if (action != null) {
                            ent1.broadcast(action);
                            spell.trigger();
                        }
                    }
                }
            }
            if (spell.getTargets().contains("enemy")) {
                for (Entity ent1: enemyEnts) {
                    if (colliding(ent1, spell)) {
                        String action = spell.getMessage();
                        if (action != null) {
                            ent1.broadcast(action);
                            spell.trigger();
                        }
                    }
                }
            }
        }
        if (input.isKeyPressed(Input.KEY_0)) {
            friendlyEnts.get(0).broadcast("exp 500000");
        }
        for (Entity ent1: enemyEnts) {
            if (!isOutsideScreen(ent1)) {
                for (Entity ent2: friendlyEnts) {
                    if (!isOutsideScreen(ent2)) {
                        if (ent1.hasComponent(Consts.COMBAT) && !ent1.hasComponent(Consts.INPUT)) {
                            if (ent2.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2)) {
                                    CombatComponent combat = (CombatComponent) ent1.getComponent(Consts.COMBAT);
                                    ent2.process(new MessageChannel(ent1, combat.attack()));

                                }
                            }
                        }
                        if (ent2.hasComponent(Consts.COMBAT) && !ent2.hasComponent(Consts.INPUT)) {
                            if (ent1.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2)) {
                                    CombatComponent combat = (CombatComponent) ent2.getComponent(Consts.COMBAT);
                                    ent1.process(new MessageChannel(ent2, combat.attack()));

                                }
                            }
                        }
                    }
                }
            }
        }
        for (Entity ent1: enemyEnts) {
            if (!isOutsideScreen(ent1)) {
                if (ent1.hasComponent(Consts.SPELL)) {
                    TransformComponent trans = (TransformComponent) ent1.getComponent(Consts.TRANSFORM);
                    float x = trans.getCenterX();
                    float y = trans.getCenterY();
                    SpellComponent spell = (SpellComponent) ent1.getComponent(Consts.SPELL);
                    float dx = x - playerPos.getX();
                    float dy = y - playerPos.getY();
                    if (Math.sqrt(dx * dx + dy * dy) < 150) {
                        if (spell.getCurrentSpell() != SpellType.Null
                                && spell.getCurrentSpell() != SpellType.SummonKirith
                                && spell.getCurrentSpell() != SpellType.Fireball) {
                            if (spell.isReady()) {
                                String action = spell.cast();
                                if (action != null) {
                                    Sound sound = new Sound(spell.getCurrentSpell().getSoundPath());
                                    sound.play();
                                    float angle = (float) Math.atan2(playerPos.getY() + 32 - y, playerPos.getX() + 32 - x);
                                    switch (spell.getCurrentSpell()) {
                                    case PoisonArrow:
                                        enemySpells.add(new PoisonArrow(new Image(SpellType.PoisonArrow.getImagePath()),
                                                "friend", new Vector2f(x, y), angle, SpellType.PoisonArrow.getSpeed(),
                                                SpellType.PoisonArrow.getRange(), SpellType.PoisonArrow.getDamage(),
                                                0));
                                        break;
                                    case Bouncer:
                                        enemySpells.add(new Bouncer(new Image(SpellType.Bouncer.getImagePath()),
                                                "friend", new Vector2f(x + 30, y), angle, SpellType.Bouncer.getSpeed(),
                                                SpellType.Bouncer.getRange(), spell.getDamage(), 15));
                                        break;
                                    case ScorchedEarth:
                                        enemySpells.add(
                                                new ScorchedEarth(new Image(SpellType.ScorchedEarth.getImagePath()),
                                                        "friend", new Vector2f(x, y), 0, 128, spell.getDamage(), 1.5f));
                                        break;
                                    case Nourish:
                                        enemySpells.add(new Heal(new Image(SpellType.Nourish.getImagePath()), "enemy",
                                                new Vector2f(x, y), 0, spell.getHealing(), 15));
                                        break;
                                    default:
                                        break;
                                    }
                                    spell.receive("next spell");
                                }
                            }
                        } else {
                            spell.receive("next spell");
                        }
                    }
                }
            }
        }
        // TODO optimize this slow loop
        for (Iterator<Entity> iter = enemyEnts.iterator(); iter.hasNext();) {
            Entity entity = (Entity) iter.next();
            if (entity.hasComponent(Consts.HEALTH)) {
                HealthComponent health = ((HealthComponent) entity.getComponent(Consts.HEALTH));
                if (!health.isAlive()) {
                    if (getTime() - health.getTimeOfDeath() >= decayTime * 1000) {
                        if (entity.hasComponent(Consts.YIELD) && entity.hasComponent(Consts.TRANSFORM)) {
                            TransformComponent trans = (TransformComponent) entity.getComponent(Consts.TRANSFORM);
                            YieldComponent yield = (YieldComponent) entity.getComponent(Consts.YIELD);
                            Entity drop = new Entity(yield.getDrops()[0].name());
                            drop.addComponent(new SpriteComponent(drop, yield.getDrops()[0].getImagePath(), 50, 50));
                            drop.addComponent(new TransformComponent(drop, trans.getX(), trans.getY(), 40, 40));
                            // TODO make array for dropped items
                            itemDrops.add(drop);
                        }
                        iter.remove();
                    }
                }
            }
        }
        for (Iterator<Entity> iter = friendlyEnts.iterator(); iter.hasNext();) {
            Entity entity = (Entity) iter.next();
            if (!entity.hasComponent(Consts.INPUT) && entity.hasComponent(Consts.HEALTH)) {
                HealthComponent health = ((HealthComponent) entity.getComponent(Consts.HEALTH));
                if (!health.isAlive()) {
                    if (getTime() - health.getTimeOfDeath() >= decayTime * 1000) {
                        iter.remove();
                    }
                }
            }
        }

        // TODO optimize this too
        for (Iterator<Entity> iter1 = friendlyEnts.iterator(); iter1.hasNext();) {
            Entity ent1 = (Entity) iter1.next();
            if (ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.INVENTORY)) {
                for (Iterator<Entity> iter2 = itemDrops.iterator(); iter2.hasNext();) {
                    Entity ent2 = (Entity) iter2.next();
                    if (colliding(ent1, ent2)) {
                        ((InventoryComponent) ent1.getComponent(Consts.INVENTORY))
                                .addItem(ItemType.valueOf(ent2.getName()));

                        // if (!itemsDropped.isEmpty()) {
                        // ((InventoryComponent)
                        // ent1.getComponent(Consts.INVENTORY)).addItem(itemsDropped.get(0));
                        // itemsDropped.remove(0);
                        // }
                        iter2.remove();
                    }
                }
                break;
            }
        }

        for (Entity ent1: friendlyEnts) {
            for (components.Component comp: ent1.getComponents()) {
                if (direction.length() != 0) {
                    // TODO make shield/orbs that follow the player using the
                    // camera compenent
                    if (!ent1.hasComponent(Consts.CAMERA)) {
                        if (comp.getID() == Consts.TRANSFORM) {
                            ((TransformComponent) comp).move(-direction.getX(), -direction.getY());
                        }
                    }
                }
                if (!isOutsideScreen(ent1)) {
                    comp.update();
                }
            }
        }
        for (Entity ent1: enemyEnts) {
            for (components.Component comp: ent1.getComponents()) {
                if (direction.length() != 0) {
                    if (comp.getID() == Consts.TRANSFORM) {
                        ((TransformComponent) comp).move(-direction.getX(), -direction.getY());
                    }
                }
                if (!isOutsideScreen(ent1)) {
                    comp.update();
                }
            }
        }
        for (Entity ent1: itemDrops) {
            for (components.Component comp: ent1.getComponents()) {
                if (direction.length() != 0) {
                    if (comp.getID() == Consts.TRANSFORM) {
                        ((TransformComponent) comp).move(-direction.getX(), -direction.getY());
                    }
                }
                if (!isOutsideScreen(ent1)) {
                    comp.update();
                }
            }
        }
        input.clearKeyPressedRecord();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        // TODO separate render from offsetting
        map.drawMap(g, -direction.getX(), -direction.getY());

        drawSpells(g);

        drawEnts(g);

        g.draw(((TransformComponent) friendlyEnts.get(0).getComponent(Consts.TRANSFORM)).getShape());

        direction.x = 0;
        direction.y = 0;

        drawUI(g);

    }

    private void drawEnts(Graphics g) {
        for (Entity ent1: friendlyEnts) {
            if (ent1.hasComponent(Consts.SPRITE)) {
                SpriteComponent sprite = (SpriteComponent) ent1.getComponent(Consts.SPRITE);
                if (direction.length() != 0) {
                    if (ent1.hasComponent(Consts.INPUT)) {
                        ((SpriteComponent) ent1.getComponent(Consts.SPRITE)).animateWalk();
                    }
                }
                if (!isOutsideScreen(ent1)) {
                    sprite.draw();
                }
            }
            if (ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.INVENTORY)) {
                ((InventoryComponent) ent1.getComponent(Consts.INVENTORY)).draw();
            }
            if (ent1.hasComponent(Consts.TRANSFORM)) {
                ((TransformComponent) ent1.getComponent(Consts.TRANSFORM)).draw();
            }
        }
        for (Entity ent1: enemyEnts) {
            if (ent1.hasComponent(Consts.SPRITE)) {
                SpriteComponent sprite = (SpriteComponent) ent1.getComponent(Consts.SPRITE);
                if (!isOutsideScreen(ent1)) {
                    sprite.draw();
                }
            }
            if (ent1.hasComponent(Consts.TRANSFORM)) {
                ((TransformComponent) ent1.getComponent(Consts.TRANSFORM)).draw();
            }
        }
        for (Entity ent1: itemDrops) {
            if (ent1.hasComponent(Consts.SPRITE)) {
                SpriteComponent sprite = (SpriteComponent) ent1.getComponent(Consts.SPRITE);
                if (!isOutsideScreen(ent1)) {
                    sprite.draw();
                }
            }
            if (ent1.hasComponent(Consts.TRANSFORM)) {
                ((TransformComponent) ent1.getComponent(Consts.TRANSFORM)).draw();
            }
        }
    }

    private void drawSpells(Graphics g) {
        for (Spell spell: friendlySpells) {
            spell.render(g);
        }
        for (Spell spell: enemySpells) {
            spell.render(g);
        }

        for (Entity cospell: cospells) {
            ((SpriteComponent) cospell.getComponent(Consts.SPRITE)).draw();
        }
    }

    private void drawUI(Graphics g) {
        AttributesComponent attrs = (AttributesComponent) friendlyEnts.get(0).getComponent(Consts.ATTRIBUTES);
        LevelComponent lvl = (LevelComponent) friendlyEnts.get(0).getComponent(Consts.LEVEL);

        g.drawImage(iface1, Consts.SCREEN_WIDTH - 240, Consts.SCREEN_HEIGHT - 152, 0, 0, 243, 156,
                new Color(255, 255, 255, 200));

        for (Button button: buttons) {
            button.draw(g);
        }

        for (Button button: buttons) {
            if (button.contains(mouseX, mouseY)) {
                button.drawDescription(g);
                break;
            }
        }

        // TODO ?make this a button as well
        g.drawImage(spellIcons[currentSpell.ordinal()], Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 147,
                Consts.SCREEN_WIDTH - 176, Consts.SCREEN_HEIGHT - 88, 0, 0,
                spellIcons[currentSpell.ordinal()].getWidth(), spellIcons[currentSpell.ordinal()].getHeight());
        g.drawString("STR: " + (int) attrs.getStrength(), Consts.SCREEN_WIDTH - 140, Consts.SCREEN_HEIGHT - 145);
        // TODO ?optimize it using newlines
        g.drawString("AGI: " + (int) attrs.getAgility(), Consts.SCREEN_WIDTH - 140, Consts.SCREEN_HEIGHT - 123);
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

        g.drawString("(" + (int) -map.getOffset().getX() + ":" + (int) -map.getOffset().getY() + ")",
                Consts.SCREEN_WIDTH - 120, 10);
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

            // TODO optimize it
            if (distance <= trans1.getRadius() + trans2.getRadius()) {
                if (trans1.getShape().intersects(trans2.getShape())) {
                    return true;
                } else if (trans1.getShape().contains(trans2.getShape())) {
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

            // TODO optimize it
            if (distance <= trans1.getRadius() + spell.getRadius()) {
                if (trans1.getShape().intersects(spell.getShape())) {
                    return true;
                } else if (trans1.getShape().contains(spell.getShape())) {
                    return true;
                } else if (spell.getShape().contains(trans1.getShape())) {
                    return true;
                }
            }
        }
        return false;
    }

    public float distance(Entity ent1, Entity ent2) {
        if (ent1.equals(ent2)) {
            return (float) 1e6;
        }
        if (ent1.hasComponent(Consts.TRANSFORM) && ent2.hasComponent(Consts.TRANSFORM)) {
            TransformComponent trans1 = (TransformComponent) (ent1.getComponent(Consts.TRANSFORM));
            TransformComponent trans2 = (TransformComponent) (ent2.getComponent(Consts.TRANSFORM));
            float dx = trans1.getCenterX() - trans2.getCenterX();
            float dy = trans1.getCenterY() - trans2.getCenterY();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            return dist;
        }

        return (float) 1e6;
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
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + 0 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());

        }

        // middle column passives
        for (int i = 6; i < 9; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + 1 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
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
