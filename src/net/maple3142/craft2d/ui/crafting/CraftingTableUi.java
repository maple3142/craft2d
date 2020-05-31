package net.maple3142.craft2d.ui.crafting;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.Player;
import net.maple3142.craft2d.ReflectionHelper;
import net.maple3142.craft2d.UiOpenable;
import net.maple3142.craft2d.crafting.CraftingInput;
import net.maple3142.craft2d.crafting.RecipeRegistry;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.ui.BlockUi;
import net.maple3142.craft2d.ui.storage.PlayerInventory;

public class CraftingTableUi extends BlockUi implements UiOpenable {

    private static final double width = 352;
    private static final double height = 332;
    private static final int itemBorderWidth = 4;
    private static final int itemDefaultSize = 32;
    public static Image img = new Image(PlayerInventory.class.getResource("/ui/crafting_table.png").toString());

    public CraftingTableUi() {
        super(10);
    }

    @Override
    public void onOpened(Player player) {
    }

    @Override
    public void onClosed(Player player) {
        storage = new ItemStack[9];
    }

    @Override
    public void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Player player) {
        double ctX = (gameWidth - width) / 2;
        double ctY = (gameHeight - height) / 2;
        ctx.drawImage(img, ctX, ctY, width, height);

        double dx = itemDefaultSize + itemBorderWidth;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int id = i * 3 + j;
                if (storage[id] != null) {
                    var item = storage[id].getItem();
                    drawImagePercentageCenter(ctx, item.getImage(), ctX + 60 + dx * j, ctY + 34 + dx * i, itemDefaultSize, itemDefaultSize, 0.75);
                    int num = storage[id].getItemsNum();
                    if (num > 1) {
                        ctx.setTextAlign(TextAlignment.RIGHT);
                        ctx.setTextBaseline(VPos.BOTTOM);
                        ctx.setFill(Color.WHITE);
                        ctx.fillText(String.valueOf(num), ctX + 60 + dx * j + itemDefaultSize, ctY + 34 + dx * i + itemDefaultSize);
                    }
                }
            }
        }

        if (storage[9] != null) {
            var item = storage[9].getItem();
            drawImagePercentageCenter(ctx, item.getImage(), ctX + 240, ctY + 62, 48, 48, 0.75); // result block is bigger
            int num = storage[9].getItemsNum();
            if (num > 1) {
                ctx.setTextAlign(TextAlignment.RIGHT);
                ctx.setTextBaseline(VPos.BOTTOM);
                ctx.setFill(Color.WHITE);
                ctx.fillText(String.valueOf(num), ctX + 240 + 48, ctY + 62 + 48);
            }
        }


        {
            fillRowItems(player.inventory.storage, ctx, ctX + 12, ctY + 280, 0); // the last row
            fillRowItems(player.inventory.storage, ctx, ctX + 12, ctY + 164, 9);
            fillRowItems(player.inventory.storage, ctx, ctX + 12, ctY + 200, 18);
            fillRowItems(player.inventory.storage, ctx, ctX + 12, ctY + 236, 27);
        }

        drawDraggedStack(ctx, mouse, itemDefaultSize);
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
    public void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Player player) {
        double ctX = (gameWidth - width) / 2;
        double ctY = (gameHeight - height) / 2;
        handleMousePressedRelativeCoordinates(event, event.getX() - ctX, event.getY() - ctY, player);

        var arr = new Item[9];
        for (int i = 0; i < 9; i++) {
            if (storage[i] != null) {
                arr[i] = storage[i].getItem();
            }
        }
        var recipe = RecipeRegistry.getInstance().findMatchedRecipe(new CraftingInput(arr));
        if (recipe == null) {
            storage[9] = null;
        } else {
            storage[9] = new ItemStack(ReflectionHelper.constructFromEmptyConstructor(recipe.getResultClass()), recipe.getResultNum());
        }
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

    @Override
    public void handleMousePressedRelativeCoordinates(MouseEvent event, double x, double y, Player player) {
        int id = calculateIdFromRelativePosition(x, y);
        if (id == 9) {
            handleResultBlock(storage, event); // special handling for result block
        } else if (id != -1) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(storage, id, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(storage, id, draggedStack);
            }
        }

        int invId = player.inventory.calculateIdFromRelativePosition(x, y); // because crafting table and inventory have same size
        if (invId != -1) {
            if (event.isPrimaryButtonDown()) {
                draggedStack = putAllItems(player.inventory.storage, invId, draggedStack);
            } else if (event.isSecondaryButtonDown()) {
                draggedStack = putOneItem(player.inventory.storage, invId, draggedStack);
            }
        }
    }

    @Override
    protected int calculateIdFromRelativePosition(double x, double y) {
        int row = -1, col = -1;
        if (60 <= x && x <= 164 && 34 <= y && y <= 138) {
            col = (int) ((x - 64) / 35); // 35=(164-60+1)/3
            row = (int) ((y - 35) / 35); // 35=(138-34+1)/3
        } else if (240 <= x && x <= 288 && 62 <= y && y <= 110) {
            return 9;
        }
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            return row * 3 + col;
        }
        return -1;
    }
}
