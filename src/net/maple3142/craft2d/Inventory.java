package net.maple3142.craft2d;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.item.ItemStack;

public class Inventory {
    public static Image image = new Image(Inventory.class.getResource("/ui/inventory_bar.png").toString());
    private static double width = 328;
    private static double height = 40;
    private static int bottomPadding = 10;
    private static int itemBarBorderWidth = 4;
    private static int itemBarItemDefaultSize = ((int) width - 10 * itemBarBorderWidth) / 9;

    public ItemStack[] storage = new ItemStack[104]; // same layout as Minecraft survival inventory

    public void drawInventoryBar(GraphicsContext ctx, double gameWidth, double gameHeight) {
        double x = (gameWidth - width) / 2;
        double y = gameHeight - height - bottomPadding;
        ctx.drawImage(image, x, y, width, height);

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
