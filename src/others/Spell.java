package others;

import enums.SpellType;

public class Spell {

    private float damage, healing, cooldown, manaCost, range, projectileSpeed;
    private String name, damageType, imagePath, iconPath, creationSoundPath, deathSoundPath;

    public static Spell getSpellData(String spellName) {
        Spell info = new Spell();

        if (spellName.equalsIgnoreCase("Teleport")) {
            info.setName(spellName);
            info.setIconPath(SpellType.Teleport.getIconPath());
            info.setImagePath(SpellType.Teleport.getImagePath());
            info.setManaCost(10);
            info.setCooldown(0.5f);
            info.setCreationSoundPath("res/sounds/063.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        } else if (spellName.equalsIgnoreCase("Fireball")) {
            info.setName(spellName);
            info.setIconPath(SpellType.Fireball.getIconPath());
            info.setImagePath(SpellType.Fireball.getImagePath());
            info.setManaCost(6);
            info.setDamage(3);
            info.setDamageType("fire");
            info.setCooldown(0.6f);
            info.setProjectileSpeed(7);
            info.setRange(350);
            info.setCreationSoundPath("res/sounds/067.ogg");
            info.setDeathSoundPath("res/sounds/068.ogg");
        } else if (spellName.equalsIgnoreCase("Bouncer")) {
            info.setName(spellName);
            info.setIconPath(SpellType.Bouncer.getIconPath());
            info.setImagePath(SpellType.Bouncer.getImagePath());
            info.setDamage(2);
            info.setDamageType("arcane");
            info.setManaCost(4);
            info.setRange(450);
            info.setCooldown(0.2f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/020.ogg");
        } else if (spellName.equalsIgnoreCase("MultiBouncer")) {
            info.setName(spellName);
            info.setIconPath(SpellType.MultiBouncer.getIconPath());
            info.setImagePath(SpellType.MultiBouncer.getImagePath());
            info.setDamage(1);
            info.setDamageType("arcane");
            info.setManaCost(10);
            info.setRange(450);
            info.setCooldown(0.2f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/020.ogg");
        } else if (spellName.equalsIgnoreCase("ScorchedEarth")) {
            info.setName(spellName);
            info.setIconPath(SpellType.ScorchedEarth.getIconPath());
            info.setImagePath(SpellType.ScorchedEarth.getImagePath());
            info.setManaCost(4);
            // info.setDamage(1);
            // info.setDamageType("fire);
            // info.setDuration(3);
            info.setRange(200);
            info.setCooldown(4f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/068.ogg");
        } else if (spellName.equalsIgnoreCase("ReviveMinion")) {
            info.setName(spellName);
            info.setIconPath(SpellType.ReviveMinion.getIconPath());
            info.setImagePath(SpellType.ReviveMinion.getImagePath());
            info.setManaCost(15);
            info.setCooldown(3f);
            info.setRange(150);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        } else if (spellName.equalsIgnoreCase("SummonKirith")) {
            info.setName(spellName);
            info.setIconPath(SpellType.SummonKirith.getIconPath());
            info.setImagePath(SpellType.SummonKirith.getImagePath());
            info.setManaCost(15);
            info.setRange(50);
            info.setCooldown(1f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        } else if (spellName.equalsIgnoreCase("SummonWall")) {
            info.setName(spellName);
            info.setIconPath(SpellType.SummonWall.getIconPath());
            info.setImagePath(SpellType.SummonWall.getImagePath());
            info.setManaCost(15);
            info.setRange(50);
            info.setCooldown(1f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        } else if (spellName.equalsIgnoreCase("Heal")) {
            info.setName(spellName);
            info.setIconPath(SpellType.Heal.getIconPath());
            info.setImagePath(SpellType.Heal.getImagePath());
            info.setManaCost(12);
            info.setRange(50);
            info.setCooldown(0.5f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/039.ogg");
        } else if (spellName.equalsIgnoreCase("HolyShield")) {
            info.setName(spellName);
            info.setIconPath(SpellType.HolyShield.getIconPath());
            info.setImagePath(SpellType.HolyShield.getImagePath());
            info.setManaCost(20);
            info.setRange(80);
            info.setCooldown(1f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        } else if (spellName.equalsIgnoreCase("Weaken")) {
            info.setName(spellName);
            info.setIconPath(SpellType.Weaken.getIconPath());
            info.setImagePath(SpellType.Weaken.getImagePath());
            info.setManaCost(10);
            info.setRange(300);
            info.setCooldown(0.66f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        } else if (spellName.equalsIgnoreCase("Decrepify")) {
            info.setName(spellName);
            info.setIconPath(SpellType.Decrepify.getIconPath());
            info.setImagePath(SpellType.Decrepify.getImagePath());
            info.setManaCost(10);
            info.setRange(300);
            info.setCooldown(0.66f);
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        } else if (spellName.equalsIgnoreCase("PoisonArrow")) {
            info.setName(spellName);
            info.setIconPath(SpellType.PoisonArrow.getIconPath());
            info.setImagePath(SpellType.PoisonArrow.getImagePath());
            info.setManaCost(5);
            info.setDamageType("poison");
            info.setCooldown(0.34f);
            info.setProjectileSpeed(10);
            info.setRange(150);
            info.setCreationSoundPath("res/sounds/024.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        } else {
            info.setName("Null");
            info.setCooldown(9999f);
            info.setIconPath(SpellType.Null.getIconPath());
            info.setImagePath(SpellType.Null.getImagePath());
            info.setCreationSoundPath("res/sounds/000.ogg");
            info.setDeathSoundPath("res/sounds/000.ogg");
        }

        return info;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getHealing() {
        return healing;
    }

    public void setHealing(float healing) {
        this.healing = healing;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public float getManaCost() {
        return manaCost;
    }

    public void setManaCost(float manaCost) {
        this.manaCost = manaCost;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(float projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getCreationSoundPath() {
        return creationSoundPath;
    }

    public void setCreationSoundPath(String creationSoundPath) {
        this.creationSoundPath = creationSoundPath;
    }

    public String getDeathSoundPath() {
        return deathSoundPath;
    }

    public void setDeathSoundPath(String deathSoundPath) {
        this.deathSoundPath = deathSoundPath;
    }

}
