package loaders;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontLoader {
    private UnicodeFont font;

    @SuppressWarnings("unchecked")
    public FontLoader(String fontName, int size, java.awt.Color color) {
        fontName = fontName.toLowerCase();

        try {
            font = new UnicodeFont("res/fonts/" + fontName + ".ttf", size, true, false);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(color));
            font.loadGlyphs();
        } catch (SlickException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void draw(float x, float y, String str) {
        font.drawString(x, y, str);
    }
}
