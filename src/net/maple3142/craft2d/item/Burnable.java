package net.maple3142.craft2d.item;

public interface Burnable {
    ItemStack getResultItemStack();

    boolean isTargetCompatible(ItemStack stk);
}
