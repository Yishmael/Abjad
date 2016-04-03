package factories;

import org.newdawn.slick.SlickException;

import components.spells.AreaOfEffectComponent;
import components.spells.BounceComponent;
import components.spells.DurationComponent;
import components.spells.LinearMovement;
import components.spells.SpellComponent;
import components.spells.SpellDamageComponent;
import components.spells.SpellHealComponent;
import components.spells.SpellPeriodicDamageComponent;
import components.units.SpriteComponent;
import components.units.TransformComponent;
import others.Entity;

public class SpellFactory {

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
        entity.addComponent(new AreaOfEffectComponent(entity, aoe, "DoT", damage, 3));
        entity.addComponent(new TransformComponent(entity, x, y, 64, 64));
        entity.addComponent(new DurationComponent(entity, 3f));

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

    public static Entity getBouncerBlueprint(float x, float y, float angle, float damage, int numberOfBounces)
            throws SlickException {
        Entity entity = new Entity("Bouncer");
        entity.addComponent(new SpriteComponent(entity, "res/images/spells/bouncer.png", 64, 64));
        entity.addComponent(new SpellComponent(entity, "enemy"));
        entity.addComponent(new TransformComponent(entity, x, y, 32, 32, 1, angle));
        entity.addComponent(new LinearMovement(entity, 3f, 33300, angle));
        entity.addComponent(new BounceComponent(entity, numberOfBounces));
        entity.addComponent(new SpellDamageComponent(entity, damage, "arcane"));
        return entity;
    }

    public static Entity getFireballBlueprint(float x, float y, float angle, float damage, String damageType)
            throws SlickException {
        Entity entity = new Entity("Fireball");
        entity.addComponent(new SpriteComponent(entity, "res/images/spells/fireball-1.png", 64, 64));
        entity.addComponent(new TransformComponent(entity, x, y, 32, 32, 1, angle));
        entity.addComponent(new SpellComponent(entity, "enemy"));
        // TODO use the aoe to move it on that circle
        // entity.addComponent(new
        // CircularMovement(entity, 3f, 5, 100));
        entity.addComponent(new LinearMovement(entity, 6f, 500, angle));
        entity.addComponent(new SpellDamageComponent(entity, damage, damageType));
        return entity;
    }
}
