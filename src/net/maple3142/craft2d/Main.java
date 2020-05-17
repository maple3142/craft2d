package net.maple3142.craft2d;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        var game = new Game();
        stage.setTitle("Test");
        stage.setMinHeight(800);
        stage.setMinWidth(600);
        stage.setScene(game.getScene());
        stage.show();
        game.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
