package net.maple3142.craft2d.game.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.Dirt;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.PlaceableItem;
import net.maple3142.craft2d.game.item.Stackable;

public class DirtBlock implements PlaceableItem, Stackable {

    public static final int id = 1;
    private final Block block = new Dirt();

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
        return item instanceof DirtBlock;
    }
}
