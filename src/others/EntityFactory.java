package others;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import components.items.ArmorComponent;
import components.items.ConsumableComponent;
import components.items.HealthBonusComponent;
import components.items.LifestealComponent;
import components.items.ManaBonusComponent;
import components.items.MovementSpeedBonusComponent;
import components.items.ResistancesBonusComponent;
import components.items.WeaponComponent;
import components.spells.AreaOfEffectComponent;
import components.spells.DurationComponent;
import components.spells.LinearMovement;
import components.spells.SpellComponent;
import components.spells.SpellDamageComponent;
import components.spells.SpellHealComponent;
import components.spells.SpellPeriodicDamageComponent;
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
import enums.ItemType;
import enums.SpellType;
import enums.StatusType;

public class EntityFactory {

    // spells

    public static Entity getSpellBlueprint(String spellName) {
        Entity entity = new Entity(spellName);

        return entity;
    }

    public static Entity getHealBlueprint(float x, float y, float healing, String targets) throws SlickException {
        Entity entity = new Entity("Heal");
        entity.addComponent(new SpriteComponent(entity, "res/images/spells/heal.png", 64, 64));
        entity.addComponent(new TransformComponent(entity, x, y, 32, 32, 1));
        entity.addComponent(new SpellComponent(entity, targets));
        entity.addComponent(new SpellHealComponent(entity, healing));

        return entity;
    }

    public static Entity getPoisonArrowBlueprint(float x, float y, float angle, float damage, float duration,
            String targets) throws SlickException {
        Entity entity = new Entity("PoisonArrow");
        entity.addComponent(new SpriteComponent(entity, "res/images/spells/poisonarrow.png", 64, 64));
        entity.addComponent(new TransformComponent(entity, x, y, 32, 32, 1, angle));
        entity.addComponent(new SpellComponent(entity, targets));
        entity.addComponent(new LinearMovement(entity, 11f, 500, angle));
        entity.addComponent(new SpellPeriodicDamageComponent(entity, damage, duration, "poison"));
        entity.addComponent(new SpellDamageComponent(entity, 1, "poison"));

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

    public static Entity getWeakenBlueprint(float x, float y, float aoe) throws SlickException {
        Entity entity = new Entity("Weaken");
        entity.addComponent(new SpriteComponent(entity, "res/images/spells/weaken.png", aoe * 2, aoe * 2));
        entity.addComponent(new SpellComponent(entity, "enemy"));
        entity.addComponent(new AreaOfEffectComponent(entity, 128, "Weaken", 1, 3));
        entity.addComponent(new TransformComponent(entity, x - aoe, y - aoe, aoe * 2, aoe * 2));
        entity.addComponent(new DurationComponent(entity, 0.33f));

        return entity;
    }

    public static Entity getScorchedEarthBlueprint(float x, float y, float damage, float aoe) throws SlickException {
        Entity entity = new Entity("ScorchedEarth");

        entity.addComponent(new SpriteComponent(entity, "res/images/spells/scorchedearth.png", 64, 64));
        entity.addComponent(new SpellComponent(entity, "enemy", 0.5f));
        entity.addComponent(new AreaOfEffectComponent(entity, aoe, "DoT", 1, 3));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64));
        entity.addComponent(new DurationComponent(entity, 3f));
        entity.addComponent(new SpellDamageComponent(entity, damage / 2, "fire"));

