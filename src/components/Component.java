package components;

import others.MessageChannel;

public interface Component {

    public abstract int getBit();

    // static long id = 0;
    public abstract void process(MessageChannel channel);

    public abstract void receive(String command);

    // static float dt = 0f;
    // static float timeSinceLastUpdate = 0f;
    public abstract void update();
}
