package components;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Ellipse;

import fonts.Text;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpriteComponent implements Component {
    private int bit = Consts.SPRITE;
    private Image image;
    private SpriteSheet sheet = null;
    private Animation animation = null;
    private Animation attackAnimation = null;
    private Animation castAnimation = null;
    private float width, height;
    private Graphics gT, gHP, gMP;
    private Entity self;
    private float lastX = 0;
    private float lastY = 0;
    private float lastScale = 1;
    private float lastRotation = 0;
    private boolean hasHealth = false;
    private float lastHealth = 0;
    private float lastMaxHealth = 0;
    private boolean hasMana = false;
    private float lastMana = 0;
    private float lastMaxMana = 0;
    private Ellipse ellipse = null;
    private Text font = new Text("fonts/verdana.ttf", java.awt.Color.white);

    public SpriteComponent(Entity self, String imagePath) throws SlickException {
        image = new Image(imagePath);
        this.self = self;
        this.width = image.getWidth();
        this.height = image.getHeight();
        gT = new Graphics();
        gHP = new Graphics();
        gMP = new Graphics();
        ellipse = new Ellipse(0, 0, 0, 0);
    }

    public SpriteComponent(Entity self, SpriteSheet sheet) throws SlickException {
        this.self = self;
        this.sheet = sheet;
        image = sheet.getSubImage(0, 0);
        animation = new Animation(sheet, 150);
        animation.setPingPong(true);
        this.width = 64;
        this.height = 64;
        gT = new Graphics();
        gHP = new Graphics();
        gMP = new Graphics();
        ellipse = new Ellipse(0, 0, 0, 0);
    }

    public void draw() {
        if (sheet != null) {
            animation.draw(lastX, lastY, width * lastScale, height * lastScale);
            image = animation.getCurrentFrame();
        } else {
            // gT.rotate(lastX + image.getWidth() / 2, lastY + image.getHeight()
            // / 2, lastRotation);
            gT.drawImage(image, lastX, lastY, lastX + width * lastScale, lastY + height * lastScale, 0, 0, width,
                    height);
        }
        if (hasHealth) {
            gHP.drawRect(lastX, lastY - 31, lastScale * image.getWidth(), 10);
            gHP.fillRect(lastX, lastY - 31, lastScale * image.getWidth() * lastHealth / lastMaxHealth, 10);
            gHP.setColor(Color.red);
            font.draw(lastX + lastScale * image.getWidth() / 2 - 20, lastY - 31,
                    (int) lastHealth + "/" + (int) lastMaxHealth);
        }
        if (hasMana) {
            gMP.drawRect(lastX, lastY - 20, lastScale * image.getWidth(), 10);
            gMP.fillRect(lastX, lastY - 20, lastScale * image.getWidth() * lastMana / lastMaxMana, 10);
            gMP.setColor(Color.blue);
            font.draw(lastX + lastScale * image.getWidth() / 2 - 20, lastY - 20,
                    (int) lastMana + "/" + (int) lastMaxMana);
        }
        ellipse.setLocation(lastX, lastY);
        ellipse.setRadii(lastScale * image.getWidth() / 2, lastScale * image.getHeight() / 2);

        gT.draw(ellipse);

        if (attackAnimation != null) {
            attackAnimation.draw(lastX++, lastY, width, height);
            if (attackAnimation.getFrame() == attackAnimation.getFrameCount() - 1) {
                attackAnimation = null;
            }
        }
    }

    public void draw(float x, float y, float rotation, float scale) {
        lastX = x;
        lastY = y;
        lastRotation = rotation;
        lastScale = scale;
    }

    @Override
    public int getBit() {
        return bit;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String[] list = null;
        String str = command;
        if (str.matches("draw")) {
            draw();
            return;
        }
        if (str.matches("draw [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]* [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(5);
            list = str.split(" ");
            draw(Float.parseFloat(list[0]), Float.parseFloat(list[1]), Float.parseFloat(list[2]),
                    Float.parseFloat(list[3]));
            return;
        }
        if (str.matches("added " + Consts.HEALTH)) {
            hasHealth = true;
            self.broadcast("requestHP");
            return;
        }
        if (str.matches("added " + Consts.MANA)) {
            hasMana = true;
            self.broadcast("requestMP");
            return;
        }
        if (str.matches("added " + Consts.TRANSFORM)) {
            self.broadcast("requestPos");
            return;
        }
        if (str.matches("removed " + Consts.HEALTH)) {
            return;
        }
        if (str.matches("removed " + Consts.MANA)) {
            hasMana = false;
            return;
        }
        if (str.matches("removed " + Consts.TRANSFORM)) {
            hasHealth = false;
            return;
        }
        if (str.matches("updateHP [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            updateHP(Float.parseFloat(list[0]), Float.parseFloat(list[1]));
            return;
        }
        if (str.matches("updateMP [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            updateMP(Float.parseFloat(list[0]), Float.parseFloat(list[1]));
            return;
        }
        if (str.matches("attacked")) {
            animateAttack();
        }
    }

    private void animateAttack() {
        try {
            attackAnimation = new Animation(new SpriteSheet("images/campfire1.png", 64, 64), 70);
            attackAnimation.setLooping(false);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }

    public void updateHP(float health, float maxHealth) {
        lastHealth = health;
        lastMaxHealth = maxHealth;
    }

    public void updateMP(float mana, float maxMana) {
        lastMana = mana;
        lastMaxMana = maxMana;
    }
}
