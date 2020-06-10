package net.maple3142.craft2d.game.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.CraftingTable;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.PlaceableItem;
import net.maple3142.craft2d.game.item.Stackable;

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
