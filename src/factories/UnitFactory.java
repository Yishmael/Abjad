package factories;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import components.units.AttackComponent;
import components.units.CastComponent;
import components.units.CollisionComponent;
import components.units.ComputerMovementComponent;
import components.units.DefenseComponent;
import components.units.HealthComponent;
import components.units.InventoryComponent;
import components.units.LevelComponent;
import components.units.ManaComponent;
import components.units.ResistanceComponent;
import components.units.SpriteComponent;
import components.units.StatusComponent;
import components.units.TransformComponent;
import components.units.YieldComponent;
import others.Entity;
import others.Spell;

public class UnitFactory {

    public static Entity getUnitBlueprint(String unitName) {
        Entity entity = new Entity(unitName);

        return entity;
    }

    public static Entity getKirithBlueprint(float x, float y, float damage) throws SlickException {
        Entity entity = new Entity("Kirith");
        entity.addComponent(new SpriteComponent(entity, "res/images/kirith.png", 64, 64));
        entity.addComponent(new HealthComponent(entity, 100000, 100000, -10000));
        // SpellType[] spells = { SpellType.MultiBouncer };
        // entity.addComponent(new CastComponent(entity, spells));
        // entity.addComponent(new ManaComponent(entity, 200, 200));
        entity.addComponent(new ComputerMovementComponent(entity, 0.5f, 300, 5));
        entity.addComponent(new AttackComponent(entity, damage, 0.7f));
        entity.addComponent(new TransformComponent(entity, x - 32, y - 32, 64, 64));

        return entity;
    }

    public static Entity getWallBlueprint(float x, float y, float angle) throws SlickException {
        Entity entity = new Entity("Wall");

        entity.addComponent(new SpriteComponent(entity, "res/images/wall.png", 128, 32));
        entity.addComponent(new HealthComponent(entity, 9000, 9000, -900));
        entity.addComponent(new TransformComponent(entity, x - 32, y - 32, 128, 32, 1, (float) (Math.PI / 2 + angle)));

        return entity;
    }

    public static Entity getEnemyBlueprint1(float x, float y, int level) throws SlickException {
        Entity entity = new Entity("Common unit " + (int) (Math.random() * 100000));
        entity.addComponent(new SpriteComponent(entity, "res/images/ifrit.png", 64, 64));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet("res/images/player1.png", 64, 64), 220, false));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64, 0.6f + (level - 1) / level));
        entity.addComponent(new HealthComponent(entity, 1 + level * 1, 1 + level * 1, 0f));
        entity.addComponent(new ComputerMovementComponent(entity, 2.2f, 200, 10));
        entity.addComponent(new AttackComponent(entity, 0.8f * level, 1f));
        entity.addComponent(new DefenseComponent(entity, 0.7f * level));
        entity.addComponent(new ResistanceComponent(entity, 0, 30, 0, 0, 0, 0));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new LevelComponent(entity, level));
        entity.addComponent(new YieldComponent(entity));
        entity.addComponent(new CollisionComponent(entity));

        return entity;
    }

    public static Entity getEnemyBlueprint2(float x, float y, int level) throws SlickException {
        Entity entity = new Entity("Bigger unit " + (int) (Math.random() * 100000));
        entity.addComponent(new SpriteComponent(entity, "res/images/bahamut.png", 64, 64));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet("res/images/player1.png", 64, 64), 220, false));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64, 1 + (level - 1) / level));
        entity.addComponent(new HealthComponent(entity, 2 + 1 * level, 2 + 1 * level, 0f));
        entity.addComponent(new ComputerMovementComponent(entity, 1.6f, 450, 300));
        entity.addComponent(new AttackComponent(entity, level, 0.8f));
        entity.addComponent(new DefenseComponent(entity, 1.3f * level));
        entity.addComponent(new ResistanceComponent(entity, 0, 30, 0, 0, 0, 0));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new LevelComponent(entity, level));
        entity.addComponent(new ManaComponent(entity, 50 + 30 * level, 50 + 30 * level, level / 10));
        Spell[] spells = { Spell.getSpellData("ReviveMinion"), Spell.getSpellData("Fireball"),
                Spell.getSpellData("PoisonArrow") };
        entity.addComponent(new CastComponent(entity, spells));
        entity.addComponent(new YieldComponent(entity));
        entity.addComponent(new CollisionComponent(entity));

        return entity;
    }

    public static Entity getDummyBlueprint(float x, float y, float health, float mana) throws SlickException {
        Entity entity = new Entity("Dummy unit " + (int) (Math.random() * 100000));
        entity.addComponent(new SpriteComponent(entity, "res/images/items/boulder1.png", 64, 64));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet("res/images/player1.png", 64, 64), 220, false));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64, 1));
        entity.addComponent(new HealthComponent(entity, health));
        entity.addComponent(new DefenseComponent(entity, 0));
        entity.addComponent(new ResistanceComponent(entity, 0, 0, 0, 0, 0, 0));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new ManaComponent(entity, mana));
        entity.addComponent(new CollisionComponent(entity));

        return entity;
    }

    // environment
    public static Entity getBarrelBlueprint() throws SlickException {
        Entity entity = new Entity("Barrel" + (int) (Math.random() * 100000));
        entity.addComponent(new SpriteComponent(entity, "res/images/barrel1.png", 90, 90));
        entity.addComponent(new TransformComponent(entity, (float) (Math.random() * 400 + 50),
                (float) (Math.random() * 400 + 50), 70, 80, 0.6f));
        entity.addComponent(new HealthComponent(entity, 500, 500));
        entity.addComponent(new DefenseComponent(entity, -1000));
        entity.addComponent(new ResistanceComponent(entity, -1000, -1000, -1000, -1000, 100, 100));

        return entity;
    }

    public static Entity getBirdBlueprint() throws SlickException {
        Entity entity = new Entity("Bird" + (int) (Math.random() * 100000));
        entity.addComponent(
                new SpriteComponent(entity, new SpriteSheet("res/images/bird1.png", 64, 64), 200, false, 64, 64));
        entity.addComponent(new TransformComponent(entity, 550, 225, 64, 64));

        return entity;
    }

    public static Entity getCampfireBlueprint() throws SlickException {
        Entity entity = new Entity("Campfire" + (int) (Math.random() * 100000));
        entity.addComponent(
                new SpriteComponent(entity, new SpriteSheet("res/images/campfire1.png", 64, 64), 80, true, 64, 64));
        entity.addComponent(new TransformComponent(entity, 440, 130, 50, 50));
        entity.addComponent(new HealthComponent(entity, 100, 100, -0.00f));
        entity.addComponent(new AttackComponent(entity, 1.7f, 0.15f));

        return entity;
    }

    public static Entity getShopBlueprint1(float x, float y) throws SlickException {
        Entity entity = new Entity("Shop" + (int) (Math.random() * 100000));
        entity.addComponent(new SpriteComponent(entity, "res/images/shop1.png", 128, 128));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet(res/images/player1.png", 64, 64), 220, false));
        entity.addComponent(new InventoryComponent(entity, null));
        entity.addComponent(new TransformComponent(entity, x, y, 128, 128));
        entity.addComponent(new CollisionComponent(entity));

        return entity;
    }

}
