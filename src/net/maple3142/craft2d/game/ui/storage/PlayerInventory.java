package net.maple3142.craft2d.game.ui.storage;

import com.google.gson.annotations.Expose;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.crafting.CraftingInput;
import net.maple3142.craft2d.game.crafting.RecipeRegistry;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.ItemStack;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.ui.BlockUi;
import net.maple3142.craft2d.game.ui.UiOpenable;
import net.maple3142.craft2d.game.utils.MouseTracker;
import net.maple3142.craft2d.game.utils.ReflectionHelper;

public class PlayerInventory extends BlockUi implements UiOpenable {
    private static final double barWidth = 328;
    private static final double barHeight = 40;
    private static final int bottomPadding = 10;
    private static final int itemBorderWidth = 4;
    private static final int itemDefaultSize = 32;
    private static final double inventoryWidth = 352;
    private static final double inventoryHeight = 332;
    public static Image inventoryBarImg = new Image(PlayerInventory.class.getResource("/ui/inventory_bar.png").toString());
    public static Image inventoryImg = new Image(PlayerInventory.class.getResource("/ui/inventory.png").toString());
    @Expose
    private int selected = 0;
    private final ItemStack[] craftingStorage = new ItemStack[5];

    public PlayerInventory() {
        super(36); // same layout as Minecraft survival inventory
    }

    public ItemStack tryFillItemStack(ItemStack stk) {
        int i = 0;
        while (stk != null && stk.getItemsNum() > 0) {
            if (storage[i] == null) {
                storage[i] = stk;
                stk = null;
            } else if (storage[i].isStackable() && stk.isStackable() && storage[i].getItem().equals(stk.getItem())) {
                int s1 = storage[i].getItemsNum();
                int s2 = stk.getItemsNum();
                if (s1 + s2 > ItemStack.maxItems) {
                    storage[i].setItemsNum(ItemStack.maxItems);
                    stk.setItemsNum(s1 + s2 - ItemStack.maxItems);
                } else {
                    storage[i].addItemsNum(s2);
                    stk = null;
                }
            }
            i++;
        }
        return stk;
    }

    public void drawInventoryBar(GraphicsContext ctx, double gameWidth, double gameHeight) {
        double x = (gameWidth - barWidth) / 2;
        double y = gameHeight - barHeight - bottomPadding;
        ctx.drawImage(inventoryBarImg, x, y, barWidth, barHeight);

        double startX = x + itemBorderWidth;
        for (int i = 0; i <= 8; i++) {
            if (selected == i) {
                ctx.setLineWidth(3);
                ctx.setStroke(Color.WHITE);
                ctx.strokeRect(startX - 1, y + itemBorderWidth - 1, itemDefaultSize + 2, itemDefaultSize + 2);
            }
            drawStackWithItem(ctx, storage[i], startX, y + itemBorderWidth, itemDefaultSize);
            startX += (itemDefaultSize + itemBorderWidth);
        }
    }

    private void fillRowItems(GraphicsContext ctx, double x, double y, int idOffset) {
        double startX = x + itemBorderWidth;
        for (int i = 0; i <= 8; i++) {
            int id = i + idOffset;
            drawStackWithItem(ctx, storage[id], startX, y + itemBorderWidth, itemDefaultSize);
            startX += (itemDefaultSize + itemBorderWidth);
        }
    }

    @Override
    public void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Game game) {
        double invX = (gameWidth - inventoryWidth) / 2;
        double invY = (gameHeight - inventoryHeight) / 2;
        ctx.drawImage(inventoryImg, invX, invY, inventoryWidth, inventoryHeight);

        fillText(ctx, "Crafting", invX + 172, invY + 24);

        fillRowItems(ctx, invX + 12, invY + 280, 0); // the last row
        fillRowItems(ctx, invX + 12, invY + 164, 9);
        fillRowItems(ctx, invX + 12, invY + 200, 18);
        fillRowItems(ctx, invX + 12, invY + 236, 27);

        double dx = itemDefaultSize + itemBorderWidth;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int id = i * 2 + j;
                drawStackWithItem(ctx, craftingStorage[id], invX + 176 + dx * j, invY + 52 + dx * i, itemDefaultSize);
            }
        }

        if (craftingStorage[4] != null) {
            drawStackWithItem(ctx, craftingStorage[4], invX + 288, invY + 72, itemDefaultSize);
        }

        drawDraggedStack(ctx, mouse, itemDefaultSize);
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

        int craftingId = calculateIdFromRelativePositionForCrafting(x, y);
        if (craftingId == 4) {
            handleResultBlock(craftingStorage, event);
        } else if (craftingId >= 0) {
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

    public int calculateIdFromRelativePosition(double x, double y) {
        if (x < 0 || y < 0 || x > inventoryWidth || y > inventoryHeight) return -2;
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
    public void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Game game) {
        double invX = (gameWidth - inventoryWidth) / 2;
        double invY = (gameHeight - inventoryHeight) / 2;
        handleMousePressedRelativeCoordinates(event, event.getX() - invX, event.getY() - invY, game);

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

    @Override
    public void onClosed(Game game) {
        super.onClosed(game);
        craftingStorage[4] = null; // prevent crafting display being dropped
        clearStorageByDropping(craftingStorage, game);
    }
}
