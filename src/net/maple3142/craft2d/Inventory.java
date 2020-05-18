package net.maple3142.craft2d;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.item.ItemStack;

public class Inventory implements UiOpenable {
    public static Image inventoryBarImg = new Image(Inventory.class.getResource("/ui/inventory_bar.png").toString());
    public static Image inventoryImg = new Image(Inventory.class.getResource("/ui/inventory.png").toString());
    private static final double width = 328;
    private static final double height = 40;
    private static final int bottomPadding = 10;
    private static final int itemBarBorderWidth = 4;
    private static final int itemBarItemDefaultSize = ((int) width - 10 * itemBarBorderWidth) / 9;

    public ItemStack[] storage = new ItemStack[104]; // same layout as Minecraft survival inventory

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

    private static final double inventoryWidth = 352;
    private static final double inventoryHeight = 332;

    @Override
    public void drawUi(GraphicsContext ctx, double gameWidth, double gameHeight) {
        double invX = (gameWidth - inventoryWidth) / 2;
        double invY = (gameHeight - inventoryHeight) / 2;
        ctx.drawImage(inventoryImg, invX, invY, inventoryWidth, inventoryHeight);
        fillRowItems(ctx, invX + 12, invY + 280, 0); // the last row
        fillRowItems(ctx, invX + 12, invY + 164, 9);
        fillRowItems(ctx, invX + 12, invY + 200, 18);
        fillRowItems(ctx, invX + 12, invY + 236, 27);
    }

    private void drawImagePercentageCenter(GraphicsContext ctx, Image image, double x, double y, double w, double h, double p) {
        double rw = (int) (w * p);
        double rh = (int) (h * p);
        double pw = (w - rw) / 2;
        double ph = (h - rh) / 2;
        ctx.drawImage(image, x + pw, y + ph, rw, rh);
    }

    private int selected = 0;

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
}
