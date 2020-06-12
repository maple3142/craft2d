package net.maple3142.craft2d.welcome;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import net.maple3142.craft2d.game.key.KeyBinding;
import net.maple3142.craft2d.game.key.KeyType;

import java.util.Map;

public class MinecraftKeyBindingGroup extends HBox {

    private Button btn;

    public MinecraftKeyBindingGroup(KeyBinding binding, Map.Entry<KeyType, KeyCode> pair) {
        setMinWidth(800);
        setMaxWidth(800);
        var label = new MinecraftLabel(pair.getKey().toString());
        var spacer = new Pane();
        setHgrow(spacer, Priority.ALWAYS);
        btn = new MinecraftButton(pair.getValue().toString());
        getChildren().addAll(label, spacer, btn);
    }

    public Button getButton() {
        return btn;
    }
}
