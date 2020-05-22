package net.maple3142.craft2d.storage;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.UiOpenable;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;

public class PlayerInventory implements UiOpenable {
    private static final double width = 328;
    private static final double height = 40;
    private static final int bottomPadding = 10;
    private static final int itemBarBorderWidth = 4;
    private static final int itemBarItemDefaultSize = ((int) width - 10 * itemBarBorderWidth) / 9;
    private static final StorageLayout layout = new PlayerInventoryStorageLayout();
    private static final double inventoryWidth = 352;
    private static final double inventoryHeight = 332;
    public static Image inventoryBarImg = new Image(PlayerInventory.class.getResource("/ui/inventory_bar.png").toString());
    public static Image inventoryImg = new Image(PlayerInventory.class.getResource("/ui/inventory.png").toString());
    public ItemStack[] storage = new ItemStack[104]; // same layout as Minecraft survival inventory
    private ItemStack draggedStack;
    private int selected = 0;

    public ItemStack putAllItems(int id, ItemStack stk) { // minecraft's left click
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

    public ItemStack putOneItem(int id, ItemStack stk) { // minecraft's right click
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

    public void drawInventoryBar(GraphicsContext ctx, double gameWidth, double gameHeight) {
        double x = (gameWidth - width) / 2;
        double y = gameHeight - height - bottomPadding;
        ctx.drawImage(inventoryBarImg, x, y, width, height);

        double startX = x + itemBarBorderWidth;
        for (int i = 0; i <= 8; i++) {
            if (selected == i) {
                ctx.setFill(Color.RED);
                ctx.fillRect(startX, y + itemBarBorderWidth, itemBarItemDefaultSize, itemBarItemDefaultSize);
            }
            if (storage[i] != null) {
                var item = storage[i].getItem();
                drawImagePercentageCenter(ctx, item.getImage(), startX, y + itemBarBorderWidth, itemBarItemDefaultSize, itemBarItemDefaultSize, 0.75);
                int num = storage[i].getItemsNum();
                if (num > 1) {
                    ctx.setTextAlign(TextAlignment.RIGHT);
                    ctx.setTextBaseline(VPos.BOTTOM);
                    ctx.setFill(Color.WHITE);
                    ctx.fillText(String.valueOf(num), startX + itemBarItemDefaultSize, y + itemBarBorderWidth + itemBarItemDefaultSize);
                }
            }
            startX += (itemBarItemDefaultSize + itemBarBorderWidth);
        }
    }

    private void fillRowItems(GraphicsContext ctx, double x, double y, int idOffset) {
        double startX = x + itemBarBorderWidth;
        for (int i = 0; i <= 8; i++) {
            int id = i + idOffset;
            if (storage[id] != null) {
                var item = storage[id].getItem();
                drawImagePercentageCenter(ctx, item.getImage(), startX, y + itemBarBorderWidth, itemBarItemDefaultSize, itemBarItemDefaultSize, 0.75);
                int num = storage[id].getItemsNum();
                if (num > 1) {
                    ctx.setTextAlign(TextAlignment.RIGHT);
                    ctx.setTextBaseline(VPos.BOTTOM);
                    ctx.setFill(Color.WHITE);
                    ctx.fillText(String.valueOf(num), startX + itemBarItemDefaultSize, y + itemBarBorderWidth + itemBarItemDefaultSize);
                }
            }
            startX += (itemBarItemDefaultSize + itemBarBorderWidth);
        }
    }

    @Override
    public void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight) {
        double invX = (gameWidth - inventoryWidth) / 2;
        double invY = (gameHeight - inventoryHeight) / 2;
        ctx.drawImage(inventoryImg, invX, invY, inventoryWidth, inventoryHeight);
        fillRowItems(ctx, invX + 12, invY + 280, 0); // the last row
        fillRowItems(ctx, invX + 12, invY + 164, 9);
        fillRowItems(ctx, invX + 12, invY + 200, 18);
        fillRowItems(ctx, invX + 12, invY + 236, 27);

        if (draggedStack != null) {
            double mx = mouse.getX();
            double my = mouse.getY();
            var item = draggedStack.getItem();
            double size = itemBarItemDefaultSize * 0.75;
            double dsz = itemBarItemDefaultSize;
            ctx.drawImage(item.getImage(), mx - size / 2, my - size / 2, size, size);
            ctx.setTextAlign(TextAlignment.RIGHT);
            ctx.setTextBaseline(VPos.BOTTOM);
            ctx.setFill(Color.WHITE);
            int num = draggedStack.getItemsNum();
            if (num > 1) {
                ctx.fillText(String.valueOf(num), mx + dsz / 2, my + dsz / 2);
            }
        }
    }

    private void drawImagePercentageCenter(GraphicsContext ctx, Image image, double x, double y, double w, double h, double p) {
        double rw = (int) (w * p);
        double rh = (int) (h * p);
        double pw = (w - rw) / 2;
        double ph = (h - rh) / 2;
        ctx.drawImage(image, x + pw, y + ph, rw, rh);
    }

    private int calculateFromRelativePosition(double x, double y) {
        int row = -1, col = -1;

        if (166 <= y && y <= 270 && 14 <= x && x <= 334) {
            row = (int) ((y - 166) / 35) + 4; // 35=(270-166+1)/3
            col = (int) ((x - 14) / (321 / 9)); // 321=334-14+1
        } else if (284 <= y && y <= 316 && 14 <= x && x <= 334) {
            row = 7;
            col = (int) ((x - 14) / (321 / 9));
        }

        return layout.getId(row, col);
    }

    @Override
    public void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight) {
        double invX = (gameWidth - inventoryWidth) / 2;
        double invY = (gameHeight - inventoryHeight) / 2;
        double x = event.getX() - invX;
        double y = event.getY() - invY;
        int id = calculateFromRelativePosition(x, y);
        if (id != -1) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(id, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(id, draggedStack);
            }
        }
    }

    public void moveSelectionToRight() {
        selected++;
        if (selected >= 9) {
            selected -= 9;
        }
    }

    public void moveSelectionToLeft() {
        selected--;
        if (selected < 0) {
            selected += 9;
        }
    }

    public ItemStack getSelectedItemStack() {
        return storage[selected];
    }

    public void setSelectedItemStack(ItemStack stk) {
        storage[selected] = stk;
    }

    public Tool getSelectedTool() {
        var stk = storage[selected];
        if (stk != null && stk.getItem() instanceof Tool) {
            return (Tool) stk.getItem();
        }
        return null;
    }
}
