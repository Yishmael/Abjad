package components;

public class AttackComponent implements Component {
    private int damage;
    private float speed;

    public AttackComponent(int damage, float speed) {
        // TODO Auto-generated constructor stub
        this.damage = damage;
        this.speed = speed;
    }

    public void strike() {
        System.out.println("Dealt " + damage + " damage!");
    }

    @Override
    public void update() {
        //System.out.println(delta);
            //System.out.println(LocalTime.now() + "updating attackcomp");

    }
}
