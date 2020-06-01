package net.maple3142.craft2d.ui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.entity.Player;
import net.maple3142.craft2d.item.ItemStack;

public abstract class BlockUi {

    public ItemStack[] storage;
    protected ItemStack draggedStack;

    protected BlockUi() {
    }

    protected BlockUi(int storageSize) {
        storage = new ItemStack[storageSize];
    }

    protected void drawDraggedStack(GraphicsContext ctx, MouseTracker mouse, double gridSize) {
        if (draggedStack != null) {
            double mx = mouse.getX();
            double my = mouse.getY();
            var item = draggedStack.getItem();
            double iconSize = gridSize * 0.75;
            ctx.drawImage(item.getImage(), mx - iconSize / 2, my - iconSize / 2, iconSize, iconSize);
            ctx.setTextAlign(TextAlignment.RIGHT);
            ctx.setTextBaseline(VPos.BOTTOM);
            ctx.setFill(Color.WHITE);
            int num = draggedStack.getItemsNum();
            if (num > 1) {
                ctx.fillText(String.valueOf(num), mx + gridSize / 2, my + gridSize / 2);
            }
        }
    }

    protected void drawImagePercentageCenter(GraphicsContext ctx, Image image, double x, double y, double w, double h, double p) {
        double rw = (int) (w * p);
        double rh = (int) (h * p);
        double pw = (w - rw) / 2;
        double ph = (h - rh) / 2;
        ctx.drawImage(image, x + pw, y + ph, rw, rh);
    }

    protected ItemStack putAllItems(ItemStack[] storage, int id, ItemStack stk) { // minecraft's left click
        // returns remaining stack
        if (stk == null) {
            var tmp = storage[id];
            storage[id] = null;
            return tmp;
        }
        if (storage[id] == null) {
            storage[id] = stk;
            return null;
        }
        if (!storage[id].isStackable() || !stk.isStackable()) {
            var tmp = storage[id];
            storage[id] = stk;
            return tmp;
        }
        if (storage[id].getItem().equals(stk.getItem())) {
            int s1 = storage[id].getItemsNum();
            int s2 = stk.getItemsNum();
            if (s1 + s2 > ItemStack.maxItems) {
                storage[id].setItemsNum(ItemStack.maxItems);
                stk.setItemsNum(s1 + s2 - ItemStack.maxItems);
                return stk;
            } else {
                storage[id].addItemsNum(s2);
                return null;
            }
        }
        var tmp = storage[id];
        storage[id] = stk;
        return tmp;
    }

    protected ItemStack putOneItem(ItemStack[] storage, int id, ItemStack stk) { // minecraft's right click
        // returns remaining stack
        if (stk == null) {
            var tmp = storage[id];
            storage[id] = null;
            return tmp;
        }
        if (storage[id] == null) {
            storage[id] = new ItemStack(stk.getItem());
            if (stk.getItemsNum() == 1) return null;
            else {
                stk.removeItemsNum(1);
                return stk;
            }
        }
        if (!storage[id].isStackable() || !stk.isStackable()) {
            var tmp = storage[id];
            storage[id] = stk;
            return tmp;
        }
        if (storage[id].getItem().equals(stk.getItem())) {
            int s1 = storage[id].getItemsNum();
            int s2 = stk.getItemsNum();
            if (s1 < ItemStack.maxItems) {
                storage[id].addItemsNum(1);
                if (s2 == 1) return null;
                else {
                    stk.removeItemsNum(1);
                    return stk;
                }
            } else {
                // do nothing if full
                return stk;
            }
        }
        var tmp = storage[id];
        storage[id] = stk;
        return tmp;
    }

    public void handleMousePressedRelativeCoordinates(MouseEvent event, double x, double y, Player player) {
        int id = calculateIdFromRelativePosition(x, y);
        if (id != -1) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(storage, id, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(storage, id, draggedStack);
            }
        }
    }

    protected abstract int calculateIdFromRelativePosition(double x, double y);
}
