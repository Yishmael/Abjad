package components.units;

import java.util.ArrayList;

import org.lwjgl.Sys;

import components.Component;
import enums.StatusType;
import others.Consts;
import others.Entity;
import others.MessageChannel;
import others.Status;

public class StatusComponent implements Component {
    private static long id = Consts.STATUS;
    private Entity self;
    private ArrayList<Status> buffs = new ArrayList<Status>();
    private ArrayList<Status> debuffs = new ArrayList<Status>();
    private ArrayList<Status> curses = new ArrayList<Status>();

    public StatusComponent(Entity self) {
        this.self = self;

        buffs.add(new Status(StatusType.Fury));
        buffs.add(new Status(StatusType.Haste));
        buffs.add(new Status(StatusType.Rage));
        buffs.add(new Status(StatusType.HoT));
        buffs.add(new Status(StatusType.MoT));

        curses.add(new Status(StatusType.Decrepify));
        curses.add(new Status(StatusType.Weaken));

        debuffs.add(new Status(StatusType.DoT));
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
        if (str.matches("[a-zA-Z]+ [-]?[0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            if (str.substring(0, 6).matches("update")) {
                return;
            }
            list = str.split(" ");
            if (!StatusType.exists(list[0])) {
                System.out.println("he");
                return;
            }

            String status = list[0];
            float amount = Float.parseFloat(list[1]);
            float duration = Float.parseFloat(list[2]);

            for (Status buff: buffs) {
                if (status.equalsIgnoreCase(buff.getName())) {
                    // accumulate buff effect for proper negation
                    if (buff.isActive()) {
                        self.broadcast(buff.getEffect() + " " + buff.getAmount() * buff.getSign());
                        buff.setAmount(buff.getAmount() + amount);
                    } else {
                        buff.setAmount(amount);
                        self.broadcast(buff.getEffect() + " " + buff.getAmount() * buff.getSign());
                    }
                    buff.setCreateTime((Sys.getTime() * 1000) / Sys.getTimerResolution());
                    buff.setDuration(duration);
                    buff.setActive(true);
                    System.out.println(status + " set on " + self.getName());
                    return;
                }
            }
            for (Status debuff: debuffs) {
                if (status.equalsIgnoreCase(debuff.getName())) {
                    // accumulate buff effect for proper negation
                    if (debuff.isActive()) {
                        self.broadcast(debuff.getEffect() + " " + debuff.getAmount() * debuff.getSign());
                        debuff.setAmount(debuff.getAmount() + amount);
                    } else {
                        debuff.setAmount(amount);
                        self.broadcast(debuff.getEffect() + " " + debuff.getAmount() * debuff.getSign());
                    }
                    debuff.setCreateTime((Sys.getTime() * 1000) / Sys.getTimerResolution());
                    debuff.setDuration(duration);
                    debuff.setActive(true);
                    System.out.println(status + " set on " + self.getName());
                    return;
                }
            }
            for (Status curse: curses) {
                if (status.equalsIgnoreCase(curse.getName())) {
                    for (Status curse1: curses) {
                        if (curse1.isActive()) {
                            return;
                        }
                    }
                    curse.setAmount(amount);
                    self.broadcast(curse.getEffect() + " " + curse.getAmount() * curse.getSign());
                    curse.setCreateTime((Sys.getTime() * 1000) / Sys.getTimerResolution());
                    curse.setDuration(duration);
                    curse.setActive(true);
                    System.out.println(status + " set on " + self.getName());
                    return;
                }
            }
        }

    }

    @Override
    public void update() {
        for (Status curse: curses) {
            if (curse.isActive()) {
                if (curse.getTimeRemaining() <= 0) {
                    System.out.println(curse.getName() + " expired" + " after " + curse.getDuration() + " seconds");
                    reset(curse);
                }
            }
        }
        for (Status buff: buffs) {
            if (buff.isActive()) {
                if (buff.getTimeRemaining() <= 0) {
                    System.out.println(buff.getName() + " expired" + " after " + buff.getDuration() + " seconds");
                    reset(buff);
                }
            }
        }
        for (Status debuff: debuffs) {
            if (debuff.isActive()) {
                if (debuff.getTimeRemaining() <= 0) {
                    System.out.println(debuff.getName() + " expired" + " after " + debuff.getDuration() + " seconds");
                    reset(debuff);
                }
            }
        }
    }

    public void reset(Status status) {
        self.broadcast(status.getEffect() + " " + -status.getAmount() * status.getSign());
        status.setActive(false);
    }

    public ArrayList<Status> getCurrentStatus() {
        ArrayList<Status> result = new ArrayList<Status>();
        for (Status curse: curses) {
            if (curse.isActive()) {
                result.add(curse);
            }
        }
        for (Status buff: buffs) {
            if (buff.isActive()) {
                result.add(buff);
            }
        }
        for (Status debuff: debuffs) {
            if (debuff.isActive()) {
                result.add(debuff);
            }
        }
        return result;
    }

    @Override
    public long getID() {
        return id;
    }
}
