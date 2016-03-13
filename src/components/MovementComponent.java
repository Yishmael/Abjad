package components;

public interface MovementComponent extends Component {
    public void move(float dx, float dy);

    public float getSpeed();

    public void setSpeed(float speed);

    public boolean canMove();

}