        return entity;

    }

    public static Entity getWallBlueprint(float x, float y, float angle) throws SlickException {
        Entity entity = new Entity("Wall");

        entity.addComponent(new SpriteComponent(entity, "res/images/wall.png", 128, 32));
        entity.addComponent(new HealthComponent(entity, 9000, 9000, -900));
        entity.addComponent(new TransformComponent(entity, x - 32, y - 32, 128, 32, 1, (float) (Math.PI / 2 + angle)));

        return entity;
    }

    public static Entity getDecrepifyBlueprint(float x, float y, float aoe) throws SlickException {
        Entity entity = new Entity("Decrepify");
        entity.addComponent(new SpriteComponent(entity, "res/images/spells/decrepify.png", aoe * 2, aoe * 2));
        entity.addComponent(new SpellComponent(entity, "enemy"));
        entity.addComponent(new TransformComponent(entity, x - aoe, y - aoe, aoe * 2, aoe * 2));
        entity.addComponent(new AreaOfEffectComponent(entity, 64, "Decrepify", 1, 3));
        entity.addComponent(new DurationComponent(entity, 0.33f));

        return entity;
    }

    public static Entity getFireballBlueprint(float x, float y, float angle, float damage, String damageType)
            throws SlickException {
        Entity entity = new Entity("CoFireball");
        entity.addComponent(new SpriteComponent(entity, "res/images/spells/cofireball-1.png", 64, 64));
        entity.addComponent(new TransformComponent(entity, x, y, 32, 32, 1, angle));
        entity.addComponent(new SpellComponent(entity, "enemy"));
        // TODO use the aoe to move it on that circle
        // entity.addComponent(new
        // CircularMovement(entity, 3f, 5, 100));
        entity.addComponent(new LinearMovement(entity, 6f, 500, angle));
        entity.addComponent(new SpellDamageComponent(entity, damage, damageType));
        return entity;
    }

    // items
    public static Entity getCherryBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Cherry");
        entity.addComponent(new SpriteComponent(entity, ItemType.Cherry.getImagePath(), 20, 20));
        entity.addComponent(
                new ConsumableComponent(entity, new StatusType[] { StatusType.HoT }, new float[] { 20 * level }, 1f));

        return entity;
    }

    public static Entity getSwiftnessPotion1(int level) throws SlickException {
        Entity entity = new Entity("Swiftness potion");
        entity.addComponent(new SpriteComponent(entity, ItemType.SwiftnessPotion1.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, new StatusType[] { StatusType.Fury, StatusType.Haste },
                new float[] { 150, 300 }, 3f));

        return entity;
    }

    public static Entity getSwiftnessPotion2(int level) throws SlickException {
        Entity entity = new Entity("Swiftness potion");
        entity.addComponent(new SpriteComponent(entity, ItemType.SwiftnessPotion2.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, new StatusType[] { StatusType.Fury, StatusType.Haste },
                new float[] { 150, 300 }, 3f));

        return entity;
    }

    public static Entity getReplenishingPotionBlueprint1(int level) throws SlickException {
        Entity entity = new Entity("Replenishing potion");
        entity.addComponent(new SpriteComponent(entity, ItemType.ReplenishingPotion1.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, StatusType.MoT, 20 * level, 4));

        return entity;
    }

    public static Entity getReplenishingPotionBlueprint2(int level) throws SlickException {
        Entity entity = new Entity("Replenishing potion");
        entity.addComponent(new SpriteComponent(entity, ItemType.ReplenishingPotion2.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, StatusType.MoT, 20 * level, 4));

        return entity;
    }

    public static Entity getHealingPotionBlueprint1(int level) throws SlickException {
        Entity entity = new Entity("Healing potion");
        entity.addComponent(new SpriteComponent(entity, ItemType.HealingPotion1.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, StatusType.HoT, 15 * level, 4));

        return entity;
    }

    public static Entity getHealingPotionBlueprint2(int level) throws SlickException {
        Entity entity = new Entity("Healing potion");
        entity.addComponent(new SpriteComponent(entity, ItemType.HealingPotion2.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, StatusType.HoT, 15 * level, 4));

        return entity;
    }

    public static Entity getBeltBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Belt");
        entity.addComponent(new SpriteComponent(entity, ItemType.Belt.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 3 * level));
        if (Math.random() < 0.7) {
            entity.addComponent(new HealthBonusComponent(entity, (int) (level + Math.random() * 12 * level)));
        }

        return entity;
    }

    public static Entity getNecklaceBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Necklace");
        entity.addComponent(new SpriteComponent(entity, ItemType.Necklace.getImagePath(), 20, 20));
        if (Math.random() < 1) {
            entity.addComponent(new ManaBonusComponent(entity, (int) (level + Math.random() * 22 * level)));
        }
        if (Math.random() < 0.35) {
            entity.addComponent(new HealthBonusComponent(entity, (int) (level + Math.random() * 11 * level)));
        }

        return entity;
    }

    public static Entity getGlovesBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Gloves");
        entity.addComponent(new SpriteComponent(entity, ItemType.Gloves.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 8 * level));
        if (Math.random() < 0.7) {
            entity.addComponent(new HealthBonusComponent(entity, (int) (level + Math.random() * 22 * level)));
        }
        if (Math.random() < 0.2) {
            entity.addComponent(new LifestealComponent(entity, 15));
        }

        return entity;
    }

    public static Entity getAxeBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Weapon");
        entity.addComponent(new SpriteComponent(entity, ItemType.Axe.getImagePath(), 20, 20));
        entity.addComponent(new WeaponComponent(entity, (float) (1 * level + Math.random() * 2 * level),
                (float) (0.5f + Math.random() * 2 / 10), 0));
        if (Math.random() < 0.4) {
            entity.addComponent(new HealthBonusComponent(entity, (int) (level + Math.random() * 30 * level)));
        }
        if (Math.random() < 0.2) {
            entity.addComponent(new ManaBonusComponent(entity, (int) (level + Math.random() * 15 * level)));
        }
        if (Math.random() < 0.14) {
            entity.addComponent(new ResistancesBonusComponent(entity, 0, (int) (Math.random() * 10 * level),
                    (int) (Math.random() * 15 * level), 0, 0, 0));
        }

        return entity;
    }

    public static Entity getWandBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Weapon");
        entity.addComponent(new SpriteComponent(entity, ItemType.Wand.getImagePath(), 20, 20));
        entity.addComponent(new WeaponComponent(entity, (float) (1 * level + Math.random() * 1 * level),
                (float) (0.9f + Math.random() * 2 / 10), 0));
        if (Math.random() < 0.7) {
            entity.addComponent(new ManaBonusComponent(entity, (int) (level * 5 + Math.random() * 50)));
        }

        return entity;
    }

    public static Entity getBootsBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Boots");
        entity.addComponent(new SpriteComponent(entity, ItemType.Boots.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 12 * level));
        if (Math.random() < 0.4) {
            entity.addComponent(
                    new MovementSpeedBonusComponent(entity, (int) (1 + level / 10 + Math.random() * level / 3)));
        }

        return entity;
    }

    public static Entity getChestBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Chest");
        entity.addComponent(new SpriteComponent(entity, ItemType.Chest.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 60 * level));
        if (Math.random() < 0.4) {
            entity.addComponent(new HealthBonusComponent(entity, 15 * level));
        }

        return entity;
    }

    public static Entity getHelmBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Helm");
        entity.addComponent(new SpriteComponent(entity, ItemType.Helm.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 45 * level));
        if (Math.random() < 0.5) {
            entity.addComponent(new HealthBonusComponent(entity, 5 * level));
        }

        return entity;
    }

    public static Entity getShieldBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Shield");
        entity.addComponent(new SpriteComponent(entity, ItemType.Shield.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 75 * level));
        if (Math.random() < 0.3) {
            entity.addComponent(new HealthBonusComponent(entity, 10 * level));
        }

        return entity;
    }

    public static Entity getSpearBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Weapon");
        entity.addComponent(new SpriteComponent(entity, ItemType.Spear.getImagePath(), 20, 20));
        entity.addComponent(new WeaponComponent(entity, (int) (3 * level + Math.random() * 2),
                (float) (1.7f + Math.random() * 5 / 10), 2));

        return entity;
    }

    public static Entity getSwordBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Weapon");
        entity.addComponent(new SpriteComponent(entity, ItemType.Sword.getImagePath(), 20, 20));
        entity.addComponent(new WeaponComponent(entity, (int) (2 * level + Math.random() * 1),
                (float) (0.9f + Math.random() * 3 / 10), 1));
        if (Math.random() < 0.2) {
            entity.addComponent(new HealthBonusComponent(entity, level * 5));
        }

        return entity;
    }

    // enemies
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
        entity.addComponent(new ManaComponent(entity, 100 + 30 * level, 100 + 30 * level, 5));
        SpellType[] spells = { SpellType.ReviveMinion, SpellType.Fireball, SpellType.PoisonArrow };
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
