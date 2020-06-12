package net.maple3142.craft2d.welcome;

import javafx.scene.control.Label;

public class MinecraftLabel extends Label {
    public MinecraftLabel(String text) {
        super(text);
        getStyleClass().add("mc-label");
    }
}
