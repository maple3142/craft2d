package net.maple3142.craft2d.game.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.PlankOak;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Fuel;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.PlaceableItem;
import net.maple3142.craft2d.game.item.Stackable;

public class PlankOakBlock implements PlaceableItem, Stackable, Fuel {

    public static final int id = 5;

    public static final Recipe<PlankOakBlock> recipe = new SimpleRecipe<>(PlankOakBlock.class, 4, 1, 1, LogOakBlock.id);

    private final Block block = new PlankOak();

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

    @Override
    public double getEnergyUnit() {
        return 1.5;
    }
}
