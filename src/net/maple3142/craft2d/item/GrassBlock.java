package net.maple3142.craft2d.item;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Grass;

public class GrassBlock implements PlaceableItem {

    private Block block = new Grass();

    @Override
    public Block getPlacedBlock() {
        return block;
    }

    @Override
    public Image getImage() {
        return block.getImage();
    }
}
