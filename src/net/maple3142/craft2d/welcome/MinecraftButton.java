package net.maple3142.craft2d.welcome;

import javafx.scene.control.Button;

public class MinecraftButton extends Button {

    private final static double rate = 2.2;

    public MinecraftButton(String text) {
        super(text);
        getStyleClass().add("mc-button");
        setFocusTraversable(false);
        setPrefSize(198 * rate, 18 * rate); // 198x18 is the size of the button image
        setMaxWidth(198 * rate);
    }
}
