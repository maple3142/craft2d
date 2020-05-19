package net.maple3142.craft2d.item;

import javafx.scene.image.Image;

public class WoodShovel implements Item, Breakable {
    private Image img = new Image(WoodShovel.class.getResource("/item/wood_shovel.png").toString());

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof WoodShovel;
    }

    @Override
    public int getFullDurability() {
        return 0;
    }

    @Override
    public int getDurability() {
        return 0;
    }

    @Override
    public boolean isBroken() {
        return false;
    }
}
