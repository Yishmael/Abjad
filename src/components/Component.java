package components;

import others.MessageChannel;

public interface Component {
    static int bit = 0;

    public int getBit();

    // static long id = 0;
    public abstract void process(MessageChannel channel);

    // static float dt = 0f;
    // static float timeSinceLastUpdate = 0f;
    public abstract void update();
}
