package net.maple3142.craft2d.item;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.LogOak;
import net.maple3142.craft2d.block.PlankOak;

public class PlankOakBlock implements PlaceableItem, Stackable {

    private Block block = new PlankOak();

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
