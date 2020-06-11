package net.maple3142.craft2d.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Music {

    public static final List<Media> staticMusics;

    static {
        try {
            var musicPath = Paths.get(Music.class.getResource("/music").toURI());
            staticMusics = Files.list(musicPath).map(p -> new Media(p.toUri().toString())).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Media> musics;

    public Music() {
        musics = new ArrayList<>(staticMusics);
        Collections.shuffle(musics);
    }

    private int current = 0;
    private MediaPlayer player;

    public void playNext() {
        if (player == null) {
            var cur = musics.get(current);
            player = new MediaPlayer(cur);
            player.setOnEndOfMedia(() -> {
                current = (current + 1) % musics.size();
                player = null;
                playNext();
            });
        }
        player.play();
    }

    public void stop() {
        if (player != null) {
            player.stop();
        }
    }
}
