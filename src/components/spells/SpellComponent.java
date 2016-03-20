package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;;

public class SpellComponent implements Component {
    private int id = Consts.SPELL;
    private Entity self;

    private boolean finished = false;

    private String targets;

    public SpellComponent(Entity self, String targets) {
        this.self = self;
        this.targets = targets;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("finished")) {
            finished = true;
            return;
        }
    }

    @Override
    public void update() {

    };

    public boolean isFinished() {
        return finished;
    }

    public String getTargets() {
        return targets;
    }

    @Override
    public int getID() {
        return id;
    }
}
