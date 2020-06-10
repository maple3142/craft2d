package net.maple3142.craft2d.game.item;

import javafx.scene.image.Image;

public interface Item {
    Image getImage();

    boolean equals(Item item);

    int getId();
}
