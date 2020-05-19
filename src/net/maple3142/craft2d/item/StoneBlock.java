package net.maple3142.craft2d.item;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Stone;

public class StoneBlock implements PlaceableItem, Stackable {

    private Block block = new Stone();

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
        return item instanceof StoneBlock;
    }
}
