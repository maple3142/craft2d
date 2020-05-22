package net.maple3142.craft2d.crafting;

import net.maple3142.craft2d.item.Item;

public class SimpleUnorderedRecipe<T extends Item> implements Recipe<T> {

    private Item[] items;
    private Class<T> clz;

    public SimpleUnorderedRecipe(Class<T> clz, Item... items) {
        if (items.length <= 0 || items.length >= 9) throw new IllegalArgumentException("Invalid number of items");
        this.clz = clz;
        this.items = items;
    }

    @Override
    public boolean matchInput(CraftingInput input) {
        return input.containsAll(items);
    }

    @Override
    public Class<T> getResultClass() {
        return clz;
    }
}
