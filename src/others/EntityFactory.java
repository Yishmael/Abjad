package others;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import components.CollisionComponent;
import components.CombatComponent;
import components.ComputerMovementComponent;
import components.HealthComponent;
import components.LevelComponent;
import components.ManaComponent;
import components.SpellComponent;
import components.SpriteComponent;
import components.StatusComponent;
import components.TransformComponent;
import components.YieldComponent;
import enums.ItemType;
import enums.SpellType;

public class EntityFactory {
    public Entity getBarrelBlueprint() throws SlickException {
        Entity entity = new Entity("Barrel");
        entity.addComponent(new SpriteComponent(entity, "images/barrel1.png", 90, 90));
        entity.addComponent(new TransformComponent(entity, (float) (Math.random() * 400 + 50),
                (float) (Math.random() * 400 + 50), 70, 80, 0.6f));
        entity.addComponent(new HealthComponent(entity, 500, 500));

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
        entity.addComponent(new HealthComponent(entity, 100, 100, -0.05f));
        entity.addComponent(new CombatComponent(entity, 1.7f, 0.15f));

        return entity;
    }

    public Entity getEnemyBlueprint1(float x, float y) throws SlickException {
        Entity entity = new Entity("Common unit");
        entity.addComponent(new SpriteComponent(entity, "images/ifrit.png", 64, 64));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet("images/player1.png", 64, 64), 220, false));
        entity.addComponent(new HealthComponent(entity, (float) (Math.random() * 70) + 100, 170, 2.2f));
        entity.addComponent(new ComputerMovementComponent(entity, 1));
        entity.addComponent(new ManaComponent(entity, 300, 300, 5));
        entity.addComponent(new CombatComponent(entity, 10, 1f));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64, 0.8f));
        SpellType[] spells = { SpellType.PoisonArrow };
        entity.addComponent(new SpellComponent(entity, spells));
        entity.addComponent(new LevelComponent(entity, 5));
        ItemType[] drops = { ItemType.Branch };
        entity.addComponent(new YieldComponent(entity, drops));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new CollisionComponent(entity));

        return entity;
    }
    public Entity getEnemyBlueprint2(float x, float y) throws SlickException {
        Entity entity = new Entity("Enemy bot");
        entity.addComponent(new SpriteComponent(entity, "images/bahamut.png", 64, 64));
        // entity.addComponent(new SpriteComponent(entity, new
        // SpriteSheet("images/player1.png", 64, 64), 220, false));
        entity.addComponent(new HealthComponent(entity, (float) (Math.random() * 300) + 100, 400, 3.2f));
        entity.addComponent(new ComputerMovementComponent(entity, 1));
        entity.addComponent(new CombatComponent(entity, 20, 0.8f));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64, 1.2f));
        // entity.addComponent(new InputComponent(entity));
        entity.addComponent(new LevelComponent(entity, 9));
        ItemType[] drops = { ItemType.Axe };
        entity.addComponent(new YieldComponent(entity, drops));
        entity.addComponent(new StatusComponent(entity));
        entity.addComponent(new CollisionComponent(entity));
        
        return entity;
    }

}
