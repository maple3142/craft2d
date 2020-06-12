package net.maple3142.craft2d.welcome;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.maple3142.craft2d.game.Game;

import java.io.File;

public class WorldCreationScreen {
    private final Scene scene;

    private File selectedDestination;

    public WorldCreationScreen(Stage stage) {
        var root = new VBox();
        root.getStyleClass().add("root");
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setOnMouseClicked(event -> {
            root.requestFocus(); // take focus from seed field
        });

        var seedField = new MinecraftTextField();
        seedField.setPromptText("Leave blank for a random seed");
        var seedLabel = new MinecraftLabel("Seed for the world generator");
        var seedGroup = new MinecraftControlGroup(seedLabel, seedField);
        seedGroup.getStyleClass().add("seed-group");

        var saveLocationBtn = new MinecraftButton("<empty>");
        saveLocationBtn.setOnAction(event -> {
            var f = Utils.showSaveDialog(stage);
            if (f == null) return;
            selectedDestination = f;
            saveLocationBtn.setText(f.getName());
        });
        var saveLocationLabel = new MinecraftLabel("Select a location to save the world");
        var saveLocationGroup = new MinecraftControlGroup(saveLocationLabel, saveLocationBtn);
        saveLocationGroup.getStyleClass().add("save-location-group");

        var btnContainer = new HBox();
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER);
        var create = new MinecraftButton("Create New World");
        create.setOnAction(event -> {
            if (selectedDestination == null) return;
            var strSeed = seedField.getText();
            long seed = strSeed.length() == 0 ? Utils.randomLong() : Utils.hash(strSeed);
            var game = new Game(seed);
            Utils.startGame(stage, game, selectedDestination);
        });
        var cancel = new MinecraftButton("Cancel");
        cancel.setOnAction(event -> stage.setScene(new WelcomeScreen(stage).getScene()));
        btnContainer.getChildren().addAll(create, cancel);

        root.getChildren().addAll(seedGroup, saveLocationGroup, btnContainer);
        scene = new Scene(root);
        scene.getStylesheets().addAll("/welcome/controls.css", "/welcome/world_creation.css");
    }

    public Scene getScene() {
        return scene;
    }
}
