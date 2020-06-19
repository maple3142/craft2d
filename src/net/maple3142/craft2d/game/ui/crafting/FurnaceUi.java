package net.maple3142.craft2d.game.ui.crafting;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.block.Furnace;
import net.maple3142.craft2d.game.item.ItemStack;
import net.maple3142.craft2d.game.ui.BlockUi;
import net.maple3142.craft2d.game.ui.UiOpenable;
import net.maple3142.craft2d.game.ui.storage.PlayerInventory;
import net.maple3142.craft2d.game.utils.MouseTracker;

public class FurnaceUi extends BlockUi implements UiOpenable {

    private static final double width = 352;
    private static final double height = 332;
    private static final int itemBorderWidth = 4;
    private static final int itemDefaultSize = 32;
    public static Image img = new Image(PlayerInventory.class.getResource("/ui/furnace.png").toString());
    public static Image imgFire = new Image(PlayerInventory.class.getResource("/ui/furnace_fire.png").toString());
    public static Image imgArrow = new Image(PlayerInventory.class.getResource("/ui/furnace_arrow.png").toString());

    private final Furnace furnace;

    public FurnaceUi(Furnace furnace) {
        this.furnace = furnace;
        this.storage = furnace.storage;
    }

    @Override
    public void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Game game) {
        double fX = (gameWidth - width) / 2;
        double fY = (gameHeight - height) / 2;
        ctx.drawImage(img, fX, fY, width, height);

        fillText(ctx, "Furnace", fX + 136, fY + 6);
        fillText(ctx, "Inventory", fX + 14, fY + 142);

        if (furnace.energyTime > 0) {
            final int imgSize = 14;
            final int size = 28;
            double percent = furnace.energyTime / furnace.maxEnergyTime;
            double imgOffset = imgSize * (1 - percent);
            double offset = size * (1 - percent);
            ctx.drawImage(imgFire,
                    0, imgOffset, imgSize, imgSize * percent,
                    fX + 112, fY + 74 + offset, size, size * percent);
        }

        if (furnace.waitTime > 0) {
            final int imgWidth = 22;
            final int width = 44;
            double percent = furnace.currentTime / furnace.waitTime;
            ctx.drawImage(imgArrow,
                    0, 0, imgWidth * percent, 17,
                    fX + 160, fY + 70, width * percent, 32);
        }

        drawStackWithItem(ctx, storage[0], fX + 112, fY + 34, itemDefaultSize);
        drawStackWithItem(ctx, storage[1], fX + 112, fY + 106, itemDefaultSize);
        drawStackWithItem(ctx, storage[2], fX + 224, fY + 62, 48);

        var player = game.player;
        {
            fillRowItems(player.inventory.storage, ctx, fX + 12, fY + 280, 0, itemDefaultSize, itemBorderWidth); // the last row
            fillRowItems(player.inventory.storage, ctx, fX + 12, fY + 164, 9, itemDefaultSize, itemBorderWidth);
            fillRowItems(player.inventory.storage, ctx, fX + 12, fY + 200, 18, itemDefaultSize, itemBorderWidth);
            fillRowItems(player.inventory.storage, ctx, fX + 12, fY + 236, 27, itemDefaultSize, itemBorderWidth);
        }

        drawDraggedStack(ctx, mouse, itemDefaultSize);

        int id = calculateIdFromRelativePosition(mouse.getX() - fX, mouse.getY() - fY);
        if (id >= 0) {
            drawItemStackLabel(ctx, storage[id], mouse.getX() + 20, mouse.getY());
        }
        var inv = game.player.inventory;
        int id2 = inv.calculateIdFromRelativePosition(mouse.getX() - fX, mouse.getY() - fY);
        if (id2 >= 0) {
            drawItemStackLabel(ctx, inv.storage[id2], mouse.getX() + 20, mouse.getY());
        }
    }

    @Override
    protected int calculateIdFromRelativePosition(double x, double y) {
        if (x < 0 || y < 0 || x > width || y > height) return -2;
        if (110 <= x && x <= 144 && 32 <= y && y <= 68) return 0;
        if (110 <= x && x <= 144 && 104 <= y && y <= 138) return 1;
        if (222 <= x && x <= 272 && 60 <= y && y <= 110) return 2;
        return -1;
    }

    @Override
    public void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Game game) {
        double fX = (gameWidth - width) / 2;
        double fY = (gameHeight - height) / 2;
        handleMousePressedRelativeCoordinates(event, event.getX() - fX, event.getY() - fY, game);

        furnace.tryConsumeFuel();
    }

    @Override
    public void handleMousePressedRelativeCoordinates(MouseEvent event, double x, double y, Game game) {
        int id = calculateIdFromRelativePosition(x, y);
        if (id == -2) {
            dropDraggedStack(game);
            return;
        }
        var originalBurnedItem = storage[0] != null ? storage[0].getItem() : null;
        if (id == 2) {
            handleResultBlock(storage, event); // special handling for result block
        } else if (id >= 0) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(storage, id, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(storage, id, draggedStack);
            }
        }
        var afterBurnedItem = storage[0] != null ? storage[0].getItem() : null;
        if (originalBurnedItem != null && afterBurnedItem != null && !originalBurnedItem.equals(afterBurnedItem)) {
            // aborted
            furnace.waitTime = 0;
            furnace.currentTime = 0;
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
    protected void handleResultBlock(ItemStack[] craftingArea, MouseEvent event) {
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
            }
        }
    }
}
