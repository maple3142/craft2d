package net.maple3142.craft2d.ui.storage;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.ui.BlockUi;
import net.maple3142.craft2d.ui.UiOpenable;

public class ChestUi extends BlockUi implements UiOpenable {

    private static final double width = 352;
    private static final double height = 332;
    private static final int itemBorderWidth = 4;
    private static final int itemDefaultSize = 32;
    public static Image img = new Image(PlayerInventory.class.getResource("/ui/chest.png").toString());

    public ChestUi(ItemStack[] storage) {
        this.storage = storage;
    }

    private void fillRowItems(ItemStack[] storage, GraphicsContext ctx, double x, double y, int idOffset) {
        double startX = x + itemBorderWidth;
        for (int i = 0; i <= 8; i++) {
            int id = i + idOffset;
            if (storage[id] != null) {
                var item = storage[id].getItem();
                drawImagePercentageCenter(ctx, item.getImage(), startX, y + itemBorderWidth, itemDefaultSize, itemDefaultSize, 0.75);
                int num = storage[id].getItemsNum();
                if (num > 1) {
                    ctx.setTextAlign(TextAlignment.RIGHT);
                    ctx.setTextBaseline(VPos.BOTTOM);
                    ctx.setFill(Color.WHITE);
                    ctx.fillText(String.valueOf(num), startX + itemDefaultSize, y + itemBorderWidth + itemDefaultSize);
                }
            }
            startX += (itemDefaultSize + itemBorderWidth);
        }
    }

    @Override
    public void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Game game) {
        var player = game.player;
        double cX = (gameWidth - width) / 2;
        double cY = (gameHeight - height) / 2;
        ctx.drawImage(img, cX, cY, width, height);

        fillRowItems(storage, ctx, cX + 12, cY + 28, 0);
        fillRowItems(storage, ctx, cX + 12, cY + 64, 9);
        fillRowItems(storage, ctx, cX + 12, cY + 100, 18);

        {
            fillRowItems(player.inventory.storage, ctx, cX + 12, cY + 280, 0); // the last row
            fillRowItems(player.inventory.storage, ctx, cX + 12, cY + 164, 9);
            fillRowItems(player.inventory.storage, ctx, cX + 12, cY + 200, 18);
            fillRowItems(player.inventory.storage, ctx, cX + 12, cY + 236, 27);
        }

        drawDraggedStack(ctx, mouse, itemDefaultSize);
    }

    @Override
    public void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Game game) {
        double cX = (gameWidth - width) / 2;
        double cY = (gameHeight - height) / 2;
        handleMousePressedRelativeCoordinates(event, event.getX() - cX, event.getY() - cY, game);
    }

    @Override
    public void handleMousePressedRelativeCoordinates(MouseEvent event, double x, double y, Game game) {
        int id = calculateIdFromRelativePosition(x, y);
        if (id == -2) {
            dropDraggedStack(game);
            return;
        }
        if (id >= 0) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(storage, id, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(storage, id, draggedStack);
            }
        }

        var inv = game.player.inventory;
        int invId = inv.calculateIdFromRelativePosition(x, y); // because crafting table and inventory have same size
        if (invId >= 0) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(inv.storage, invId, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(inv.storage, invId, draggedStack);
            }
        }
    }

    @Override
    protected int calculateIdFromRelativePosition(double x, double y) {
        if (x < 0 || y < 0 || x > width || y > height) return -2;
        int row = -1, col = -1;
        if (30 <= y && y <= 134 && 14 <= x && x <= 334) {
            row = (int) ((y - 30) / 35); // 35=(134-30+1)/3
            col = (int) ((x - 14) / (321 / 9)); // 321=334-14+1
        }
        if (row >= 0 && row < 3 && col >= 0 && col < 9) {
            return row * 9 + col;
        }
        return -1;
    }
}
