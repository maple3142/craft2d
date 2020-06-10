package net.maple3142.craft2d.welcome;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.maple3142.craft2d.game.Game;
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
        var load = new MinecraftButton("Load");
        var exit = new MinecraftButton("Exit");
        root.getChildren().addAll(titleWrapper, newWorld, load, exit);
        scene = new Scene(root);
        scene.getStylesheets().add("/welcome/main.css");

        var fc = new FileChooser();
        var ef = new FileChooser.ExtensionFilter("Craft2d World Files", "*.world");
        fc.getExtensionFilters().add(ef);
        newWorld.setOnAction(event -> {
            var file = fc.showSaveDialog(stage);
            if (file == null) return;
            var game = new Game();
            game.setExitCallback(() -> {
                IOHelper.writeGameToPath(file.toPath(), game);
                stage.setScene(scene);
            });
            stage.setScene(game.getScene());
            game.start();
        });
        load.setOnAction(event -> {
            var file = fc.showOpenDialog(stage);
            if (file == null) return;
            var game = IOHelper.readGameFromPath(file.toPath());
            if (game == null) return;
            game.setExitCallback(() -> {
                IOHelper.writeGameToPath(file.toPath(), game);
                stage.setScene(scene);
            });
            stage.setScene(game.getScene());
            game.start();
        });
        exit.setOnAction(event -> stage.close());
    }

    public Scene getScene() {
        return scene;
    }
}
