package net.maple3142.craft2d.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Chest;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.PlaceableItem;
import net.maple3142.craft2d.item.Stackable;

public class ChestBlock implements PlaceableItem, Stackable {

    public static final int id = 11;

    public static final Recipe<ChestBlock> recipe = new SimpleRecipe<>(ChestBlock.class, 1, 3, 3,
            PlankOakBlock.id, PlankOakBlock.id, PlankOakBlock.id,
            PlankOakBlock.id, 0, PlankOakBlock.id,
            PlankOakBlock.id, PlankOakBlock.id, PlankOakBlock.id);

    private final Block block = new Chest();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Block getPlacedBlock() {
        // It shouldn't share the same block, or storage will be same.
        return new Chest();
    }

    @Override
    public Image getImage() {
        return block.getImage();
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof ChestBlock;
    }
}
