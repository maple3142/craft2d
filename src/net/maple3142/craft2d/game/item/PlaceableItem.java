package net.maple3142.craft2d.game.item;

import net.maple3142.craft2d.game.block.Block;

public interface PlaceableItem extends Item {
    Block getPlacedBlock();
}
