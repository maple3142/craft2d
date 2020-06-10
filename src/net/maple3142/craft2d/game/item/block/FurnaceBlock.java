package net.maple3142.craft2d.game.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.Furnace;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.PlaceableItem;
import net.maple3142.craft2d.game.item.Stackable;

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
