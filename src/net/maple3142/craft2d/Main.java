package net.maple3142.craft2d;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.maple3142.craft2d.utils.IOHelper;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    final int minHeight = 768;
    final int minWidth = 1024;

    @Override
    public void start(Stage stage) {
        var root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        var title = new Label("Craft2d");
        title.setFont(Font.font("Arial", 70));
        var newWorld = new Button("New World");
        var load = new Button("Load");
        var exit = new Button("Exit");
        root.getChildren().addAll(title, newWorld, load, exit);
        var welcomeScene = new Scene(root);
        stage.setTitle("Craft2d");
        stage.setMinHeight(minHeight);
        stage.setMinWidth(minWidth);
        stage.setHeight(minHeight);
        stage.setWidth(minWidth);
        stage.setScene(welcomeScene);
        stage.show();

        var fc = new FileChooser();
        var ef = new FileChooser.ExtensionFilter("Craft2d World Files", "*.world");
        fc.getExtensionFilters().add(ef);
        newWorld.setOnAction(event -> {
            var file = fc.showSaveDialog(stage);
            if (file == null) return;
            var game = new Game();
            game.setExitCallback(() -> {
                IOHelper.writeGameToPath(file.toPath(), game);
                stage.setScene(welcomeScene);
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
                stage.setScene(welcomeScene);
            });
            stage.setScene(game.getScene());
            game.start();
        });
        exit.setOnAction(event -> stage.close());
    }
}
