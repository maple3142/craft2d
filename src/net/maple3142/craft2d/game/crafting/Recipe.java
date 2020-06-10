package net.maple3142.craft2d.game.crafting;

import net.maple3142.craft2d.game.item.Item;

public interface Recipe<T extends Item> {
    boolean matchInput(CraftingInput input);

    Class<T> getResultClass();

    int getResultNum();
}
