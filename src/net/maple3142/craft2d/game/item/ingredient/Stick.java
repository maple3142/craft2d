package net.maple3142.craft2d.game.item.ingredient;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.Stackable;
import net.maple3142.craft2d.game.item.block.PlankOakBlock;

public class Stick implements Item, Stackable {

    public static final int id = 6;

    public static final Recipe<Stick> recipe = new SimpleRecipe<>(Stick.class, 4, 1, 2, PlankOakBlock.id, PlankOakBlock.id);

    private final Image img = new Image(Stick.class.getResource("/item/stick.png").toString());

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
