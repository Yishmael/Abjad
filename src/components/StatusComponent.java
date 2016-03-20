package components;

import org.lwjgl.Sys;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class StatusComponent implements Component {
    public static int id = Consts.STATUS;
    public Entity self;
    public long createTime, now;
    public boolean cursed = false;
    public String[] effects;
    public float durations[];
    public float amounts[];

    public StatusComponent(Entity self) {
        this.self = self;
        effects = new String[10];
        durations = new float[10];
        amounts = new float[10];
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel != null) {
            receive(channel.getCommand());
        }
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
                createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
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
                createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
            }
            return;
        }
        if (str.matches("dot [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            if (!cursed) {
                list = str.split(" ");
                effects[2] = list[0];
                amounts[2] = Float.parseFloat(list[1]);
                durations[2] = Float.parseFloat(list[2]);
                self.broadcast("HPregen " + -amounts[2]);
                createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
                return;
            }
        }
        if (str.matches("haste [0-9]+ [0-9]+[.]?[0-9]*")) {
            if (!cursed) {
                list = str.split(" ");
                effects[3] = list[0];
                amounts[3] = Integer.parseInt(list[1]);
                durations[3] = Float.parseFloat(list[2]);
                self.broadcast("MS " + amounts[3] + "%");
                createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
                return;
            }
        }
        if (str.matches("fury [0-9]+ [0-9]+[.]?[0-9]*")) {
            if (!cursed) {
                list = str.split(" ");
                effects[4] = list[0];
                amounts[4] = Integer.parseInt(list[1]);
                durations[4] = Float.parseFloat(list[2]);
                self.broadcast("AS " + amounts[4] + "%");
                createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
                return;
            }
        }
        if (str.matches("rage [0-9]+ [0-9]+[.]?[0-9]*")) {
            if (!cursed) {
                list = str.split(" ");
                effects[5] = list[0];
                amounts[5] = Integer.parseInt(list[1]);
                durations[5] = Float.parseFloat(list[2]);
                self.broadcast("DMG " + amounts[5]);
                createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
                return;
            }
        }
        if (str.matches("hot [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            if (!cursed) {
                list = str.split(" ");
                effects[6] = list[0];
                amounts[6] = Float.parseFloat(list[1]);
                durations[6] = Float.parseFloat(list[2]);
                self.broadcast("HPregen " + amounts[6]);
                System.out.println(amounts[6]);
                createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
                return;
            }
        }
        if (str.matches("mot [-]?[0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            if (!cursed) {
                list = str.split(" ");
                effects[7] = list[0];
                amounts[7] = Float.parseFloat(list[1]);
                durations[7] = Float.parseFloat(list[2]);
                self.broadcast("MPregen " + amounts[7]);
                createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                cursed = true;
                return;
            }
        }
    }

    // temp
    @Override
    public void update() {
        if (cursed) {
            for (int i = 0; i < effects.length; i++) {
                if (effects[i] != null) {
                    if ((Sys.getTime() * 1000) / Sys.getTimerResolution() - createTime >= durations[i] * 1000f) {
                        System.out.println(effects[i] + " expired" + " after " + durations[i] + " seconds");
                        reset();
                        cursed = false;
                    }
                }
            }
        }
    }

    // temp
    public void reset() {
        if (effects[0] != null) {
            self.broadcast("HPcap " + amounts[0]);
            effects[0] = null;
            durations[0] = 0;
            amounts[0] = 0;
        }
        if (effects[1] != null) {
            self.broadcast("DMG " + amounts[1]);
            effects[1] = null;
            durations[1] = 0;
            amounts[1] = 0;
        }
        if (effects[2] != null) {
            self.broadcast("HPregen " + amounts[2]);
            effects[2] = null;
            durations[2] = 0;
            amounts[2] = 0;
        }
        if (effects[3] != null) {
            self.broadcast("MS " + -amounts[3] + "%");
            effects[3] = null;
            durations[3] = 0;
            amounts[3] = 0;
        }
        if (effects[4] != null) {
            self.broadcast("AS " + -amounts[4] + "%");
            effects[4] = null;
            durations[4] = 0;
            amounts[4] = 0;
        }
        if (effects[5] != null) {
            self.broadcast("DMG " + -amounts[5]);
            effects[5] = null;
            durations[5] = 0;
            amounts[5] = 0;
        }
        if (effects[6] != null) {
            self.broadcast("HPregen " + -amounts[6]);
            effects[6] = null;
            durations[6] = 0;
            amounts[6] = 0;
        }
        if (effects[7] != null) {
            self.broadcast("MPregen " + -amounts[7]);
            effects[7] = null;
            durations[7] = 0;
            amounts[7] = 0;
        }
    }

    @Override
    public int getID() {
        return id;
    }
}
