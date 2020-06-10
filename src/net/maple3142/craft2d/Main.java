package net.maple3142.craft2d;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.maple3142.craft2d.welcome.WelcomeScreen;

public class Main extends Application {

    final int minHeight = 768;
    final int minWidth = 1024;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        var welcome = new WelcomeScreen(stage);
        stage.setTitle("Craft2d");
        stage.setMinHeight(minHeight);
        stage.setMinWidth(minWidth);
        stage.setHeight(minHeight);
        stage.setWidth(minWidth);
        stage.setScene(welcome.getScene());
        stage.getIcons().add(new Image(getClass().getResource("/icon.png").toString()));
        stage.setMaximized(true);
        stage.show();
    }
}
