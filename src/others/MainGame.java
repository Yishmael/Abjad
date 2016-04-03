package others;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
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

import components.Component;
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
import factories.ItemFactory;
import factories.SpellFactory;
import factories.UnitFactory;

public class MainGame extends BasicGame { // add states

    public static int dt = 0;
    public static boolean DEBUG = false;

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
    private ArrayList<Entity> friendlySpells = new ArrayList<Entity>();
    private ArrayList<Entity> friendlySpellsQueue = new ArrayList<Entity>();

    private ArrayList<Entity> enemyEnts = new ArrayList<Entity>();
    private ArrayList<Entity> enemyEntsQueue = new ArrayList<Entity>();
    private ArrayList<Entity> enemySpells = new ArrayList<Entity>();
    private ArrayList<Entity> enemySpellsQueue = new ArrayList<Entity>();

    private ArrayList<ArrayList<Entity>> ents = new ArrayList<ArrayList<Entity>>();
    private ArrayList<ArrayList<Entity>> spellents = new ArrayList<ArrayList<Entity>>();

    private ArrayList<Entity> itemDrops = new ArrayList<Entity>();

    private Map map;
    private Input input;
    private Image iface1;
    private Image[] spellIcons;
    private Spell currentSpell = null;
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
    private boolean showInventory = false;

    public MainGame() {
        super("Stars are falling");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setMouseCursor(new Image("res/images/ui/cursor1.png"), 0, 0);
        map = new Map();
        iface1 = new Image("res/images/ui/iface1.png");
        buttons = new Button[30];

        initButtons();

        ents.add(friendlyEnts);
        ents.add(enemyEnts);

        spellents.add(friendlySpells);
        spellents.add(enemySpells);

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
        entity.addComponent(new ManaComponent(entity, 30, 30, 0.25f));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new ResistanceComponent(entity));
        ArrayList<Entity> inventory = new ArrayList<Entity>();
        inventory.add(ItemFactory.getCherryBlueprint(1));
        inventory.add(ItemFactory.getAxeBlueprint(1));
        inventory.add(ItemFactory.getSwordBlueprint(1));
        inventory.add(ItemFactory.getSwiftnessPotion1(1));
        inventory.add(ItemFactory.getHealingPotionBlueprint2(2));
        inventory.add(ItemFactory.getReplenishingPotionBlueprint2(1));
        entity.addComponent(new InventoryComponent(entity, inventory));
        entity.addComponent(new InputComponent(entity));
        Spell[] spells = { Spell.getSpellData("Fireball"), Spell.getSpellData("PoisonArrow") };
        currentSpell = spells[0];
        entity.addComponent(new CastComponent(entity, spells));
        entity.addComponent(new AttributesComponent(entity, 0, 0, 0));
        entity.addComponent(new LevelComponent(entity, 1));
        entity.addComponent(new CollisionComponent(entity));
        entity.addComponent(new CameraComponent(entity));
        friendlyEnts.add(entity);

        friendlyEnts.add(UnitFactory.getShopBlueprint1(500, 10));

