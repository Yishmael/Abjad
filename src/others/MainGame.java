package others;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Vector2f;

import components.Component;
import components.spells.CircularMovement;
import components.spells.SpellComponent;
import components.units.AttackComponent;
import components.units.AttributesComponent;
import components.units.CameraComponent;
import components.units.CastComponent;
import components.units.CollisionComponent;
import components.units.ComputerMovementComponent;
import components.units.DefenseComponent;
import components.units.HealthComponent;
import components.units.InputComponent;
import components.units.InventoryComponent;
import components.units.LevelComponent;
import components.units.ManaComponent;
import components.units.PlayerMovementCompenent;
import components.units.ResistanceComponent;
import components.units.SpriteComponent;
import components.units.StatusComponent;
import components.units.TransformComponent;
import components.units.YieldComponent;
import enums.PassiveType;
import enums.SpellType;
import spells.Spell;
import spells.projectile.Bouncer;
import spells.projectile.Fireball;
import spells.selfcast.Heal;
import spells.selfcast.HolyShield;

public class MainGame extends BasicGame { // add states

    public static int dt = 0;

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame());
            appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, Consts.FULLSCREEN);
            appgc.setTargetFrameRate(Consts.FPS);
            appgc.setIcon("res/images/ui/icon.png");
            // appgc.setClearEachFrame(false);
            // appgc.setUpdateOnlyWhenVisible(false);
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
    // private Sound sound;
    private Image iface1;
    private Image[] spellIcons;
    private SpellType currentSpell = SpellType.Null;
    private Vector2f offset = new Vector2f();
    private Button[] buttons;
    private float mouseX, mouseY;
    private int mouseWheel;
    private Vector2f playerPos = new Vector2f(Consts.SCREEN_WIDTH / 2 - 32, Consts.SCREEN_HEIGHT / 2 - 32);
    private float playerAngle;
    private Vector2f destination = new Vector2f(playerPos);
    private long lastTime = 0;
    private float decayTime = 12f;
    private boolean showTree = false;
    private boolean showMap = false;

    public MainGame() {
        super("Stars are falling");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setMouseCursor(new Image("res/images/ui/cursor2.png"), 0, 0);
        map = new Map();
        iface1 = new Image("res/images/ui/iface1.png");
        buttons = new Button[30];

        initButtons();

        spellIcons = new Image[SpellType.values().length];

        for (int i = 0; i < spellIcons.length; i++) {
            spellIcons[i] = new Image(SpellType.values()[i].getIconPath());
        }

        // TODO make map with coords where enemies should spawn
        Entity entity = new Entity("Player");
        entity.addComponent(
                new SpriteComponent(entity, new SpriteSheet("res/images/player1.png", 64, 64), 220, false, 64, 64));
        entity.addComponent(new TransformComponent(entity, playerPos.getX(), playerPos.getY(), 60, 60));
        entity.addComponent(new HealthComponent(entity, 50, 50, 0.34f));
        entity.addComponent(new AttackComponent(entity, 1.5f, 0.8f));
        entity.addComponent(new DefenseComponent(entity, 10));
        entity.addComponent(new PlayerMovementCompenent(entity, 4f));
        entity.addComponent(new ManaComponent(entity, 30, 30, 1.7f));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new ResistanceComponent(entity));
        ArrayList<Entity> inventory = new ArrayList<Entity>();
        // inventory.add(EntityFactory.getCherryBlueprint(1));
        inventory.add(EntityFactory.getSwiftnessPotion1(1));
        inventory.add(EntityFactory.getAxeBlueprint(1));
        inventory.add(EntityFactory.getHealingPotionBlueprint1(2));
        inventory.add(EntityFactory.getHealingPotionBlueprint2(2));
        inventory.add(EntityFactory.getReplenishingPotionBlueprint2(1));
        entity.addComponent(new InventoryComponent(entity, inventory));
        entity.addComponent(new InputComponent(entity));
        SpellType[] spells = SpellType.getSorcererSpells();
        currentSpell = spells[0];
        entity.addComponent(new CastComponent(entity, spells));
        entity.addComponent(new AttributesComponent(entity, 0, 0, 0));
        entity.addComponent(new LevelComponent(entity, 1));
        entity.addComponent(new CollisionComponent(entity));
        entity.addComponent(new CameraComponent(entity));
        friendlyEnts.add(entity);

        enemyEnts.add(EntityFactory.getEnemyBlueprint1(300, 50, 5));
        enemyEnts.add(EntityFactory.getEnemyBlueprint1(180, 450, 1));
        enemyEnts.add(EntityFactory.getEnemyBlueprint1(60, 260, 1));
        enemyEnts.add(EntityFactory.getEnemyBlueprint2(70, 60, 1));
        enemyEnts.add(EntityFactory.getDummyBlueprint(40, 400, 500, 500));
        enemyEnts.add(EntityFactory.getBarrelBlueprint());
        enemyEnts.add(EntityFactory.getBarrelBlueprint());
        enemyEnts.add(EntityFactory.getCampfireBlueprint());
        enemyEnts.add(EntityFactory.getBirdBlueprint());
        // for (int i = 0; i < 3; i++) {
        // for (int j = 0; j < 3; j++) {
        // if (Math.random() < 0.3) {
        // enemyEnts.add(EntityFactory.getEnemyBlueprint2(Consts.SCREEN_WIDTH -
        // 100 +
        // i * 3 * 64,
        // Consts.SCREEN_HEIGHT - 100 + j * 3 * 64, j + 1));
        // } else {
        // enemyEnts.add(EntityFactory.getEnemyBlueprint1(Consts.SCREEN_WIDTH -
        // 100 +
        // i * 3 * 64,
        // Consts.SCREEN_HEIGHT - 100 + j * 3 * 64, j + 1));
        // }
        // }
        // }

        friendlyEnts.add(EntityFactory.getShopBlueprint1(500, 10));
    }

    @Override
    public void update(GameContainer gc, int unusedDelta) throws SlickException {
        input = gc.getInput();
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();
        mouseWheel = Mouse.getDWheel();
        playerAngle = (float) Math.atan2(mouseY - playerPos.getY() - 32, mouseX - playerPos.getX() - 32);

        dt = getDelta();
        if (dt > 20) {
            dt = 20;
        }

        // show name (and stats) of entity when hovering over it

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            for (int i = 0; i < 3; i++) {
                if (buttons[i].contains(mouseX, mouseY)) {
                    System.out.println("Clicked on " + buttons[i].getDescription());
                    for (Entity ent: friendlyEnts) {
                        if (ent.hasComponent(Consts.INPUT)) {
                            ent.broadcast(buttons[i].getCommand());
                        }
                    }
                }
            }
            if (showTree) {
                for (int i = 3; i < buttons.length; i++) {
                    if (buttons[i].contains(mouseX, mouseY)) {
                        System.out.println("Clicked on " + buttons[i].getDescription());
                        for (Entity ent: friendlyEnts) {
                            if (ent.hasComponent(Consts.INPUT)) {
                                ent.broadcast(buttons[i].getCommand());
                            }
                        }
                    }
                }
            }
            for (Entity ent: friendlyEnts) {
                if (ent.getName().matches("Shop") && colliding(friendlyEnts.get(0), ent)) {
                    if (((TransformComponent) ent.getComponent(Consts.TRANSFORM)).contains(mouseX, mouseY)) {
                        ent.broadcast("toggleInv");
                    }
                }
            }
            // System.out.println(mouseX + ":" + mouseY);
        }

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            if (((PlayerMovementCompenent) friendlyEnts.get(0).getComponent(Consts.PLAYERMOVEMENT)).canMove()) {
                if (new Vector2f(mouseX, mouseY).distance(playerPos) > 1) {
                    destination = new Vector2f(mouseX, mouseY);
                }
            }
        }

        if (destination.distance(playerPos) > 1) {
            float angle1 = (float) Math.atan2(destination.getY() - playerPos.getY(),
                    destination.getX() - playerPos.getX());
            PlayerMovementCompenent movement = ((PlayerMovementCompenent) friendlyEnts.get(0)
                    .getComponent(Consts.PLAYERMOVEMENT));
            if (movement.canMove()) {
                float speed1 = movement.getSpeed();
                offset.x = (float) (dt / 1000f * speed1 * Math.cos(angle1) * Consts.TILE_SIZE);
                offset.y = (float) (dt / 1000f * speed1 * Math.sin(angle1) * Consts.TILE_SIZE);
                destination.x = Math.abs(destination.getX() - offset.getX());
                destination.y = Math.abs(destination.getY() - offset.getY());
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
        if (input.isKeyPressed(Input.KEY_T)) {
            showTree = !showTree;
        }
        if (input.isKeyPressed(Input.KEY_M)) {
            showMap = !showMap;
        }

        for (Iterator<Entity> iterator = friendlyEnts.iterator(); iterator.hasNext();) {
            Entity ent1 = iterator.next();
            if (ent1.hasComponent(Consts.INPUT)) {
                if (mouseWheel > 0) {
                    ent1.broadcast("next spell");
                    CastComponent spell = (CastComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = spell.getCurrentSpell();
                } else if (mouseWheel < 0) {
                    ent1.broadcast("prev spell");
                    CastComponent spell = (CastComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = spell.getCurrentSpell();
                }
                if (input.isKeyPressed(Input.KEY_1)) {
                    ent1.broadcast("KEY " + Input.KEY_1);
                }
                if (input.isKeyPressed(Input.KEY_2)) {
                    ent1.broadcast("KEY " + Input.KEY_2);
                    CastComponent spell = (CastComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = spell.getCurrentSpell();
                }
                if (input.isKeyPressed(Input.KEY_3)) {
                    System.out.println(((StatusComponent) ent1.getComponent(Consts.STATUS)).getCurrentStatus());
                }
                if (input.isKeyPressed(Input.KEY_4)) {

                }
                if (input.isKeyPressed(Input.KEY_I)) {
                    ent1.broadcast("KEY " + Input.KEY_I);
                }
                if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                    ent1.broadcast("KEY " + Input.KEY_ESCAPE);
                }
                if (input.isKeyPressed(Input.KEY_F9)) {
                    ent1.broadcast("AS 20%");
                    ent1.broadcast("DMG 20%");
                    ent1.broadcast("MS 20%");
                    ent1.broadcast("LS 20%");
                    ent1.broadcast("MPcap 500");
                    ent1.broadcast("HPcap 500");
                    ent1.broadcast("MPregen 100");
                }
                if (input.isKeyPressed(Input.KEY_0)) {
                    ent1.broadcast("exp 500");
                }
                if (input.isKeyPressed(Input.KEY_R)) {
                    ent1.broadcast("ress");
                }
                if (input.isKeyDown(Input.KEY_A)) {
                    AttackComponent attack = (AttackComponent) ent1.getComponent(Consts.ATTACK);
                    if (attack.isReady()) {
                        String action = attack.attack();
                        for (Entity ent2: enemyEnts) {
                            if (ent2.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2) || distance(ent1, ent2) <= 64 + 8 * attack.getRangeAdder()) {
                                    // get health before and after attack to fix
                                    // killing blow lifesteal
                                    float healthBefore = ((HealthComponent) ent2.getComponent(Consts.HEALTH))
                                            .getHealth();
                                    ent2.process(new MessageChannel(ent1, action));
                                    float healthAfter = ((HealthComponent) ent2.getComponent(Consts.HEALTH))
                                            .getHealth();
                                    if (healthBefore > 0 && healthAfter <= 0) {
                                        if (ent2.hasComponent(Consts.LEVEL)) {
                                            LevelComponent level = (LevelComponent) ent2.getComponent(Consts.LEVEL);
                                            ent1.process(
                                                    new MessageChannel(ent2, "exp " + level.getExperienceBounty()));
                                        }
                                    }
                                    if (healthBefore > 0) {
                                        ent1.process(new MessageChannel(ent2,
                                                "HPdelta " + (healthBefore - healthAfter) * attack.getLifesteal()));
                                    }
                                    if (attack.getCleaveRadius() == 0) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (input.isKeyDown(Input.KEY_E) || input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
                    CastComponent spell = (CastComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = spell.getCurrentSpell();
                    if (spell.isReady()) {
                        String action = spell.cast();
                        if (action != null) {
                            // sound = new Sound(currentSpell.getSoundPath());
                            // sound.play();
                            switch (currentSpell) {
                            case CoFireball:
                                cospells.add(EntityFactory.getFireballBlueprint(playerPos.getX() + 32,
                                        playerPos.getY() + 32, playerAngle, spell.getDamage(), spell.getDamageType()));
                                break;
                            case Decrepify:
                                cospells.add(EntityFactory.getDecrepifyBlueprint(mouseX, mouseY, 128));
                                break;
                            case SummonWall:
                                friendlyEntsQueue.add(EntityFactory.getWallBlueprint(mouseX, mouseY, playerAngle));
                                break;
                            case ReviveMinion:
                                Entity closestEntity = getClosestEntity(new Vector2f(mouseX, mouseY), friendlyEnts);
                                closestEntity.broadcast("ress");
                                break;
                            case HolyShield:
                                friendlySpells.add(new HolyShield(new Image(currentSpell.getImagePath()), "nothing",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), 0, 30, 5));
                                break;
                            case Teleport:
                                offset = new Vector2f(-(playerPos.getX() - mouseX), -(playerPos.getY() - mouseY));
                                break;
                            case Heal:
                                cospells.add(EntityFactory.getHealBlueprint(playerPos.getX() + 32,
                                        playerPos.getY() + 32, spell.getHealing(), "friend"));
                                break;
                            case ScorchedEarth:
                                cospells.add(EntityFactory.getScorchedEarthBlueprint(playerPos.getX() + 32,
                                        playerPos.getY() + 32, spell.getDamage(), 50));
                                break;
                            case Weaken:
                                cospells.add(EntityFactory.getWeakenBlueprint(mouseX, mouseY, 50));
                                break;
                            case PoisonArrow:
                                cospells.add(EntityFactory.getPoisonArrowBlueprint(playerPos.getX() + 32,
                                        playerPos.getY() + 32, playerAngle, 2, 4, "enemy"));
                                break;
                            case Bouncer:
                                friendlySpells.add(new Bouncer(new Image(currentSpell.getImagePath()), "enemy",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), playerAngle,
                                        SpellType.Bouncer.getSpeed(), SpellType.Bouncer.getRange(), spell.getDamage(),
                                        15));
                                break;
                            case MultiBouncer:
                                int count = 5;
                                for (int i = 0; i < count; i++) {
                                    friendlySpells.add(new Bouncer(new Image(SpellType.Bouncer.getImagePath()), "enemy",
                                            new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32),
                                            (float) ((2 * Math.PI * i - Math.PI * 0.4f) / count),
                                            SpellType.Bouncer.getSpeed(), SpellType.Bouncer.getRange(),
                                            spell.getDamage(), 15));
                                }
                                break;
                            case SummonKirith:
                                // kill any previous summons
                                for (Entity ent: friendlyEnts) {
                                    if (ent.getName().matches("Kirith")) {
                                        ent.broadcast("HPdelta -999999");
                                    }
                                }
                                friendlyEntsQueue
                                        .add(EntityFactory.getKirithBlueprint(mouseX, mouseY, spell.getDamage()));
                                break;
                            case Fireball:
                                friendlySpells.add(new Fireball(new Image(currentSpell.getImagePath()), "enemy",
                                        new Vector2f(playerPos.getX() + 32, playerPos.getY() + 32), playerAngle,
                                        SpellType.Fireball.getSpeed(), SpellType.Fireball.getRange(), spell.getDamage(),
                                        16));
                                break;
                            default:
                                break;
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
                    CastComponent spell = (CastComponent) ent1.getComponent(Consts.SPELL);
                    float dx = x - playerPos.getX();
                    float dy = y - playerPos.getY();
                    if (Math.sqrt(dx * dx + dy * dy) <= spell.getCurrentSpell().getRange()) {
                        if (spell.isReady()) {
                            String action = spell.cast();
                            if (action != null) {
                                // sound = new
                                // Sound(spell.getCurrentSpell().getSoundPath());
                                // sound.play();
                                float angle = (float) Math.atan2(playerPos.getY() + 32 - y, playerPos.getX() + 32 - x);
                                switch (spell.getCurrentSpell()) {
                                case Fireball:
                                    enemySpells.add(new Fireball(new Image(spell.getCurrentSpell().getImagePath()),
                                            "friend", new Vector2f(x, y), angle, SpellType.Fireball.getSpeed(),
                                            SpellType.Fireball.getRange(), spell.getDamage(), 16));
                                    break;
                                case ReviveMinion:
                                    ArrayList<Entity> ents = new ArrayList<Entity>();
                                    for (Entity ent: enemyEnts) {
                                        ents.add(ent);
                                    }
                                    Entity temp = ents.get(0);
                                    if (temp.hasComponent(Consts.HEALTH)) {
                                        while (((HealthComponent) temp.getComponent(Consts.HEALTH)).isAlive()) {
                                            ents.remove(temp);
                                            temp = getClosestEntity(ent1, ents);
                                            if (ents.isEmpty()) {
                                                break;
                                            }
                                            if (!temp.hasComponent(Consts.HEALTH)) {
                                                break;
                                            }
                                        }
                                        if (temp.getName().matches("Common unit [0-9]+")) {
                                            temp.broadcast("ress");
                                        }
                                    }
                                    break;
                                case PoisonArrow:
                                    cospells.add(EntityFactory.getPoisonArrowBlueprint(x, y, angle, 2, 4, "friend"));
                                    break;
                                case Bouncer:
                                    enemySpells.add(new Bouncer(new Image(SpellType.Bouncer.getImagePath()), "friend",
                                            new Vector2f(x + 30, y), angle, SpellType.Bouncer.getSpeed(),
                                            SpellType.Bouncer.getRange(), spell.getDamage(), 15));
                                    break;
                                case Heal:
                                    enemySpells.add(new Heal(new Image(SpellType.Heal.getImagePath()), "enemy",
                                            new Vector2f(x, y), 0, spell.getHealing(), 15));
                                    break;
                                default:
                                    break;
                                }
                                spell.receive("next spell");
                            }
                        }

                    }
                }
            }
        }

        for (Iterator<Entity> iter = cospellsQueue.iterator(); iter.hasNext();) {
            Entity entity = iter.next();
            cospells.add(entity);
            iter.remove();
        }
        for (Iterator<Entity> iter = friendlyEntsQueue.iterator(); iter.hasNext();) {
            Entity entity = iter.next();
            friendlyEnts.add(entity);
            iter.remove();
        }
        for (Iterator<Entity> iter = enemyEntsQueue.iterator(); iter.hasNext();) {
            Entity entity = iter.next();
            enemyEnts.add(entity);
            iter.remove();
        }

        // temp
        for (Entity cospell: cospells) {
            if (offset.length() != 0) {
                ((TransformComponent) cospell.getComponent(Consts.TRANSFORM)).move(-offset.getX(), -offset.getY());
            }
            for (Component comp: cospell.getComponents()) {
                if (comp.getID() != Consts.SPRITE) {
                    comp.update();
                }
            }
        }

        for (Spell spell: friendlySpells) {
            spell.offset(offset.negate());
            spell.update();
        }
        for (Spell spell: enemySpells) {
            spell.offset(offset.negate());
            spell.update();
        }

        for (Iterator<Entity> iter = cospells.iterator(); iter.hasNext();) {
            Entity spell = iter.next();
            if (isOutsideScreen(spell) || ((SpellComponent) spell.getComponent(Consts.SPELL)).isFinished()) {
                iter.remove();
                // System.out.println(spell.getName() + " destroyed");
            }
        }
        for (Iterator<Spell> iter = friendlySpells.iterator(); iter.hasNext();) {
            Spell spell = iter.next();
            if (isOutsideScreen(spell) || spell.isFinished()) {
                iter.remove();
                // System.out.println(spell.toString() + " destroyed");
            }
        }
        for (Iterator<Spell> iter = enemySpells.iterator(); iter.hasNext();) {
            Spell spell = iter.next();
            if (isOutsideScreen(spell) || spell.isFinished()) {
                iter.remove();
                // System.out.println(spell.toString() + " destroyed");
            }
        }

        for (Iterator<Entity> iter = cospells.iterator(); iter.hasNext();) {
            Entity spell = iter.next();
            SpellComponent spellComp = ((SpellComponent) spell.getComponent(Consts.SPELL));
            if (spellComp.getTargets().contains("enemy")) {
                ArrayList<Entity> targets = new ArrayList<Entity>();
                for (Entity enemy: enemyEnts) {
                    if (colliding(enemy, spell)) {
                        if (!targets.contains(enemy)) {
                            targets.add(enemy);
                        }
                        // sound = new
                        // Sound(SpellType.valueOf(spell.getName()).getDeathSoundPath());
                        // sound.play();
                    }
                }
                if (targets.size() > 0) {
                    spellComp.handleTargets(targets);
                }
                if (spellComp.isFinished()) {
                    iter.remove();
                }
            }
            if (spellComp.getTargets().contains("friend")) {
                ArrayList<Entity> targets = new ArrayList<Entity>();
                for (Entity friend: friendlyEnts) {
                    if (colliding(friend, spell)) {
                        if (!targets.contains(friend)) {
                            targets.add(friend);
                        }
                        // sound = new
                        // Sound(SpellType.valueOf(spell.getName()).getDeathSoundPath());
                        // sound.play();
                    }
                }
                if (targets.size() > 0) {
                    spellComp.handleTargets(targets);
                }
                if (spellComp.isFinished()) {
                    iter.remove();
                }
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
                            if (ent1.hasComponent(Consts.HEALTH)) {
                                boolean aliveBefore = ((HealthComponent) ent1.getComponent(Consts.HEALTH)).isAlive();
                                ent1.process(new MessageChannel(friendlyEnts.get(0), action));
                                boolean aliveAfter = ((HealthComponent) ent1.getComponent(Consts.HEALTH)).isAlive();

                                if (aliveBefore && !aliveAfter) {
                                    if (ent1.hasComponent(Consts.LEVEL)) {
                                        LevelComponent level = (LevelComponent) ent1.getComponent(Consts.LEVEL);
                                        friendlyEnts.get(0).process(
                                                new MessageChannel(ent1, "exp " + level.getExperienceBounty()));
                                    }
                                }
                            }
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
        for (Entity ent1: enemyEnts) {
            if (!isOutsideScreen(ent1)) {
                for (Entity ent2: friendlyEnts) {
                    if (!isOutsideScreen(ent2)) {
                        if (ent1.hasComponent(Consts.ATTACK) && !ent1.hasComponent(Consts.INPUT)) {
                            if (ent2.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2)) {
                                    AttackComponent combat = (AttackComponent) ent1.getComponent(Consts.ATTACK);
                                    if (combat.isReady()) {
                                        ent2.process(new MessageChannel(ent1, combat.attack()));
                                    }
                                }
                            }
                        }
                        if (ent2.hasComponent(Consts.ATTACK) && !ent2.hasComponent(Consts.INPUT)) {
                            if (ent1.hasComponent(Consts.HEALTH)) {
                                if (colliding(ent1, ent2)) {
                                    AttackComponent combat = (AttackComponent) ent2.getComponent(Consts.ATTACK);
                                    if (combat.isReady()) {
                                        ent1.process(new MessageChannel(ent2, combat.attack()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // TODO optimize this slow loop
        for (Iterator<Entity> iter = enemyEnts.iterator(); iter.hasNext();) {
            Entity entity = iter.next();
            if (entity.hasComponent(Consts.HEALTH)) {
                HealthComponent health = ((HealthComponent) entity.getComponent(Consts.HEALTH));
                if (!health.isAlive()) {
                    if (entity.hasComponent(Consts.YIELD) && entity.hasComponent(Consts.TRANSFORM)) {
                        TransformComponent trans = (TransformComponent) entity.getComponent(Consts.TRANSFORM);
                        YieldComponent yield = (YieldComponent) entity.getComponent(Consts.YIELD);
                        if (!yield.hasYielded()) {
                            for (Entity drop: yield.getDrops()) {
                                drop.addComponent(
                                        new TransformComponent(drop, (float) (trans.getX() + Math.random() * 20),
                                                (float) (trans.getY() + Math.random() * 20), 20, 20, 1));
                                itemDrops.add(drop);
                            }
                        }
                    }
                    if (getTime() - health.getTimeOfDeath() >= decayTime * 1000) {
                        iter.remove();
                    }
                }
            }
            // TODO optimize this too
            for (Iterator<Entity> iter1 = friendlyEnts.iterator(); iter1.hasNext();) {
                Entity ent1 = iter1.next();
                if (ent1.hasComponent(Consts.INPUT) && ent1.hasComponent(Consts.INVENTORY)) {
                    for (Iterator<Entity> iter2 = itemDrops.iterator(); iter2.hasNext();) {
                        Entity ent2 = iter2.next();
                        if (colliding(ent1, ent2)) {
                            ((InventoryComponent) ent1.getComponent(Consts.INVENTORY)).addItem(ent2);
                            iter2.remove();
                        }
                    }
                    break;
                }
            }
        }
        for (Iterator<Entity> iter = friendlyEnts.iterator(); iter.hasNext();) {
            Entity entity = iter.next();
            if (!entity.hasComponent(Consts.INPUT) && entity.hasComponent(Consts.HEALTH)) {
                HealthComponent health = ((HealthComponent) entity.getComponent(Consts.HEALTH));
                if (!health.isAlive()) {
                    if (getTime() - health.getTimeOfDeath() >= decayTime * 1000) {
                        iter.remove();
                    }
                }
            }
        }

        for (Entity ent1: friendlyEnts) {
            for (Component comp: ent1.getComponents()) {
                if (offset.length() != 0) {
                    // TODO make shield/orbs that follow the player using the
                    // camera component
                    if (!ent1.hasComponent(Consts.CAMERA)) {
                        if (comp.getID() == Consts.TRANSFORM) {
                            ((TransformComponent) comp).move(-offset.getX(), -offset.getY());
                        }
                    }
                }
                if (!isOutsideScreen(ent1)) {
                    comp.update();
                }
            }
        }
        for (Entity ent1: enemyEnts) {
            for (Component comp: ent1.getComponents()) {
                if (offset.length() != 0) {
                    if (comp.getID() == Consts.TRANSFORM) {
                        ((TransformComponent) comp).move(-offset.getX(), -offset.getY());
                    }
                }
                if (!isOutsideScreen(ent1)) {
                    comp.update();
                    if (comp.getID() == Consts.TRANSFORM) {
                        if (ent1.hasComponent(Consts.COMPUTERMOVEMENT)) {
                            ComputerMovementComponent movement = ((ComputerMovementComponent) ent1
                                    .getComponent(Consts.COMPUTERMOVEMENT));
                            if (distance(ent1, friendlyEnts.get(0)) <= movement.getAquisitionRange()
                                    && distance(ent1, friendlyEnts.get(0)) >= movement.getMinimumDistance()) {
                                if (movement.canMove()) {
                                    TransformComponent trans = (TransformComponent) comp;
                                    float angle = (float) Math.atan2(playerPos.getY() - trans.getY(),
                                            playerPos.getX() - trans.getX());
                                    movement.move((float) Math.cos(angle), (float) Math.sin(angle));
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Entity ent1: itemDrops) {
            for (Component comp: ent1.getComponents()) {
                if (offset.length() != 0) {
                    if (comp.getID() == Consts.TRANSFORM) {
                        ((TransformComponent) comp).move(-offset.getX(), -offset.getY());
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
        // TODO separate render from offsetting and map from minimap
        map.drawMap(g, -offset.getX(), -offset.getY(), showMap);

        drawSpells(g);

        drawEnts(g);

        g.draw(((TransformComponent) friendlyEnts.get(0).getComponent(Consts.TRANSFORM)).getShape());

        offset.x = 0;
        offset.y = 0;

        ArrayList<Status> statusList = ((StatusComponent) friendlyEnts.get(0).getComponent(Consts.STATUS))
                .getCurrentStatus();
        for (int i = 0; i < statusList.size(); i++) {
            g.drawString(statusList.get(i).toString(), 5, 50 + 15 * i);
        }

        drawUI(g);

    }

    private void drawEnts(Graphics g) {

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
        for (Entity ent1: friendlyEnts) {
            if (ent1.hasComponent(Consts.SPRITE)) {
                SpriteComponent sprite = (SpriteComponent) ent1.getComponent(Consts.SPRITE);
                if (offset.length() != 0) {
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
            } else if (ent1.hasComponent(Consts.INVENTORY)) {
                ((InventoryComponent) ent1.getComponent(Consts.INVENTORY)).draw();
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
            ((TransformComponent) cospell.getComponent(Consts.TRANSFORM)).draw();
            if (cospell.hasComponent(Consts.CIRCULARMOVEMENT)) {
                ((CircularMovement) cospell.getComponent(Consts.CIRCULARMOVEMENT)).draw();
            }
        }
    }

    private void drawUI(Graphics g) {
        AttributesComponent attrs = (AttributesComponent) friendlyEnts.get(0).getComponent(Consts.ATTRIBUTES);
        LevelComponent lvl = (LevelComponent) friendlyEnts.get(0).getComponent(Consts.LEVEL);

        g.drawImage(iface1, Consts.SCREEN_WIDTH - 240, Consts.SCREEN_HEIGHT - 152, 0, 0, 243, 156);

        for (int i = 0; i < 3; i++) {
            buttons[i].draw(g);
        }
        for (int i = 0; i < 3; i++) {
            if (buttons[i].contains(mouseX, mouseY)) {
                buttons[i].drawDescription(g);
                break;
            }
        }

        if (showTree) {
            for (int i = 3; i < buttons.length; i++) {
                buttons[i].draw(g);
            }
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].contains(mouseX, mouseY)) {
                    buttons[i].drawDescription(g);
                    break;
                }
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

        g.drawString("Points: " + attrs.getAvailablePoints(), Consts.SCREEN_WIDTH - 140, Consts.SCREEN_HEIGHT - 80);

        g.drawString("Level " + lvl.getLevel(), Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 72);
        g.drawString("Experience " + (long) lvl.getExperience() + "/" + (long) lvl.getMaxExperience(),
                Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 52);
        g.drawRect(Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 32, 100, 10);
        g.fillRect(Consts.SCREEN_WIDTH - 235, Consts.SCREEN_HEIGHT - 32,
                100 * (lvl.getExperience() - lvl.getRequiredExperience(lvl.getLevel()))
                        / (lvl.getMaxExperience() - lvl.getRequiredExperience(lvl.getLevel())),
                10);
        g.setColor(Color.white);

        g.drawString("(" + (int) (-map.getOffset().getX() + playerPos.getX()) + ":"
                + (int) (-map.getOffset().getY() + playerPos.getY()) + ")", Consts.SCREEN_WIDTH - 120, 10);
    }

    private Entity getClosestEntity(Entity ent1, ArrayList<Entity> ents) {
        float distance = Integer.MAX_VALUE;
        Entity result = null;

        for (Entity ent2: ents) {
            if (distance(ent1, ent2) < distance) {
                distance = distance(ent1, ent2);
                result = ent2;
            }
        }

        return result;
    }

    private Entity getClosestEntity(Vector2f point, ArrayList<Entity> ents) {
        float distance = Integer.MAX_VALUE;
        TransformComponent trans = null;
        Entity result = null;

        for (Entity ent2: ents) {
            trans = ((TransformComponent) ent2.getComponent(Consts.TRANSFORM));
            float dx = trans.getX() - point.getX();
            float dy = trans.getY() - point.getY();
            if (Math.sqrt(dx * dx + dy * dy) < distance) {
                distance = (float) Math.sqrt(dx * dx + dy * dy);
                result = ent2;
            }
        }

        return result;
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
                } else if (trans2.getShape().contains(trans1.getShape())) {
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
            return Integer.MAX_VALUE;
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

    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastTime);
        lastTime = time;
        return delta;
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private void initButtons() throws SlickException {

        // primary stats
        for (int i = 0; i < 3; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(new Image(tempType.getIconPath()),
                    new Rectangle(Consts.SCREEN_WIDTH - 170, Consts.SCREEN_HEIGHT - 145 + 22 * i, 20, 20),
                    tempType.getName(), tempType.getCommand());
        }

        // column 1
        for (int i = 3; i < 6; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + 0 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());

        }

        // column 2
        for (int i = 6; i < 9; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + 1 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }

        // column 3
        for (int i = 9; i < 12; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + 2 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }

        // column 4
        for (int i = 12; i < 15; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + -1 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }
        // column 5
        for (int i = 15; i < 18; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + -2 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }
        // column 6
        for (int i = 18; i < 21; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + -3 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }
        // column 7
        for (int i = 21; i < 24; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + -4 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }
        // column 8
        for (int i = 24; i < 27; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + -5 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }
        // column 9
        for (int i = 27; i < 30; i++) {
            PassiveType tempType = PassiveType.values()[i];
            buttons[i] = new Button(
                    new Image(tempType.getIconPath()), new RoundedRectangle(Consts.SCREEN_WIDTH - 230 + -6 * 80,
                            Consts.SCREEN_HEIGHT - 451 + (i % 3) * 80, 50, 50, 5),
                    tempType.getName(), tempType.getCommand());
        }
    }

    private boolean isOutsideScreen(Entity entity) {
        if (entity.hasComponent(Consts.TRANSFORM)) {
            TransformComponent trans = (TransformComponent) entity.getComponent(Consts.TRANSFORM);
            // TODO change +200 to adapt to actual width/height of the image
            if (trans.getX() + 200 < 0 || trans.getX() - 200 > Consts.SCREEN_WIDTH) {
                return true;
            } else if (trans.getY() + 200 < 0 || trans.getY() - 200 > Consts.SCREEN_HEIGHT) {
                return true;
            }
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

}
