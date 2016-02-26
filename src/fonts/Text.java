package fonts;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Text {
    private UnicodeFont font;

    @SuppressWarnings("unchecked")
    public Text(String fontPath, java.awt.Color color) {
        try {
            font = new UnicodeFont(fontPath, 9, true, false);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(color));
        try {
            font.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void draw(float x, float y, String str) {
        font.drawString(x, y, str);
    }

}
