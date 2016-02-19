package components;

public class MovementComponent implements Component{
	private float speed;
	
	public MovementComponent(float speed) {
		// TODO Auto-generated constructor stub
		this.speed = speed;
	}
	
	public boolean isRunning() {
		return speed >= 5;
	}

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }
	
}
