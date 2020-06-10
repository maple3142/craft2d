package net.maple3142.craft2d.game.block;

import net.maple3142.craft2d.game.item.ItemStack;
import net.maple3142.craft2d.game.item.Tool;

public interface BreakableBlock extends Block {
    double getHardness();

    ItemStack getDroppedItem(Tool brokeBy);
}
