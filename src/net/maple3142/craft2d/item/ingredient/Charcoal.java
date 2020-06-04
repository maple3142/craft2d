package net.maple3142.craft2d.item.ingredient;

import javafx.scene.image.Image;
import net.maple3142.craft2d.item.Fuel;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Stackable;

public class Charcoal implements Item, Stackable, Fuel {

    public static final int id = 22;

    private final Image img = new Image(Charcoal.class.getResource("/item/charcoal.png").toString());

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
        return item instanceof Charcoal;
    }

    @Override
    public double getEnergyUnit() {
        return 8.0;
    }
}
