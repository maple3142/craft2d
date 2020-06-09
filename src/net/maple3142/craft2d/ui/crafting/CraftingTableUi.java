package net.maple3142.craft2d.ui.crafting;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.ReflectionHelper;
import net.maple3142.craft2d.crafting.CraftingInput;
import net.maple3142.craft2d.crafting.RecipeRegistry;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.ui.BlockUi;
import net.maple3142.craft2d.ui.UiOpenable;
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
    public void onClosed(Game game) {
        super.onClosed(game);
        storage = new ItemStack[9];
    }

    @Override
    public void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Game game) {
        var player = game.player;
        double ctX = (gameWidth - width) / 2;
        double ctY = (gameHeight - height) / 2;
        ctx.drawImage(img, ctX, ctY, width, height);

        double dx = itemDefaultSize + itemBorderWidth;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int id = i * 3 + j;
                drawStackWithItem(ctx, storage[id], ctX + 60 + dx * j, ctY + 34 + dx * i, itemDefaultSize);
            }
        }

        drawStackWithItem(ctx, storage[9], ctX + 240, ctY + 62, 48); // result block is bigger

        {
            fillRowItems(player.inventory.storage, ctx, ctX + 12, ctY + 280, 0, itemDefaultSize, itemBorderWidth); // the last row
            fillRowItems(player.inventory.storage, ctx, ctX + 12, ctY + 164, 9, itemDefaultSize, itemBorderWidth);
            fillRowItems(player.inventory.storage, ctx, ctX + 12, ctY + 200, 18, itemDefaultSize, itemBorderWidth);
            fillRowItems(player.inventory.storage, ctx, ctX + 12, ctY + 236, 27, itemDefaultSize, itemBorderWidth);
        }

        drawDraggedStack(ctx, mouse, itemDefaultSize);
    }


    @Override
    public void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Game game) {
        double ctX = (gameWidth - width) / 2;
        double ctY = (gameHeight - height) / 2;
        handleMousePressedRelativeCoordinates(event, event.getX() - ctX, event.getY() - ctY, game);

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

    @Override
    public void handleMousePressedRelativeCoordinates(MouseEvent event, double x, double y, Game game) {
        int id = calculateIdFromRelativePosition(x, y);
        if (id == -2) {
            dropDraggedStack(game);
            return;
        }
        if (id == 9) {
            handleResultBlock(storage, event); // special handling for result block
        } else if (id >= 0) {
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
