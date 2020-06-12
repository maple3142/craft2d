package net.maple3142.craft2d.welcome;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.maple3142.craft2d.GlobalKeyBinding;

import java.util.concurrent.atomic.AtomicBoolean;

public class KeyBindingScreen {

    private Scene scene;

    public KeyBindingScreen(Stage stage) {
        var root = new VBox();
        root.getStyleClass().add("root");
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        var title = new MinecraftLabel("Key Bindings");
        title.getStyleClass().add("title");

        root.getChildren().addAll(title);

        var keyList = new VBox();
        keyList.setAlignment(Pos.CENTER);
        keyList.setSpacing(10);
        keyList.getStyleClass().add("key-list");
        AtomicBoolean normalMode = new AtomicBoolean(true);
        for (var pair : GlobalKeyBinding.binding.getMapping().entrySet()) {
            var group = new MinecraftKeyBindingGroup(GlobalKeyBinding.binding, pair);
            keyList.getChildren().add(group);
            var btn = group.getButton();
            btn.setOnAction(event -> {
                if (normalMode.get()) {
                    normalMode.set(false);
                    btn.setText("<???>");
                    btn.requestFocus();
                }
            });
            btn.setOnKeyPressed(event -> {
                var code = event.getCode();
                if (event.getCharacter().equals("\u0000") && code == KeyCode.UNDEFINED) {
                    code = KeyCode.SPACE; // I don't know why it is sometimes UNDEFINED
                }
                var success = GlobalKeyBinding.binding.changeBinding(pair.getKey(), code);
                if (success) {
                    btn.setText(code.toString());
                } else {
                    btn.setText(pair.getValue().toString());
                }
                normalMode.set(true);
            });
        }
        root.getChildren().addAll(keyList);

        var done = new MinecraftButton("Done");
        done.getStyleClass().add("done-btn");
        done.setOnAction(event -> stage.setScene(new WelcomeScreen(stage).getScene()));
        root.getChildren().add(done);

        scene = new Scene(root);
        scene.getStylesheets().addAll("/welcome/controls.css", "/welcome/key_binding.css");
    }

    public Scene getScene() {
        return scene;
    }
}
