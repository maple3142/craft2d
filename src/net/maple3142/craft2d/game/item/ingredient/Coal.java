package net.maple3142.craft2d.game.item.ingredient;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.item.Fuel;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.Stackable;

public class Coal implements Item, Stackable, Fuel {

    public static final int id = 15;

    private final Image img = new Image(Coal.class.getResource("/item/coal.png").toString());

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
        return item instanceof Coal;
    }

    @Override
    public double getEnergyUnit() {
        return 8;
    }
}
