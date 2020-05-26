package net.maple3142.craft2d.crafting;

import net.maple3142.craft2d.item.Item;

public class SimpleUnorderedRecipe<T extends Item> implements Recipe<T> {

    private int[] ids;
    private Class<T> clz;
    private int num;

    public SimpleUnorderedRecipe(Class<T> clz, int num, int... ids) {
        if (ids.length <= 0 || ids.length >= 9) throw new IllegalArgumentException("Invalid number of items");
        this.clz = clz;
        this.num = num;
        this.ids = ids;
    }

    @Override
    public boolean matchInput(CraftingInput input) {
        return input.containsAll(ids);
    }

    @Override
    public Class<T> getResultClass() {
        return clz;
    }

    @Override
    public int getResultNum() {
        return num;
    }
}