        enemyEnts.add(UnitFactory.getEnemyBlueprint1(300, 50, 5));
        enemyEnts.add(UnitFactory.getEnemyBlueprint1(180, 450, 1));
        enemyEnts.add(UnitFactory.getEnemyBlueprint1(60, 260, 1));
        enemyEnts.add(UnitFactory.getEnemyBlueprint2(70, 60, 1));
        enemyEnts.add(UnitFactory.getDummyBlueprint(40, 400, 500, 500));
        enemyEnts.add(UnitFactory.getBarrelBlueprint());
        enemyEnts.add(UnitFactory.getBarrelBlueprint());
        enemyEnts.add(UnitFactory.getCampfireBlueprint());
        enemyEnts.add(UnitFactory.getBirdBlueprint());
        // for (int i = 0; i < 3; i++) {
        // for (int j = 0; j < 3; j++) {
        // if (Math.random() < 0.3) {
        // enemyEnts.add(UnitFactory.getEnemyBlueprint2(Consts.SCREEN_WIDTH -
        // 100 + i * 3 * 64,
        // Consts.SCREEN_HEIGHT - 100 + j * 3 * 64, j + 1));
        // } else {
        // enemyEnts.add(UnitFactory.getEnemyBlueprint1(Consts.SCREEN_WIDTH -
        // 100 + i * 3 * 64,
        // Consts.SCREEN_HEIGHT - 100 + j * 3 * 64, j + 1));
        // }
        // }
        // }

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
        for (int i = 0; i < 0 * Keyboard.getKeyCount(); i++) {
            if (input.isKeyDown(i)) {

            }
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
            // TODO make a shop
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
                destination = new Vector2f(mouseX, mouseY);
            }
        }

        if (destination.distance(playerPos) > 3) {
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
        if (input.isKeyPressed(Input.KEY_K)) {
            showTree = !showTree;
        }
        if (input.isKeyPressed(Input.KEY_M)) {
            showMap = !showMap;
        }

        for (Iterator<Entity> iterator = friendlyEnts.iterator(); iterator.hasNext();) {
            Entity ent1 = iterator.next();
            if (ent1.hasComponent(Consts.INPUT)) {
                if (mouseWheel > 0) {
                    CastComponent spell = (CastComponent) (ent1.getComponent(Consts.SPELL));
                    spell.nextSpell();
                    currentSpell = spell.getCurrentSpell();
                } else if (mouseWheel < 0) {
                    CastComponent spell = (CastComponent) (ent1.getComponent(Consts.SPELL));
                    spell.prevSpell();
                    currentSpell = spell.getCurrentSpell();
                }
                if (input.isKeyPressed(Input.KEY_1)) {
                    ((InventoryComponent) ent1.getComponent(Consts.INVENTORY)).nextItem();
                }
                if (input.isKeyPressed(Input.KEY_2)) {
                }
                if (input.isKeyPressed(Input.KEY_3)) {

                }
                if (input.isKeyPressed(Input.KEY_4)) {
                }
                if (input.isKeyPressed(Input.KEY_I)) {
                    ((InventoryComponent) ent1.getComponent(Consts.INVENTORY)).toggleInv();
                    showInventory = ((InventoryComponent) ent1.getComponent(Consts.INVENTORY)).isShown();
                }
                if (input.isKeyPressed(Input.KEY_ESCAPE)) {

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
                if (input.isKeyDown(Input.KEY_E) || input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
                    CastComponent castComp = (CastComponent) (ent1.getComponent(Consts.SPELL));
                    currentSpell = castComp.getCurrentSpell();
                    if (castComp.isReady() && castComp.canCast() && castComp.hasMana()) {
                        switch (currentSpell.getName()) {
                        case "Fireball":
                            castComp.cast();
                            friendlySpellsQueue
                                    .add(SpellFactory.getFireballBlueprint(playerPos.getX() + 32, playerPos.getY() + 32,
                                            playerAngle, castComp.getDamage(), castComp.getDamageType()));
                            break;
                        case "Decrepify":
                            castComp.cast();
                            friendlySpellsQueue.add(SpellFactory.getDecrepifyBlueprint(mouseX, mouseY, 128));
                            break;
                        case "SummonWall":
                            castComp.cast();
                            friendlyEntsQueue.add(UnitFactory.getWallBlueprint(mouseX, mouseY, playerAngle));
                            break;
                        case "ReviveMinion":
                            castComp.cast();
                            Entity closestEntity = getClosestEntity(new Vector2f(mouseX, mouseY), friendlyEnts);
                            closestEntity.broadcast("ress");
                            break;
                        case "HolyShield":
                            castComp.cast();
                            // pass
                            break;
                        case "Teleport":
                            castComp.cast();
                            offset = new Vector2f(-(playerPos.getX() - mouseX), -(playerPos.getY() - mouseY));
                            break;
                        case "Heal":
                            castComp.cast();
                            friendlySpellsQueue.add(SpellFactory.getHealBlueprint(playerPos.getX() + 32,
                                    playerPos.getY() + 32, castComp.getHealing(), "friend"));
                            break;
                        case "ScorchedEarth":
                            castComp.cast();
                            friendlySpellsQueue.add(SpellFactory.getScorchedEarthBlueprint(playerPos.getX() + 32,
                                    playerPos.getY() + 32, castComp.getDamage(), 50));
                            break;
                        case "Weaken":
                            castComp.cast();
                            friendlySpellsQueue.add(SpellFactory.getWeakenBlueprint(mouseX, mouseY, 50));
                            break;
                        case "PoisonArrow":
                            castComp.cast();
                            friendlySpellsQueue.add(SpellFactory.getPoisonArrowBlueprint(playerPos.getX() + 32,
                                    playerPos.getY() + 32, playerAngle, 2, 4, "enemy"));
                            break;
                        case "Bouncer":
                            castComp.cast();
                            friendlySpellsQueue.add(SpellFactory.getBouncerBlueprint(playerPos.getX() + 32,
                                    playerPos.getY() + 32, playerAngle, castComp.getDamage(), 3));
                            break;
                        case "MultiBouncer":
                            castComp.cast();
                            int count = 10;
                            for (int i = 0; i < count; i++) {
                                friendlySpellsQueue.add(SpellFactory.getBouncerBlueprint(playerPos.getX() + 32,
                                        playerPos.getY() + 32, (float) ((2 * Math.PI * i - Math.PI * 0.4f) / count),
                                        castComp.getDamage(), 3));
                            }
                            break;
                        case "SummonKirith":
                            castComp.cast();
                            // kill any previous summons
                            for (Entity ent: friendlyEnts) {
                                if (ent.getName().matches("Kirith")) {
                                    ent.broadcast("kill");
                                }
                            }
                            friendlyEntsQueue.add(UnitFactory.getKirithBlueprint(mouseX, mouseY, castComp.getDamage()));
                            break;
                        default:
                            break;

                        }
                    }
                }
            }
        }

        for (ArrayList<Entity> entities1: ents) {
            for (Entity ent1: entities1) {
                if (!isOutsideScreen(ent1)) {
                    if (ent1.hasComponent(Consts.INPUT) && input.isKeyDown(Input.KEY_A)
                            || !ent1.hasComponent(Consts.INPUT)) {
                        if (ent1.hasComponent(Consts.ATTACK)) {
                            AttackComponent attack = (AttackComponent) ent1.getComponent(Consts.ATTACK);
                            if (attack.isReady() && attack.canAttack()) {
                                for (ArrayList<Entity> entities2: ents) {
                                    for (Entity ent2: entities2) {
                                        if (!ent1.equals(ent2)) {
                                            if (!isOutsideScreen(ent2)) {
                                                // temp
                                                if (ent2.getName().contains("unit") && !ent1.getName().contains("unit")
                                                        || ent1.getName().contains("unit")
                                                                && !ent2.getName().contains("unit")) {
                                                    if (ent2.hasComponent(Consts.HEALTH)) {
                                                        if (colliding(ent1, ent2) || distance(ent1, ent2) <= 64
                                                                + 8 * attack.getRangeAdder()) {
                                                            float healthBefore = ((HealthComponent) ent2
                                                                    .getComponent(Consts.HEALTH)).getHealth();
                                                            for (String atk: attack.attack()) {
                                                                ent2.process(new MessageChannel(ent1, atk));
                                                            }
                                                            float healthAfter = ((HealthComponent) ent2
                                                                    .getComponent(Consts.HEALTH)).getHealth();
                                                            if (healthBefore > 0 && healthAfter <= 0) {
                                                                if (ent2.hasComponent(Consts.LEVEL)) {
                                                                    LevelComponent level = (LevelComponent) ent2
                                                                            .getComponent(Consts.LEVEL);
                                                                    ent1.process(new MessageChannel(ent2,
                                                                            "exp " + level.getExperienceBounty()));
                                                                }
                                                            }
                                                            if (healthBefore > 0) {
                                                                ent1.process(
                                                                        new MessageChannel(ent2,
                                                                                "HPdelta " + (healthBefore
                                                                                        - healthAfter) * attack
                                                                                                .getLifesteal()));
                                                            }
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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
                    CastComponent castComp = (CastComponent) ent1.getComponent(Consts.SPELL);
                    float dx = x - playerPos.getX();
                    float dy = y - playerPos.getY();
                    if (Math.sqrt(dx * dx + dy * dy) <= castComp.getCurrentSpell().getRange()) {
                        if (castComp.isReady() && castComp.canCast() && castComp.hasMana()) {
                            // sound = new
                            // Sound(spell.getCurrentSpell().getSoundPath());
                            // sound.play();
                            float angle = (float) Math.atan2(playerPos.getY() + 32 - y, playerPos.getX() + 32 - x);
                            switch (castComp.getCurrentSpell().getName()) {
                            case "Fireball":
                                castComp.cast();
                                // pass
                                break;
                            case "ReviveMinion":
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
                                        castComp.cast();
                                    }
                                }
                                break;
                            case "PoisonArrow":
                                castComp.cast();
                                enemySpellsQueue.add(SpellFactory.getPoisonArrowBlueprint(x, y, angle, 2, 4, "friend"));
                                break;
                            case "Bouncer":
                                castComp.cast();
                                // pass
                                break;
                            case "Heal":
                                castComp.cast();
                                // pass
                                break;
                            default:
                                break;
                            }
                        }
                        castComp.receive("next spell");
                    }
                }
            }
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
        for (Iterator<Entity> iter = friendlySpellsQueue.iterator(); iter.hasNext();) {
            Entity entity = iter.next();
            friendlySpells.add(entity);
            iter.remove();
        }
        for (Iterator<Entity> iter = enemySpellsQueue.iterator(); iter.hasNext();) {
            Entity entity = iter.next();
            enemySpells.add(entity);
            iter.remove();
        }
        // temp
        for (ArrayList<Entity> spells: spellents) {
            for (Entity spell: spells) {
                if (offset.length() != 0) {
                    ((TransformComponent) spell.getComponent(Consts.TRANSFORM)).move(-offset.getX(), -offset.getY());
                }
                for (Component comp: spell.getComponents()) {
                    if (comp.getID() != Consts.SPRITE) {
                        comp.update();
                    }
                }
            }

        }
        for (ArrayList<Entity> spells: spellents) {
            for (Entity spell1: spells) {
                SpellComponent spellComp = ((SpellComponent) spell1.getComponent(Consts.SPELL));
                if (spellComp.getTargets().contains("enemy")) {
                    ArrayList<Entity> targets = new ArrayList<Entity>();
                    for (Entity enemy: enemyEnts) {
                        if (colliding(enemy, spell1)) {
                            if (!targets.contains(enemy)) {
                                targets.add(enemy);
                            }
                            new Sound(SpellType.valueOf(spell1.getName()).getDeathSoundPath()).play();
                        }
                    }
                    if (targets.size() > 0) {
                        spellComp.handleTargets(targets);
                    }
                }
                if (spellComp.getTargets().contains("friend")) {
                    ArrayList<Entity> targets = new ArrayList<Entity>();
                    for (Entity friend: friendlyEnts) {
                        if (colliding(friend, spell1)) {
                            if (!targets.contains(friend)) {
                                targets.add(friend);
                            }
                            new Sound(SpellType.valueOf(spell1.getName()).getDeathSoundPath()).play();
                        }
                    }
                    if (targets.size() > 0) {
                        spellComp.handleTargets(targets);
                    }
                }
            }
        }
        for (ArrayList<Entity> spells: spellents) {
            for (Iterator<Entity> iter = spells.iterator(); iter.hasNext();) {
                Entity spell = iter.next();
                if (isOutsideScreen(spell) || ((SpellComponent) spell.getComponent(Consts.SPELL)).isFinished()) {
                    iter.remove();
                    // System.out.println(spell.getName() + " destroyed");
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

        drawEnts(g);

        drawSpells(g);

        g.draw(((TransformComponent) friendlyEnts.get(0).getComponent(Consts.TRANSFORM)).getShape());

        offset.x = 0;
        offset.y = 0;

        g.drawString("Status: ", 5, 50 - 15);
        ArrayList<Status> statusList = ((StatusComponent) friendlyEnts.get(0).getComponent(Consts.STATUS))
                .getCurrentStatuses();
        for (int i = 0; i < statusList.size(); i++) {
            g.drawString(statusList.get(i).getStatusInfo(), 5, 50 + 15 * i);
        }

        drawUI(g);

    }

    private void drawEnts(Graphics g) {
        for (Entity ent1: friendlyEnts) {
            for (Component comp: ent1.getComponents()) {
                if (offset.length() != 0) {
                    // if moving, animate walking on the player
                    if (ent1.hasComponent(Consts.INPUT)) {
                        ((SpriteComponent) ent1.getComponent(Consts.SPRITE)).animateWalk();
                    }
                }
                if (!isOutsideScreen(ent1)) {
                    comp.draw();
                }
            }
        }
        for (Entity ent1: enemyEnts) {
            for (Component comp: ent1.getComponents()) {
                if (!isOutsideScreen(ent1)) {
                    comp.draw();
                }
            }
        }
        for (Entity ent1: itemDrops) {
            for (Component comp: ent1.getComponents()) {
                if (!isOutsideScreen(ent1)) {
                    comp.draw();
                }
            }
        }
    }

    private void drawSpells(Graphics g) {

        for (Entity spell: friendlySpells) {
            for (Component comp: spell.getComponents()) {
                comp.draw();
            }
        }
        for (Entity spell: enemySpells) {
            for (Component comp: spell.getComponents()) {
                comp.draw();
            }
        }
    }

    private void drawUI(Graphics g) throws SlickException {
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

        if (showInventory) {
            InventoryComponent inv = ((InventoryComponent) friendlyEnts.get(0).getComponent(Consts.INVENTORY));
            if (inv.isShown()) {
                for (Button frame: inv.getFrames()) {
                    if (frame.contains(mouseX, mouseY)) {
                        inv.drawFrame(frame);
                        break;
                    }
                }
            }
        }
        // TODO ?make this a button as well
        g.drawImage(spellIcons[(SpellType.valueOf(currentSpell.getName()).ordinal())], Consts.SCREEN_WIDTH - 235,
                Consts.SCREEN_HEIGHT - 147, Consts.SCREEN_WIDTH - 176, Consts.SCREEN_HEIGHT - 88, 0, 0,
                spellIcons[(SpellType.valueOf(currentSpell.getName()).ordinal())].getWidth(),
                spellIcons[(SpellType.valueOf(currentSpell.getName()).ordinal())].getHeight());
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
}
