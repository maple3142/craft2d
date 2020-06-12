package net.maple3142.craft2d.welcome;

import javafx.scene.control.TextField;

public class MinecraftTextField extends TextField {

    public MinecraftTextField() {
        super();
        setFocusTraversable(false);
        getStyleClass().add("mc-text-field");
        setMinHeight(36);
        setMaxWidth(198 * 2.2);
    }
}
