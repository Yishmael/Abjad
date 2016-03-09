package components;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class LevelComponent implements Component {
    public int id = Consts.LEVEL;

    private Entity self;
    private int level;
    private float experience;
    private float maxExperience;

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
                self.broadcast("lvlup");
            }
        }
    }

    public float calculateMaxExperience(int level) {
        if (level < 1) {
            return 0;
        }
        return maxExperience + (float) (100 * (level + 1) + Math.pow(1.25f, (level + 1)));
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    public float getRequiredExperience(int level) {
        if (level < 2) {
            return 0;
        }
        return getRequiredExperience(level - 1) + (float) (100 * (level) + Math.pow(1.25f, (level )));
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
    public int getID() {
        return id;
    }
}
