package others;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import components.CollisionComponent;
import components.CombatComponent;
import components.HealthComponent;
import components.InventoryComponent;
import components.ManaComponent;
import components.MovementComponent;
import components.SpriteComponent;
import components.TransformComponent;

public class EntityFactory {

    public Entity getCampfireBlueprint() throws SlickException {
        Entity entity = new Entity("Campfire");
        entity.addComponent(new SpriteComponent(entity, new SpriteSheet("images/campfire1.png", 64, 64), 80, true));
        entity.addComponent(new TransformComponent(entity, 260, 70));
        entity.addComponent(new HealthComponent(entity, 100, 100, -0.05f));
        entity.addComponent(new CombatComponent(entity, 0.75f, 0.05f));
        
        return entity;
    }

    public Entity getBarrelBlueprint() throws SlickException {
        Entity entity = new Entity("Barrel");
        entity.addComponent(new SpriteComponent(entity, "images/barrel1.png"));
        entity.addComponent(new TransformComponent(entity, (float)(Math.random() * 400 + 50), (float) (Math.random()* 400 + 50), 0.6f));
        entity.addComponent(new HealthComponent(entity, 100, 100));
        
        return entity;
    }

    public Entity getBirdBlueprint() throws SlickException {
        Entity entity = new Entity("Bird");
        entity.addComponent(new SpriteComponent(entity, new SpriteSheet("images/bird1.png", 64, 64), 200, false));
        entity.addComponent(new TransformComponent(entity, 550, 225));

        return entity;
    }
    
    public Entity getEnemyBlueprint() throws SlickException {
        Entity entity = new Entity("Enemy bot");
        entity.addComponent(new SpriteComponent(entity, "images/bahamut.png"));
        entity.addComponent(new HealthComponent(entity, 421, 500, 7));
        entity.addComponent(new MovementComponent(entity, 1));
        entity.addComponent(new ManaComponent(entity, 500, 700, 10));
        entity.addComponent(new CombatComponent(entity, 25, 0.8f));
        entity.addComponent(new InventoryComponent(entity, "images/inventory2.png"));
        entity.addComponent(new TransformComponent(entity, 110, 200, 1.2f));
        entity.addComponent(new CollisionComponent(entity));
        
        return entity;
    }
    
    
    
    
}
