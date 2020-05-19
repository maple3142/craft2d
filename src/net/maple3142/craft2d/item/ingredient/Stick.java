package net.maple3142.craft2d.item.ingredient;

import javafx.scene.image.Image;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Stackable;

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
