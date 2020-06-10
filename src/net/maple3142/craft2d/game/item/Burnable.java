package net.maple3142.craft2d.game.item;

public interface Burnable {
    ItemStack getResultItemStack();

    boolean isTargetCompatible(ItemStack stk);
}
