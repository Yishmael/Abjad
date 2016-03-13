package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ImpactComponent implements Component {
    private int id = Consts.IMPACT;
    private Entity self;
    private boolean hasSplit = false;

    private boolean impact = false;

    public ImpactComponent(Entity self) {
        this.self = self;
    }

    public void process(MessageChannel channel) {
    }

    public void receive(String command) {
        String str = command;
        if (str.matches("added " + Consts.SPLIT)) {
            hasSplit = true;
            return;
        }
        if (str.matches("impact")) {
            impact = true;
            return;
        }
    }

    public void update() {
        if (impact) {
            if (hasSplit) {
                self.broadcast("split");
            }
        }
    }

    public int getID() {
        return id;
    }

}
