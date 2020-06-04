package net.maple3142.craft2d.ui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.entity.FloatingItem;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.utils.Vector2;

public abstract class BlockUi implements UiOpenable {

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

    protected void fillRowItems(ItemStack[] storage, GraphicsContext ctx, double x, double y, int idOffset, int size, int borderWidth) {
        double startX = x + borderWidth;
        for (int i = 0; i <= 8; i++) {
            int id = i + idOffset;
            if (storage[id] != null) {
                var item = storage[id].getItem();
                drawImagePercentageCenter(ctx, item.getImage(), startX, y + borderWidth, size, size, 0.75);
                int num = storage[id].getItemsNum();
                if (num > 1) {
                    ctx.setTextAlign(TextAlignment.RIGHT);
                    ctx.setTextBaseline(VPos.BOTTOM);
                    ctx.setFill(Color.WHITE);
                    ctx.fillText(String.valueOf(num), startX + size, y + borderWidth + size);
                }
            }
            startX += (size + borderWidth);
        }
    }

    protected void drawStackWithItem(GraphicsContext ctx, ItemStack stk, double x, double y, int size) {
        if (stk != null) {
            var item = stk.getItem();
            drawImagePercentageCenter(ctx, item.getImage(), x, y, size, size, 0.75);
            int num = stk.getItemsNum();
            if (num > 1) {
                ctx.setTextAlign(TextAlignment.RIGHT);
                ctx.setTextBaseline(VPos.BOTTOM);
                ctx.setFill(Color.WHITE);
                ctx.fillText(String.valueOf(num), x + size, y + size);
            }
        }
    }

    protected boolean removeFromStorage(ItemStack[] storage, int id, int num) {
        if (storage[id] == null) return false;
        if (storage[id].getItemsNum() > num) {
            storage[id].removeItemsNum(num);
            return true;
        } else if (storage[id].getItemsNum() == num) {
            storage[id] = null;
            return true;
        }
        return false;
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

    protected void handleResultBlock(ItemStack[] craftingArea, MouseEvent event) {
        // this is for crafting result handling, need to be overloaded for other uses
        int resultId = craftingArea.length - 1;
        if (craftingArea[resultId] == null) return;
        int maxItems = craftingArea[resultId].isStackable() ? ItemStack.maxItems : 1;
        if (draggedStack == null ||
                (craftingArea[resultId].getItem().equals(draggedStack.getItem()) && craftingArea[resultId].getItemsNum() + draggedStack.getItemsNum() <= maxItems)) {
            if (event.isPrimaryButtonDown() || event.isSecondaryButtonDown()) {
                if (draggedStack == null) {
                    draggedStack = craftingArea[resultId];
                } else {
                    draggedStack.addItemsNum(craftingArea[resultId].getItemsNum());
                }
                craftingArea[resultId] = null;

                // remove 1 items from crafting panel
                for (int i = 0; i < craftingArea.length - 1; i++) {
                    if (craftingArea[i] != null) {
                        if (craftingArea[i].getItemsNum() == 1) {
                            craftingArea[i] = null;
                        } else {
                            craftingArea[i].removeItemsNum(1);
                        }
                    }
                }
            }
        }
    }

    public void handleMousePressedRelativeCoordinates(MouseEvent event, double x, double y, Game game) {
        int id = calculateIdFromRelativePosition(x, y);
        if (id != -1) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(storage, id, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(storage, id, draggedStack);
            }
        }
    }

    @Override
    public void onOpened(Game game) {

    }

    @Override
    public void onClosed(Game game) {
        dropDraggedStack(game);
    }

    public void dropDraggedStack(Game game) {
        if (draggedStack != null) {
            var pos = game.player.position;
            // TODO: change this according to player's facing
            var newPos = new Vector2(pos.x + 2, pos.y);
            var item = new FloatingItem(draggedStack, newPos);
            game.entities.add(item);
            draggedStack = null;
        }
    }

    protected abstract int calculateIdFromRelativePosition(double x, double y); // -1=noop -2=outside of ui
}
