package net.maple3142.craft2d.item;

public class ItemStack {
    public static int maxItems = 64;
    private int numItems;
    private Item item;

    public ItemStack(Item item) {
        this.item = item;
        numItems = 1;
    }

    public void addItems(int num) {
        if (num <= 0) throw new IllegalArgumentException("num should be greater than 0");
        numItems += num;
    }

    public void removeItems(int num) {
        if (num <= 0) throw new IllegalArgumentException("num should be greater than 0");
        numItems -= num;
    }

    public int getItemsNum() {
        return numItems;
    }

    public Item getItem() {
        return item;
    }
}
