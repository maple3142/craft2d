package net.maple3142.craft2d.welcome;

import javafx.scene.control.Control;
import javafx.scene.layout.VBox;

public class MinecraftControlGroup extends VBox {
    public MinecraftControlGroup(MinecraftLabel label, Control control) {
        getChildren().addAll(label, control);
        getStyleClass().add("mc-control-group");
        setSpacing(5);
        maxWidthProperty().bind(control.maxWidthProperty());
    }
}
