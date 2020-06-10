package net.maple3142.craft2d.game.item;

import net.maple3142.craft2d.game.block.Block;

public interface Tool extends Breakable {
    double getHardnessMultiplier(Block target);
}
