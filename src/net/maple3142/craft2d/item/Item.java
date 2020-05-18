package net.maple3142.craft2d.item;

import javafx.scene.image.Image;

public interface Item {
    Image getImage();

    boolean equals(Item item);
}
