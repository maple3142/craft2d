package net.maple3142.craft2d.game.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.Chest;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.PlaceableItem;
import net.maple3142.craft2d.game.item.Stackable;

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
