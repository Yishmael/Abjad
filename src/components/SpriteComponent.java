package components;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import fonts.Text;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpriteComponent implements Component {
    private int id = Consts.SPRITE;
    private Image image;
    private SpriteSheet sheet = null;
    private Animation standAnimation = null;
    private boolean standing = true;
    private Animation walkAnimation = null;
    private boolean walking = false;
    private Vector2f facing = new Vector2f(1, 0);
    private Animation attackAnimation = null;
    private boolean attacking = false;
    private Animation castAnimation = null;
    private boolean casting = false;
    private int duration = 0;
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
    private Text font = new Text("fonts/verdana.ttf", java.awt.Color.white);

    public SpriteComponent(Entity self, String imagePath, float width, float height) throws SlickException {
        image = new Image(imagePath);
        this.self = self;
        this.width = width;
        this.height = height;
        gT = new Graphics();
        gHP = new Graphics();
        gMP = new Graphics();
    }

    public SpriteComponent(Entity self, SpriteSheet sheet, int duration, boolean pingPong, float width, float height)
            throws SlickException {
        this.self = self;
        this.sheet = sheet;
        image = sheet.getSubImage(0, 0);
        this.width = width;
        this.height = height;
        this.duration = duration;
        standAnimation = new Animation();
        for (int i = 0; i < sheet.getHorizontalCount(); i++) {
            standAnimation.addFrame(sheet.getSubImage(i, 0), duration);
        }
        standAnimation.setPingPong(pingPong);
        gT = new Graphics();
        gHP = new Graphics();
        gMP = new Graphics();

        if (sheet != null && walkAnimation == null) {
            walkAnimation = new Animation();
            for (int i = 0; i < sheet.getHorizontalCount(); i++) {
                walkAnimation.addFrame(sheet.getSubImage(i, 1), duration);
            }
            walkAnimation.setLooping(true);
        }

        if (sheet != null && attackAnimation == null) {
            attackAnimation = new Animation();

            for (int i = 0; i < sheet.getHorizontalCount(); i++) {
                if (sheet.getVerticalCount() > 3)
                    attackAnimation.addFrame(sheet.getSubImage(i, 3), duration);
            }
            attackAnimation.setLooping(true);
        }

        if (sheet != null) {
            castAnimation = new Animation();
            for (int i = 0; i < sheet.getHorizontalCount(); i++) {

                // softcode this to accept all sprite sheets
                if (sheet.getVerticalCount() > 4)
                    castAnimation.addFrame(sheet.getSubImage(i, 4), duration);
                else
                    castAnimation.addFrame(sheet.getSubImage(0, 0), 1);
            }
            castAnimation.setLooping(true);
        }

        if (sheet != null) {
            castAnimation = new Animation();
            for (int i = 0; i < sheet.getHorizontalCount(); i++) {
                castAnimation.addFrame(sheet.getSubImage(i, 2), duration);
            }
            castAnimation.setLooping(true);
        }
    }

    @SuppressWarnings("deprecation")
    public void draw() {
        // add rotation
        image.setCenterOfRotation(lastScale * image.getWidth() / 2, lastScale * image.getHeight() / 2);
        if (lastHealth == 0 && lastMaxHealth > 0) {
            gT.drawImage(image, lastX, lastY, lastX + width * lastScale, lastY + height * lastScale, 0, 0,
                    image.getWidth(), image.getHeight());
        } else if (sheet != null) {
            if (standing) {
                standAnimation.getCurrentFrame().getFlippedCopy(facing.getX() == -1, false).draw(lastX, lastY,
                        width * lastScale, height * lastScale);
                standAnimation.updateNoDraw();
            }
        } else {
            gT.drawImage(image, lastX, lastY, lastX + width * lastScale, lastY + height * lastScale, 0, 0,
                    image.getWidth(), image.getHeight());
        }
        if (hasHealth) {
            gHP.drawRect(lastX, lastY - 31, lastScale * width, 10);
            gHP.fillRect(lastX, lastY - 31, lastScale * width * lastHealth / lastMaxHealth, 10);
            gHP.setColor(new Color((int) (((1.0f - lastHealth / lastMaxHealth) * 255) % 255),
                    (int) ((lastHealth / lastMaxHealth) * 255), 122));
            font.draw(lastX + lastScale * width / 2 - 20, lastY - 31, (long) lastHealth + "/" + (long) lastMaxHealth);
        }
        if (hasMana) {
            gMP.drawRect(lastX, lastY - 20, lastScale * width, 10);
            gMP.fillRect(lastX, lastY - 20, lastScale * width * lastMana / lastMaxMana, 10);
            gMP.setColor(Color.blue);
            font.draw(lastX + lastScale * width / 2 - 20, lastY - 20, (long) lastMana + "/" + (long) lastMaxMana);
        }

        if (sheet != null) {
            if (attacking && attackAnimation != null) {
                walking = false;
                standing = false;
                casting = false;
                attackAnimation.getCurrentFrame().getFlippedCopy(facing.getX() == -1, false).draw(lastX, lastY, width,
                        height);
                attackAnimation.updateNoDraw();
                if (attackAnimation.getFrame() == attackAnimation.getFrameCount() - 1) {
                    attacking = false;
                }
            } else if (casting && castAnimation != null) {
                attacking = false;
                standing = false;
                walking = false;
                castAnimation.getCurrentFrame().getFlippedCopy(facing.getX() == -1, false).draw(lastX, lastY, width,
                        height);
                castAnimation.updateNoDraw();
                if (castAnimation.getFrame() == castAnimation.getFrameCount() - 1) {
                    casting = false;
                }
            } else if (walking && walkAnimation != null) {
                attacking = false;
                standing = false;
                casting = false;
                walkAnimation.getCurrentFrame().getFlippedCopy(facing.getX() == -1, false).draw(lastX, lastY, width,
                        height);
                walkAnimation.updateNoDraw();
                if (walkAnimation.getFrame() == walkAnimation.getFrameCount() - 1) {
                    walking = false;
                }
            } else {
                standing = true;
            }
        }
    }

    public void draw(float x, float y, float rotation, float scale) {
        if (x > lastX && facing.getX() != 1) {
            facing.x = 1;
            // System.out.println(self.getName() + " facing right");
        } else if (x < lastX && facing.getX() != -1) {
            facing.x = -1;
            // System.out.println(self.getName() + " facing left");
        }
        lastX = x;
        lastY = y;
        lastRotation = rotation;
        lastScale = scale;
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
            self.broadcast("width " + width);
            self.broadcast("height " + height);
            self.broadcast("requestPos");
            return;
        }
        if (str.matches("removed " + Consts.HEALTH)) {
            hasHealth = false;
            return;
        }
        if (str.matches("removed " + Consts.MANA)) {
            hasMana = false;
            return;
        }
        if (str.matches("updateHP [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            // System.out.println("health update arrived" + LocalTime.now());
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
        if (str.matches("animate Attack")) {
            animateAttack();
            return;
        }
        if (str.matches("animate Walk")) {
            animateWalk();
            return;
        }
        if (str.matches("animate Fireball")) {
            animateFireball();
            return;
        }
        if (str.matches("animate Nourish")) {
            animateNourish();
            return;
        }
        if (str.matches("animate Explosion")) {
            animateExplosion();
            return;
        }
        if (str.matches("died")) {
            lastHealth = 0;
            return;
        }
    }

    public void animateExplosion() {
        casting = true;
    }

    public void animateFireball() {
        casting = true;
    }

    public void animateNourish() {
        casting = true;
    }

    public void animateAttack() {
        attacking = true;
    }

    public void animateWalk() {
        walking = true;
    }

    @Override
    public void update() {
        draw();
    }

    public void updateHP(float health, float maxHealth) {
        lastHealth = health;
        lastMaxHealth = maxHealth;
    }

    public void updateMP(float mana, float maxMana) {
        lastMana = mana;
        lastMaxMana = maxMana;
    }

    public Vector2f getFacing() {
        return facing;
    }

    public void setFacing(Vector2f facing) {
        this.facing.x = facing.getX();
        this.facing.y = facing.getY();
    }

    public void bindFacing(Vector2f facing) {
        this.facing = facing;
    }

    @Override
    public int getID() {
        return id;
    }
}
