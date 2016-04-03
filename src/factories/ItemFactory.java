package factories;

import org.newdawn.slick.SlickException;

import components.items.ArmorComponent;
import components.items.ConsumableComponent;
import components.items.ElementalAttackDamageBonusComponent;
import components.items.HealthBonusComponent;
import components.items.LifestealComponent;
import components.items.ManaBonusComponent;
import components.items.MovementSpeedBonusComponent;
import components.items.ResistancesBonusComponent;
import components.items.WeaponComponent;
import components.units.SpriteComponent;
import enums.ItemType;
import enums.StatusType;
import others.Entity;

public class ItemFactory {

    public static Entity getItemBlueprint(String itemName) {
        Entity entity = new Entity(itemName);

        return entity;
    }

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
                new float[] { 150, 200 }, 3f));

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
        if (Math.random() < 0.1) {
            entity.addComponent(new ManaBonusComponent(entity, (int) (level + Math.random() * 15 * level)));
        }
        if (Math.random() < 0.14) {
            entity.addComponent(new ResistancesBonusComponent(entity, 0, (int) (Math.random() * 10 * level),
                    (int) (Math.random() * 15 * level), 0, 0, 0));
        }
        entity.addComponent(new ElementalAttackDamageBonusComponent(entity, 0, 1 + (int) (level / 5), 0, 0, 0, 0));

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
        entity.addComponent(new WeaponComponent(entity, (int) (2 * level + Math.random() * 2),
                (float) (1.7f + Math.random() * 5 / 10), 2));

        return entity;
    }

    public static Entity getSwordBlueprint(int level) throws SlickException {
        Entity entity = new Entity("Weapon");
        entity.addComponent(new SpriteComponent(entity, ItemType.Sword.getImagePath(), 20, 20));
        entity.addComponent(new WeaponComponent(entity, (int) (1.3f * level + Math.random() * 1),
                (float) (0.9f + Math.random() * 3 / 10), 1));
        if (Math.random() < 0.2) {
            entity.addComponent(new HealthBonusComponent(entity, level * 5));
        }

        return entity;
    }

}
