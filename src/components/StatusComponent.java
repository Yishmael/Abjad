package components;

import org.lwjgl.Sys;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class StatusComponent implements Component {
    public static int id = Consts.STATUS;
    public Entity self;
    public long lastTime, now;
    public boolean cursed = false;
    public String[] effects;
    public float durations[];
    public int amounts[];

    public StatusComponent(Entity self) {
        this.self = self;
        effects = new String[10];
        durations = new float[10];
        amounts = new int[10];
    }

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub
    }

    // temp
    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("decrepify [0-9]+ [0-9]+[.]?[0-9]*")) {
            if (!cursed) {
                list = str.split(" ");
                effects[0] = list[0];
                amounts[0] = Integer.parseInt(list[1]);
                durations[0] = Float.parseFloat(list[2]);
                self.broadcast("HPcap " + -amounts[0]);
                lastTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
            }
            return;
        }
        if (str.matches("weaken [0-9]+ [0-9]+[.]?[0-9]*")) {
            if (!cursed) {
                list = str.split(" ");
                effects[1] = list[0];
                amounts[1] = Integer.parseInt(list[1]);
                durations[1] = Float.parseFloat(list[2]);
                self.broadcast("DMG " + -amounts[1]);
                lastTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
            }
            return;
        }
    }

    // temp
    @Override
    public void update() {
        if (cursed) {
            for (int i = 0; i < effects.length; i++) {
                if (effects[i] != null) {
                    if ((Sys.getTime() * 1000) / Sys.getTimerResolution() - lastTime >= durations[i] * 1000f) {
                        System.out.println(effects[i] + " expired" + " after " + durations[i] + " seconds");
                        resetAll();
                        cursed = false;
                    }
                }
            }
        }
    }

    // temp
    public void resetAll() {
        for (int i = 0; i < effects.length; i++) {
            effects[i] = null;
            durations[i] = 0;
            amounts[i] = 0;
        }
    }

    @Override
    public int getID() {
        return id;
    }
}
