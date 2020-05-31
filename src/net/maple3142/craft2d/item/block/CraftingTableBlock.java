package net.maple3142.craft2d.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.CraftingTable;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.PlaceableItem;
import net.maple3142.craft2d.item.Stackable;

public class CraftingTableBlock implements PlaceableItem, Stackable {

    public static final int id = 10;

    public static final Recipe<CraftingTableBlock> recipe = new SimpleRecipe<>(CraftingTableBlock.class, 1, 2, 2,
            PlankOakBlock.id, PlankOakBlock.id,
            PlankOakBlock.id, PlankOakBlock.id);

    private final Block block = new CraftingTable();

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
        return item instanceof CraftingTableBlock;
    }
}
