package net.maple3142.craft2d.crafting;

import net.maple3142.craft2d.item.Item;

public interface Recipe<T extends Item> {
    boolean matchInput(CraftingInput input);

    Class<T> getResultClass();

    int getResultNum();
}
