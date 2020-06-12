package net.maple3142.craft2d.welcome;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.*;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.utils.IOHelper;

import java.io.File;
import java.util.Optional;
import java.util.Random;

public class Utils {
    private static FileChooser fc = new FileChooser();
    private static FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Craft2d World Files", "*.world");

    static {
        fc.getExtensionFilters().add(ef);
    }

    public static File showSaveDialog(Window window) {
        return fc.showSaveDialog(window);
    }

    public static File showOpenDialog(Window window) {
        return fc.showOpenDialog(window);
    }


    public static void startGame(Stage stage, Game game, File file) {
        EventHandler<WindowEvent> closeHandler = event -> {
            var confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Exit warning");
            confirm.setHeaderText("Do you really want to exit without saving?");
            confirm.setContentText("If you don't, cancel this and press ESC key.");
            confirm.initModality(Modality.APPLICATION_MODAL);
            confirm.initOwner(stage);
            Optional<ButtonType> closeResponse = confirm.showAndWait();
            if (closeResponse.isEmpty() || closeResponse.get().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        };
        game.setExitCallback(() -> {
            IOHelper.writeGameToPath(file.toPath(), game);
            stage.setScene(new WelcomeScreen(stage).getScene());
            stage.setOnCloseRequest(null);
        });
        stage.setScene(game.getScene());
        stage.setOnCloseRequest(closeHandler);
        game.start();
    }

    public static long hash(String string) {
        long h = 1125899906842597L; // prime
        int len = string.length();

        for (int i = 0; i < len; i++) {
            h = 31 * h + string.charAt(i);
        }
        return h;
    }

    private static Random rand = new Random();

    public static long randomLong() {
        return rand.nextLong();
    }
}
