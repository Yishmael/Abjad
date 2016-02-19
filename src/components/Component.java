package components;

public interface Component {
    static float dt = 0f;
    static float timeSinceLastUpdate = 0f;
    static float oldTimeSinceLastUpdate = 0f;
	public abstract void update();
}