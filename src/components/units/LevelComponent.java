package components.units;

import org.lwjgl.Sys;

import components.Component;
import loaders.FontLoader;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class LevelComponent implements Component {
    public long id = Consts.LEVEL;

    private Entity self;
    private int level;
    private float experience;
    private float maxExperience;
    private FontLoader font = new FontLoader("cac", 23, java.awt.Color.WHITE);
    private long levelUpTime;

    public LevelComponent(Entity self, int level) {
        this.self = self;
        this.level = level;
        experience = 0;
        maxExperience = calculateMaxExperience(level);
    }

    @Override
    public void process(MessageChannel channel) {
        String str = channel.getCommand();
        receive(str);
    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("exp [0-9]+[.]?[0-9]*")) {
            experience += Float.parseFloat(str.substring(4));
            System.out.println("+" + (int) Float.parseFloat(str.substring(4)) + " experience");
            while (experience >= maxExperience) {
                level++;
                maxExperience = calculateMaxExperience(level);
                System.out.println("Level " + level + "!");
                self.broadcast("leveledup");
                levelUpTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
            }
        } else if (str.matches("lvlup")) {
            level++;
            maxExperience = calculateMaxExperience(level);
            // TODO fix the experience when forcing a level up
            experience = maxExperience;
            System.out.println("Level " + level + "!");
            self.broadcast("leveledup");
        }
    }

    public float calculateMaxExperience(int level) {
        if (level < 1) {
            return 0;
        }
        return maxExperience + (float) (100 * (level + 1) + Math.pow(1.25f, (level + 1)));
    }

    // temp
    @Override
    public void draw() {
        if (levelUpTime > 0) {
            // display the text for 1.5 seconds
            if ((Sys.getTime() * 1000) / Sys.getTimerResolution() - levelUpTime <= 1500) {
                font.draw(Consts.SCREEN_WIDTH / 2 - 32, Consts.SCREEN_HEIGHT / 2 - (32 + 32 + 25), "Level up.");
            } else {
                levelUpTime = 0;
            }
        }
    }

    @Override
    public void update() {

    }

    public float getRequiredExperience(int level) {
        if (level < 2) {
            return 0;
        }
        return getRequiredExperience(level - 1) + (float) (100 * (level) + Math.pow(1.25f, (level)));
    }

    public float getExperienceBounty() {
        return level * 50;
    }

    public int getLevel() {
        return level;
    }

    public float getExperience() {
        return experience;
    }

    public float getMaxExperience() {
        return maxExperience;
    }

    @Override
    public long getID() {
        return id;
    }
}
