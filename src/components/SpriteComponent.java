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
    private Animation standAnimation = null;
    private boolean standing = true;
    private Animation walkAnimation = null;
    private boolean walking = false;
    private boolean right = true;
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

    public SpriteComponent(Entity self, SpriteSheet sheet, int duration, boolean pingPong) throws SlickException {
        this.self = self;
        this.sheet = sheet;
        image = sheet.getSubImage(0, 0);
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.duration = duration;
        standAnimation = new Animation();
        for (int i = 0; i < sheet.getHorizontalCount(); i++) {
            standAnimation.addFrame(sheet.getSubImage(i, 0), duration);
        }
        standAnimation.setPingPong(pingPong);
        gT = new Graphics();
        gHP = new Graphics();
        gMP = new Graphics();
        ellipse = new Ellipse(0, 0, 0, 0);
    }

    @SuppressWarnings("deprecation")
    public void draw() {
        if (lastHealth == 0 && lastMaxHealth > 0) {
            gT.drawImage(image, lastX, lastY, lastX + width * lastScale, lastY + height * lastScale, 0, 0, width,
                    height);
        } else if (sheet != null) {
            if (standing) {
                standAnimation.getCurrentFrame().getFlippedCopy(!right, false).draw(lastX, lastY, width * lastScale,
                        height * lastScale);
                standAnimation.updateNoDraw();
                image = standAnimation.getCurrentFrame();
            }
        } else {
            // gT.rotate(lastX + image.getWidth() / 2, lastY + image.getHeight()
            // / 2, lastRotation);

            gT.drawImage(image, lastX, lastY, lastX + width * lastScale, lastY + height * lastScale, 0, 0, width,
                    height);
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
        ellipse.setLocation(lastX, lastY);
        ellipse.setRadii(lastScale * width / 2, lastScale * height / 2);

        gT.draw(ellipse);

        if (sheet != null) {
            if (attacking && attackAnimation != null) {
                walking = false;
                standing = false;
                casting = false;
                attackAnimation.getCurrentFrame().getFlippedCopy(!right, false).draw(lastX, lastY, width, height);
                attackAnimation.updateNoDraw();
                if (attackAnimation.getFrame() == attackAnimation.getFrameCount() - 1) {
                    attacking = false;
                }
            } else if (casting && castAnimation != null) {
                attacking = false;
                standing = false;
                walking = false;
                castAnimation.getCurrentFrame().getFlippedCopy(!right, false).draw(lastX, lastY, width, height);
                castAnimation.updateNoDraw();
                if (castAnimation.getFrame() == castAnimation.getFrameCount() - 1) {
                    casting = false;
                }
            } else if (walking && walkAnimation != null) {
                attacking = false;
                standing = false;
                casting = false;
                walkAnimation.getCurrentFrame().getFlippedCopy(!right, false).draw(lastX, lastY, width, height);
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
        if (x > lastX && !right) {
            right = true;
            // System.out.println("facing right");
        } else if (x < lastX && right) {
            right = false;
            // System.out.println("facing left");
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

    private void animateExplosion() {
        if (sheet != null) {
            if (sheet.getHorizontalCount() < 10) {
                return;
            }
            castAnimation = new Animation();
            SpriteSheet ss = null;
            try {
                ss = new SpriteSheet("images/spells/explosion.png", 160, 160);
            } catch (SlickException e1) {
                e1.printStackTrace();
            }

            for (int i = 0; i < ss.getVerticalCount(); i++) {
                for (int j = 0; j < ss.getHorizontalCount(); j++) {
                    castAnimation.addFrame(ss.getSubImage(j, i), 150);
                }
            }
            castAnimation.setLooping(false);
        }
        casting = true;
    }

    private void animateFireball() {
        if (sheet != null) {
            castAnimation = new Animation();
            for (int i = 0; i < sheet.getHorizontalCount(); i++) {
                castAnimation.addFrame(sheet.getSubImage(i, 4), 100);
            }
            castAnimation.setLooping(true);
        }
        casting = true;
    }

    private void animateNourish() {
        if (sheet != null) {
            castAnimation = new Animation();
            for (int i = 0; i < sheet.getHorizontalCount(); i++) {
                castAnimation.addFrame(sheet.getSubImage(i, 2), 120);
            }
            castAnimation.setLooping(true);
        }
        casting = true;
    }

    private void animateAttack() {
        if (sheet != null && attackAnimation == null) {
            attackAnimation = new Animation();

            for (int i = 0; i < sheet.getHorizontalCount(); i++) {
                attackAnimation.addFrame(sheet.getSubImage(i, 3), 100);
            }
            attackAnimation.setLooping(true);
        }
        attacking = true;
    }

    private void animateWalk() {
        if (sheet != null && walkAnimation == null) {
            walkAnimation = new Animation();
            for (int i = 0; i < sheet.getHorizontalCount(); i++) {
                walkAnimation.addFrame(sheet.getSubImage(i, 1), duration);
            }
            walkAnimation.setLooping(true);
        }
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

    @Override
    public int getBit() {
        return bit;
    }
}
