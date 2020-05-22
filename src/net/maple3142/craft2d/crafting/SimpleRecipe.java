package net.maple3142.craft2d.crafting;

import net.maple3142.craft2d.item.Item;

public class SimpleRecipe<T extends Item> implements Recipe<T> {

    private int width;
    private int height;
    private int[] ids;
    private Class<T> clz;

    public SimpleRecipe(Class<T> clz, int width, int height, int... ids) {
        // a simple recipe helper to match id only
        if (width <= 0 || width > 3 || height <= 0 || height > 3)
            throw new IllegalArgumentException("Invalid width or height");
        if (ids.length != width * height) throw new IllegalArgumentException("ids length must me width*height");

        this.clz = clz;
        this.width = width;
        this.height = height;
        this.ids = ids;
    }

    private boolean matchSubBlock(CraftingInput input, int hs, int vs) {
        int i = 0;
        for (int c = hs; c < hs + width; c++) {
            for (int r = vs; r < vs + height; r++) {
                if (input.getItemId(r, c) != ids[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean matchInput(CraftingInput input) {
        for (int hs = 0; hs <= 3 - width; hs++) {
            for (int vs = 0; vs <= 3 - height; vs++) {
                if (matchSubBlock(input, hs, vs)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Class<T> getResultClass() {
        return clz;
    }
}
