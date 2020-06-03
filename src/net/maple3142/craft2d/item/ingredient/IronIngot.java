package net.maple3142.craft2d.item.ingredient;

import javafx.scene.image.Image;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Stackable;

public class IronIngot implements Item, Stackable {

    public static final int id = 21;

    private final Image img = new Image(IronIngot.class.getResource("/item/iron_ingot.png").toString());

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof IronIngot;
    }
}
