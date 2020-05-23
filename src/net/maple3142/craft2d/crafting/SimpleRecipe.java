package net.maple3142.craft2d.crafting;

import net.maple3142.craft2d.item.Item;

public class SimpleRecipe<T extends Item> implements Recipe<T> {

    private int width;
    private int height;
    private int[] ids;
    private Class<T> clz;
    private int num;

    public SimpleRecipe(Class<T> clz, int num, int width, int height, int... ids) {
        // a simple recipe helper to match id only
        if (width <= 0 || width > 3 || height <= 0 || height > 3)
            throw new IllegalArgumentException("Invalid width or height");
        if (ids.length != width * height) throw new IllegalArgumentException("ids length must me width*height");

        this.clz = clz;
        this.num = num;
        this.width = width;
        this.height = height;
        this.ids = ids;
    }

    private boolean matchSubBlock(CraftingInput input, int hs, int vs) {
        int i = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                boolean isInSubBlock = hs <= c && c < hs + width && vs <= r && r < vs + height;
                if (isInSubBlock) {
                    if (input.getItemId(r, c) != ids[i]) {
                        return false;
                    }
                    i++;
                } else {
                    if (input.getItemId(r, c) != 0) {
                        return false;
                    }
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

    @Override
    public int getResultNum() {
        return num;
    }
}
