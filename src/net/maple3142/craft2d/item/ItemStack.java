package net.maple3142.craft2d.item;

public class ItemStack {
    public static int maxItems = 64;
    private int numItems;
    private final Item item;

    public ItemStack(Item item) {
        this.item = item;
        numItems = 1;
    }

    public ItemStack(Item item, int num) {
        this.item = item;
        numItems = num;
    }

    public void addItemsNum(int num) {
        if (!isStackable()) return;
        numItems += num;
        if (numItems > ItemStack.maxItems) numItems = ItemStack.maxItems;
    }

    public void removeItemsNum(int num) {
        numItems -= num;
        if (numItems < 0) numItems = 0;
    }

    public int getItemsNum() {
        if (!(item instanceof Stackable)) {
            return 1;
        }
        return numItems;
    }

    public void setItemsNum(int num) {
        numItems = num;
    }

    public Item getItem() {
        return item;
    }

    public boolean isStackable() {
        return item instanceof Stackable;
    }
}
