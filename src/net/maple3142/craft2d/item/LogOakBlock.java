package net.maple3142.craft2d.item;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Dirt;
import net.maple3142.craft2d.block.LogOak;

public class LogOakBlock implements PlaceableItem, Stackable {

    private Block block = new LogOak();

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
        return item instanceof LogOakBlock;
    }
}
