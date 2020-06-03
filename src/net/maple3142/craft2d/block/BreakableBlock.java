package net.maple3142.craft2d.block;

import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;

public interface BreakableBlock extends Block {
    double getHardness();

    ItemStack getDroppedItem(Tool brokeBy);
}
