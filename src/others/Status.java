package others;

import org.lwjgl.Sys;

import enums.StatusType;

public class Status {
    private String name, effect;
    private long createTime;
    private boolean active;
    private float duration, amount;
    private int sign;

    public Status(StatusType type) {
        this.name = type.name();
        this.effect = type.getEffect();
        this.sign = type.getSign();
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public float getSign() {
        return sign;
    }

    public float getTimeRemaining() {
        return (duration * 1000 - ((Sys.getTime() * 1000) / Sys.getTimerResolution() - createTime)) / 1000f;
    }

    public String getStatusInfo() {
        return String.format("[%s, %s, %s, %s]", name, effect, sign * amount, getTimeRemaining());
    }

}
