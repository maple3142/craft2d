package net.maple3142.craft2d.game.crafting;

import net.maple3142.craft2d.game.item.Item;

public class CraftingInput {
    private final Item[] items;

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
            if (item == null) continue;
            if (item.equals(it)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(int id) {
        if (id == 0) throw new IllegalArgumentException("id can't be 0 here");
        for (var item : items) {
            if (item == null) continue;
            if (item.getId() == id) {
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

    public boolean containsAll(int... args) {
        for (var it : args) {
            if (!contains(it)) {
                return false;
            }
        }
        return true;
    }

}
