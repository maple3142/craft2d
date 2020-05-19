package net.maple3142.craft2d.item;

import javafx.scene.image.Image;

public class Stick implements Item, Stackable {
    private Image img = new Image(Stick.class.getResource("/item/stick.png").toString());

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof Stick;
    }
}
