package net.maple3142.craft2d.game;

import javafx.scene.text.Font;

public class FontProvider {
    public static final Font minecraftFontBig;
    public static final Font minecraftFontNormal;

    static {
        var path = FontProvider.class.getResource("/minecraftia.ttf").toString();
        minecraftFontBig = Font.loadFont(path, 15);
        minecraftFontNormal = Font.loadFont(path, 12);
    }
}
