package components.spells;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.Sys;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;;

public class SpellComponent implements Component {
    private long id = Consts.SPELL;
    private Entity self;

    private boolean finished = false;
    private boolean hasAoE, hasDuration, canBounce;

    private String targets;
    private ArrayList<String> actions = new ArrayList<String>();
    private float timeBetweedActions;

    private long lastTime, now;

    public SpellComponent(Entity self, String targets, float timeBetweedActions) {
        this.self = self;
        this.targets = targets;
        this.timeBetweedActions = timeBetweedActions;
    }

    public SpellComponent(Entity self, String targets) {
        this.self = self;
        this.targets = targets;
        this.timeBetweedActions = 0;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("finished")) {
            finished = true;
        } else if (str.matches("spelldmg [a-z]+ [0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            actions.add(list[0] + " " + list[1]);
        } else if (str.matches("spellperiodicdmg [a-z]+ [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(17);
            list = str.split(" ");
            actions.add("DoT " + Float.parseFloat(list[1]) + " " + list[2]);
        } else if (str.matches("aoedata [a-zA-Z]+ [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            list = str.split(" ");
            actions.add(list[0] + " " + Float.parseFloat(list[1]) + " " + list[2]);
        } else if (str.matches("healing [0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            actions.add("HPdelta" + " " + str);
        } else if (str.matches("bouncingstop")) {
            canBounce = false;
        } else if (str.matches("added [0-9]+")) {
            str = str.substring(6);
            long temp = Long.parseLong(str);
            if (temp == Consts.AREAOFEFFECT) {
                self.broadcast("requestaoedata");
                hasAoE = true;
            } else if (temp == Consts.SPELLDAMAGE) {
                self.broadcast("requestspelldmg");
            } else if (temp == Consts.DURATION) {
                hasDuration = true;
            } else if (temp == Consts.CIRCULARMOVEMENT) {
                hasDuration = true;
            } else if (temp == Consts.SPELLPERIODICDAMAGE) {
                self.broadcast("requestspellperiodicdmg");
            } else if (temp == Consts.SPELLHEAL) {
                self.broadcast("requesthealing");
            } else if (temp == Consts.BOUNCE) {
                canBounce = true;
            }
        }
    }

    public void handleTargets(ArrayList<Entity> entities) {
        if (actions.size() > 0) {
            now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
            if (now - lastTime >= timeBetweedActions * 1000f) {
                lastTime = now;
                for (Iterator<Entity> iter = entities.iterator(); iter.hasNext();) {
                    Entity entity = (Entity) iter.next();
                    for (String action: actions) {
                        entity.broadcast(action);
                    }
                    if (!hasAoE) {
                        if (canBounce) {
                            if (targets.contains("enemy")) {
                                targets = "friend";
                            } else if (targets.contains("friend")) {
                                targets = "enemy";
                            }
                            System.out.println(actions);
                            self.broadcast("bounced");
                        } else {
                            finished = true;
                        }
                    }
                }
                if (!hasDuration && !canBounce) {
                    finished = true;
                    actions = new ArrayList<String>();
                }
            }
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
    public long getID() {
        return id;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }
}
