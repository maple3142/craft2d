package net.maple3142.craft2d.ui.storage;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.entity.Player;
import net.maple3142.craft2d.ReflectionHelper;
import net.maple3142.craft2d.UiOpenable;
import net.maple3142.craft2d.crafting.CraftingInput;
import net.maple3142.craft2d.crafting.RecipeRegistry;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.ui.BlockUi;

public class PlayerInventory extends BlockUi implements UiOpenable {
    private static final double width = 328;
    private static final double height = 40;
    private static final int bottomPadding = 10;
    private static final int itemBorderWidth = 4;
    private static final int itemDefaultSize = 32;
    private static final double inventoryWidth = 352;
    private static final double inventoryHeight = 332;
    public static Image inventoryBarImg = new Image(PlayerInventory.class.getResource("/ui/inventory_bar.png").toString());
    public static Image inventoryImg = new Image(PlayerInventory.class.getResource("/ui/inventory.png").toString());
    private int selected = 0;

    public PlayerInventory() {
        super(36); // same layout as Minecraft survival inventory
    }

    public void drawInventoryBar(GraphicsContext ctx, double gameWidth, double gameHeight) {
        double x = (gameWidth - width) / 2;
        double y = gameHeight - height - bottomPadding;
        ctx.drawImage(inventoryBarImg, x, y, width, height);

        double startX = x + itemBorderWidth;
        for (int i = 0; i <= 8; i++) {
            if (selected == i) {
                ctx.setFill(Color.RED);
                ctx.fillRect(startX, y + itemBorderWidth, itemDefaultSize, itemDefaultSize);
            }
            if (storage[i] != null) {
                var item = storage[i].getItem();
                drawImagePercentageCenter(ctx, item.getImage(), startX, y + itemBorderWidth, itemDefaultSize, itemDefaultSize, 0.75);
                int num = storage[i].getItemsNum();
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

    private void fillRowItems(GraphicsContext ctx, double x, double y, int idOffset) {
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
    public void onOpened(Player player) {

    }

    @Override
    public void onClosed(Player player) {

    }

    @Override
    public void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Player player) {
        double invX = (gameWidth - inventoryWidth) / 2;
        double invY = (gameHeight - inventoryHeight) / 2;
        ctx.drawImage(inventoryImg, invX, invY, inventoryWidth, inventoryHeight);

        fillRowItems(ctx, invX + 12, invY + 280, 0); // the last row
        fillRowItems(ctx, invX + 12, invY + 164, 9);
        fillRowItems(ctx, invX + 12, invY + 200, 18);
        fillRowItems(ctx, invX + 12, invY + 236, 27);

        double dx = itemDefaultSize + itemBorderWidth;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int id = i * 2 + j;
                if (craftingStorage[id] != null) {
                    var item = craftingStorage[id].getItem();
                    drawImagePercentageCenter(ctx, item.getImage(), invX + 176 + dx * j, invY + 52 + dx * i, itemDefaultSize, itemDefaultSize, 0.75);
                    int num = craftingStorage[id].getItemsNum();
                    if (num > 1) {
                        ctx.setTextAlign(TextAlignment.RIGHT);
                        ctx.setTextBaseline(VPos.BOTTOM);
                        ctx.setFill(Color.WHITE);
                        ctx.fillText(String.valueOf(num), invX + 176 + dx * j + itemDefaultSize, invY + 52 + dx * i + itemDefaultSize);
                    }
                }
            }
        }

        if (craftingStorage[4] != null) {
            var item = craftingStorage[4].getItem();
            drawImagePercentageCenter(ctx, item.getImage(), invX + 288, invY + 72, itemDefaultSize, itemDefaultSize, 0.75); // result block is bigger
            int num = craftingStorage[4].getItemsNum();
            if (num > 1) {
                ctx.setTextAlign(TextAlignment.RIGHT);
                ctx.setTextBaseline(VPos.BOTTOM);
                ctx.setFill(Color.WHITE);
                ctx.fillText(String.valueOf(num), invX + 288 + itemDefaultSize, invY + 72 + itemDefaultSize);
            }
        }

        drawDraggedStack(ctx, mouse, itemDefaultSize);
    }

    private ItemStack[] craftingStorage = new ItemStack[5];

    @Override
    public void handleMousePressedRelativeCoordinates(MouseEvent event, double x, double y, Player player) {
        int id = calculateIdFromRelativePosition(x, y);
        if (id != -1) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(storage, id, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(storage, id, draggedStack);
            }
        }

        int craftingId = calculateIdFromRelativePositionForCrafting(x, y);
        if (craftingId == 4) {
            handleResultBlock(craftingStorage, event);
        } else if (craftingId != -1) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(craftingStorage, craftingId, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(craftingStorage, craftingId, draggedStack);
            }
        }
    }

    public int calculateIdFromRelativePositionForCrafting(double x, double y) {
        if (70 <= y && y <= 104 && 286 <= x && x <= 320) {
            return 4;
        }

        int row = -1, col = -1;
        if (48 <= y && y <= 120 && 174 <= x && x <= 244) {
            col = (int) ((x - 174) / 35);
            row = (int) ((y - 48) / 35);
        }
        if (row != -1 && col != -1) {
            return row * 2 + col;
        }
        return -1;
    }

    private void handleResultBlock(ItemStack[] craftingArea, MouseEvent event) {
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

    public int calculateIdFromRelativePosition(double x, double y) {
        int row = -1, col = -1;

        if (166 <= y && y <= 270 && 14 <= x && x <= 334) {
            row = (int) ((y - 166) / 35); // 35=(270-166+1)/3
            col = (int) ((x - 14) / (321 / 9)); // 321=334-14+1
        } else if (284 <= y && y <= 316 && 14 <= x && x <= 334) {
            row = 3;
            col = (int) ((x - 14) / (321 / 9));
        }

        if (col < 0 || col >= 9) return -1;
        if (row == 3) {
            return col;
        }
        if (0 <= row && row < 3) {
            return row * 9 + col + 9;
        }
        return -1;
    }

    @Override
    public void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Player player) {
        double invX = (gameWidth - inventoryWidth) / 2;
        double invY = (gameHeight - inventoryHeight) / 2;
        handleMousePressedRelativeCoordinates(event, event.getX() - invX, event.getY() - invY, player);

        var arr = new Item[9];
        // from 2x2 to 3x3
        if (craftingStorage[0] != null) arr[0] = craftingStorage[0].getItem();
        if (craftingStorage[1] != null) arr[1] = craftingStorage[1].getItem();
        if (craftingStorage[2] != null) arr[3] = craftingStorage[2].getItem();
        if (craftingStorage[3] != null) arr[4] = craftingStorage[3].getItem();
        var recipe = RecipeRegistry.getInstance().findMatchedRecipe(new CraftingInput(arr));
        if (recipe == null) {
            craftingStorage[4] = null;
        } else {
            craftingStorage[4] = new ItemStack(ReflectionHelper.constructFromEmptyConstructor(recipe.getResultClass()), recipe.getResultNum());
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
