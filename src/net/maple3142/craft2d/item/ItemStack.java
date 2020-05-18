package net.maple3142.craft2d.item;

public class ItemStack {
    public static int maxItems = 64;
    private int numItems;
    private Item item;

    public ItemStack(Item item) {
        this.item = item;
        numItems = 1;
    }

    public ItemStack(Item item, int num) {
        this.item = item;
        numItems = num;
    }

    public void setItemsNum(int num) {
        numItems = num;
    }

    public void addItemsNum(int num) {
        numItems += num;
    }

    public void removeItemsNum(int num) {
        numItems -= num;
    }

    public int getItemsNum() {
        return numItems;
    }

    public Item getItem() {
        return item;
    }
}
