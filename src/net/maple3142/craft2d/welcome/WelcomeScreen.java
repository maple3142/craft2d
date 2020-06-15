package net.maple3142.craft2d.welcome;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.maple3142.craft2d.game.utils.IOHelper;

public class WelcomeScreen {

    private final Scene scene;

    public WelcomeScreen(Stage stage) {
        var root = new VBox();
        root.getStyleClass().add("root");
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        var titleImg = new Image(getClass().getResource("/welcome/title.png").toString(), 393 * 1.5, 101 * 1.5, false, false);
        var title = new ImageView(titleImg);
        var titleWrapper = new StackPane(title);
        titleWrapper.setAlignment(Pos.CENTER);
        titleWrapper.getStyleClass().add("title-wrapper");

        var newWorld = new MinecraftButton("New World");
        newWorld.setOnAction((ActionEvent event) -> stage.setScene(new WorldCreationScreen(stage).getScene()));

        var load = new MinecraftButton("Load");
        load.setOnAction(event -> {
            var file = Utils.showOpenDialog(stage);
            if (file == null) return;
            var game = IOHelper.readGameFromPath(file.toPath());
            if (game == null) return;
            Utils.startGame(stage, game, file);
        });

        var binding = new MinecraftButton("Key Bindings");
        binding.setOnAction(event -> stage.setScene(new KeyBindingScreen(stage).getScene()));

        var exit = new MinecraftButton("Exit");
        exit.setOnAction(event -> stage.close());

        root.getChildren().addAll(titleWrapper, newWorld, load, binding, exit);
        scene = new Scene(root);
        scene.getStylesheets().addAll("/welcome/controls.css", "/welcome/welcome.css");

    }

    public Scene getScene() {
        return scene;
    }
}
