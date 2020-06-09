package net.maple3142.craft2d.item;

import net.maple3142.craft2d.block.Block;

public interface Tool extends Breakable {
    double getHardnessMultiplier(Block target);
}
