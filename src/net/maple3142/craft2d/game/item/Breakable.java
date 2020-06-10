package net.maple3142.craft2d.game.item;

public interface Breakable {
    int getFullDurability();

    int getDurability();

    void setDurability(int d);

    boolean isBroken();

    boolean isIntact();

    void reduceDurabilityByOne();
}
