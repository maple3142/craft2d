package net.maple3142.craft2d.item.ingredient;

import javafx.scene.image.Image;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Stackable;
import net.maple3142.craft2d.item.block.PlankOakBlock;

public class Stick implements Item, Stackable {

    public static final int id = 6;

    public static final Recipe<Stick> recipe = new SimpleRecipe<>(Stick.class, 1, 2, PlankOakBlock.id, PlankOakBlock.id);
    private Image img = new Image(Stick.class.getResource("/item/stick.png").toString());

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
        return item instanceof Stick;
    }
}
