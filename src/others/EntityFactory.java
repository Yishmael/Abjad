package others;

import java.time.LocalTime;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import components.AttackComponent;
import components.CastComponent;
import components.CollisionComponent;
import components.ComputerMovementComponent;
import components.DefenseComponent;
import components.HealthComponent;
import components.InventoryComponent;
import components.LevelComponent;
import components.ManaComponent;
import components.ResistanceComponent;
import components.SpriteComponent;
import components.StatusComponent;
import components.TransformComponent;
import components.YieldComponent;
import components.items.ArmorComponent;
import components.items.ConsumableComponent;
import components.items.HealthBonusComponent;
import components.items.ManaBonusComponent;
import components.items.MovementSpeedBonusComponent;
import components.items.WeaponComponent;
import enums.ItemType;
import enums.SpellType;

public class EntityFactory {

    // items
    public Entity getCherryBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Cherry");
        entity.addComponent(new SpriteComponent(entity, ItemType.Cherry.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, 10 * level, "hot", 1f));
        
        return entity;
    }
    public Entity getReplenishingPotionBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Replenishing potion");
        entity.addComponent(new SpriteComponent(entity, ItemType.ReplenishingPotion.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, 20 * level, "mot", 4));

        return entity;
    }

    public Entity getHealingPotionBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Healing potion");
        entity.addComponent(new SpriteComponent(entity, ItemType.HealingPotion.getImagePath(), 20, 20));
        entity.addComponent(new ConsumableComponent(entity, 15 * level, "hot", 4));

        return entity;
    }

    public Entity getBeltBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Belt");
        entity.addComponent(new SpriteComponent(entity, ItemType.Belt.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 6 * level));
        if (Math.random() < 0.7) {
            entity.addComponent(new HealthBonusComponent(entity, (int) (level + Math.random() * 12 * level)));
        }

        return entity;
    }

    public Entity getNecklaceBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Necklace");
        entity.addComponent(new SpriteComponent(entity, ItemType.Necklace.getImagePath(), 20, 20));
        if (Math.random() < 0.7) {
            entity.addComponent(new ManaBonusComponent(entity, (int) (level + Math.random() * 22 * level)));
        }
        if (Math.random() < 0.35) {
            entity.addComponent(new HealthBonusComponent(entity, (int) (level + Math.random() * 11 * level)));
        }

        return entity;
    }

    public Entity getGlovesBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Gloves");
        entity.addComponent(new SpriteComponent(entity, ItemType.Gloves.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 8 * level));
        if (Math.random() < 0.7) {
            entity.addComponent(new HealthBonusComponent(entity, (int) (level + Math.random() * 22 * level)));
        }

        return entity;
    }

    public Entity getAxeBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Weapon");
        entity.addComponent(new SpriteComponent(entity, ItemType.Axe.getImagePath(), 20, 20));
        entity.addComponent(new WeaponComponent(entity, (float) (1 * level + Math.random() * 2 * level),
                (float) (0.5f + Math.random() * 2 / 10), 0));
        if (Math.random() < 0.6) {
            entity.addComponent(new HealthBonusComponent(entity, (int) (level + Math.random() * 30 * level)));
        }
        if (Math.random() < 0.3) {
            entity.addComponent(new ManaBonusComponent(entity, (int) (level + Math.random() * 15 * level)));
        }

        return entity;
    }

    public Entity getBranchBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Weapon");
        entity.addComponent(new SpriteComponent(entity, ItemType.Branch.getImagePath(), 20, 20));
        entity.addComponent(new WeaponComponent(entity, (float) (1 * level + Math.random() * 1 * level),
                (float) (1.25f + Math.random() * 2 / 10), 0));
        if (Math.random() < 0.7) {
            entity.addComponent(new ManaBonusComponent(entity, (int) (level * 10 + Math.random() * 50)));
        }

        return entity;
    }

    public Entity getBootsBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Boots");
        entity.addComponent(new SpriteComponent(entity, ItemType.Boots.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 20 * level));
        if (Math.random() < 0.5) {
            entity.addComponent(
                    new MovementSpeedBonusComponent(entity, (int) (1 + level / 10 + Math.random() * level / 3)));
        }

        return entity;
    }

    public Entity getChestBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Chest");
        entity.addComponent(new SpriteComponent(entity, ItemType.Chest.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 150 * level));
        if (Math.random() < 0.4) {
            entity.addComponent(new HealthBonusComponent(entity, 15 * level));
        }

        return entity;
    }

    public Entity getHelmetBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Helmet");
        entity.addComponent(new SpriteComponent(entity, ItemType.Helmet.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 30 * level));
        if (Math.random() < 0.4) {
            entity.addComponent(new HealthBonusComponent(entity, 5 * level));
        }

        return entity;
    }

    public Entity getShieldBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Shield");
        entity.addComponent(new SpriteComponent(entity, ItemType.Shield.getImagePath(), 20, 20));
        entity.addComponent(new ArmorComponent(entity, 175 * level));
        if (Math.random() < 0.44) {
            entity.addComponent(new HealthBonusComponent(entity, 10 * level));
        }

        return entity;
    }

    public Entity getSpearBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Weapon");
        entity.addComponent(new SpriteComponent(entity, ItemType.Spear.getImagePath(), 20, 20));
        entity.addComponent(new WeaponComponent(entity, (int) (3 * level + Math.random() * 2),
                (float) (1.7f + Math.random() * 5 / 10), 2));

        return entity;
    }

    public Entity getSwordBlueprint(int level) throws SlickException {
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
    public Entity getEnemyBlueprint1(float x, float y, int level) throws SlickException {
        Entity entity = new Entity("Common unit " + LocalTime.now().getNano());
        entity.addComponent(new SpriteComponent(entity, "images/ifrit.png", 64, 64));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet("images/player1.png", 64, 64), 220, false));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64, 0.6f + (level - 1) / level));
        entity.addComponent(new HealthComponent(entity, 1 + level * 1, 1 + level * 1, 0.15f));
        entity.addComponent(new ComputerMovementComponent(entity, 1));
        entity.addComponent(new AttackComponent(entity, 2.5f * level, 1f));
        entity.addComponent(new DefenseComponent(entity, 0.7f * level));
        entity.addComponent(new ResistanceComponent(entity, 0, 30, 0, 0, 0, 0));
        entity.addComponent(new LevelComponent(entity, level));
        ArrayList<Entity> drops = new ArrayList<Entity>();
        drops.add(getAxeBlueprint(level));
        entity.addComponent(new YieldComponent(entity, drops));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new CollisionComponent(entity));

        return entity;
    }

    public Entity getEnemyBlueprint2(float x, float y, int level) throws SlickException {
        Entity entity = new Entity("Bigger unit");
        entity.addComponent(new SpriteComponent(entity, "images/bahamut.png", 64, 64));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet("images/player1.png", 64, 64), 220, false));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64, 1 + (level - 1) / level));
        entity.addComponent(new HealthComponent(entity, 2 + 1 * level, 2 + 1 * level, 0.3f));
        entity.addComponent(new ComputerMovementComponent(entity, 1));
        entity.addComponent(new AttackComponent(entity, 3 * level, 0.8f));
        entity.addComponent(new DefenseComponent(entity, 1.3f * level));
        entity.addComponent(new ResistanceComponent(entity, 0, 30, 0, 0, 0, 0));
        entity.addComponent(new LevelComponent(entity, level));
        entity.addComponent(new ManaComponent(entity, 100 + 30 * level, 100 + 30 * level, 5));
        SpellType[] spells = { SpellType.ReviveMinion, SpellType.Fireball, SpellType.PoisonArrow };
        entity.addComponent(new CastComponent(entity, spells));
        ArrayList<Entity> drops = new ArrayList<Entity>();
        drops.add(getBootsBlueprint(level));
        entity.addComponent(new YieldComponent(entity, drops));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new CollisionComponent(entity));

        return entity;
    }

    // environment
    public Entity getBarrelBlueprint() throws SlickException {
        Entity entity = new Entity("Barrel");
        entity.addComponent(new SpriteComponent(entity, "images/barrel1.png", 90, 90));
        entity.addComponent(new TransformComponent(entity, (float) (Math.random() * 400 + 50),
                (float) (Math.random() * 400 + 50), 70, 80, 0.6f));
        entity.addComponent(new HealthComponent(entity, 500, 500));
        entity.addComponent(new ResistanceComponent(entity, -400, 0, 0, 0, 0, 0));

        return entity;
    }

    public Entity getBirdBlueprint() throws SlickException {
        Entity entity = new Entity("Bird");
        entity.addComponent(
                new SpriteComponent(entity, new SpriteSheet("images/bird1.png", 64, 64), 200, false, 64, 64));
        entity.addComponent(new TransformComponent(entity, 550, 225, 64, 64));

        return entity;
    }

    public Entity getCampfireBlueprint() throws SlickException {
        Entity entity = new Entity("Campfire");
        entity.addComponent(
                new SpriteComponent(entity, new SpriteSheet("images/campfire1.png", 64, 64), 80, true, 64, 64));
        entity.addComponent(new TransformComponent(entity, 440, 130, 50, 50));
        entity.addComponent(new HealthComponent(entity, 100, 100, -0.00f));
        entity.addComponent(new AttackComponent(entity, 1.7f, 0.15f));

        return entity;
    }

    public Entity getShopBlueprint1(float x, float y) throws SlickException {
        Entity entity = new Entity("Shop");
        entity.addComponent(new SpriteComponent(entity, "images/shop1.png", 128, 128));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet("images/player1.png", 64, 64), 220, false));
        entity.addComponent(new InventoryComponent(entity, null));
        entity.addComponent(new TransformComponent(entity, x, y, 128, 128));
        entity.addComponent(new CollisionComponent(entity));

        return entity;
    }

}
