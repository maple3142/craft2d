package net.maple3142.craft2d.item;

import net.maple3142.craft2d.block.Block;

public interface PlaceableItem extends Item{
    Block getPlacedBlock();
}
