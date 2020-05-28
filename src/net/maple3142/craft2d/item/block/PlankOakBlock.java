package net.maple3142.craft2d.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.PlankOak;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleUnorderedRecipe;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.PlaceableItem;
import net.maple3142.craft2d.item.Stackable;

public class PlankOakBlock implements PlaceableItem, Stackable {

    public static final int id = 5;

    public static final Recipe<PlankOakBlock> recipe = new SimpleUnorderedRecipe<>(PlankOakBlock.class, 4, LogOakBlock.id);

    private Block block = new PlankOak();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Block getPlacedBlock() {
        return block;
    }

    @Override
    public Image getImage() {
        return block.getImage();
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof PlankOakBlock;
    }
}