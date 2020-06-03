package net.maple3142.craft2d.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Chest;
import net.maple3142.craft2d.block.Furnace;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.PlaceableItem;
import net.maple3142.craft2d.item.Stackable;

public class FurnaceBlock implements PlaceableItem, Stackable {

    public static final int id = 22;

    public static final Recipe<FurnaceBlock> recipe = new SimpleRecipe<>(FurnaceBlock.class, 1, 3, 3,
            CobblestoneBlock.id, CobblestoneBlock.id, CobblestoneBlock.id,
            CobblestoneBlock.id, 0, CobblestoneBlock.id,
            CobblestoneBlock.id, CobblestoneBlock.id, CobblestoneBlock.id);

    private final Block block = new Furnace();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Block getPlacedBlock() {
        // It shouldn't share the same block, or storage will be same.
        return new Furnace();
    }

    @Override
    public Image getImage() {
        return block.getImage();
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof FurnaceBlock;
    }
}
