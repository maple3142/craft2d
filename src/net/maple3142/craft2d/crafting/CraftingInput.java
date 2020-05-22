package net.maple3142.craft2d.crafting;

import net.maple3142.craft2d.item.Item;

public class CraftingInput {
    private Item[] items;

    public CraftingInput(Item... items) {
        if (items.length != 9) throw new IllegalArgumentException("Invalid crafting input");
        this.items = items;
    }

    public Item getItem(int r, int c) {
        int id = r * 3 + c;
        return items[id];
    }

    public int getItemId(int r, int c) {
        var item = getItem(r, c);
        return item == null ? 0 : item.getId();
    }

    public Item[] getItems() {
        return items;
    }

    public boolean contains(Item it) {
        for (var item : items) {
            if (item.equals(it)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(Item... args) {
        for (var it : args) {
            if (!contains(it)) {
                return false;
            }
        }
        return true;
    }

}
