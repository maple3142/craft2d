package net.maple3142.craft2d.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.item.Burnable;
import net.maple3142.craft2d.item.Fuel;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.FurnaceBlock;
import net.maple3142.craft2d.ui.crafting.FurnaceUi;

public class Furnace implements BreakableBlock, Interactable, StoneLike, Loopable {

    public static Image imgOff = new Image(Furnace.class.getResource("/block/furnace_off.png").toString());

    public static Image imgOn = new Image(Furnace.class.getResource("/block/furnace_on.png").toString());

    public static int energyUnitToSeconds = 10; // 1 E.U. = 10 s

    public ItemStack[] storage = new ItemStack[3]; // 0=to be burned,1=fuel,2=result

    @Override
    public Image getImage() {
        return energyTime > 0 ? imgOn : imgOff;
    }

    @Override
    public void draw(GraphicsContext ctx, int x, int y, int w, int h) {
        ctx.drawImage(getImage(), x, y, w, h);
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemStack getDroppedItem(Tool brokeBy) {
        return new ItemStack(new FurnaceBlock());
    }

    @Override
    public void onInteracted(Game game) {
        game.openUi(new FurnaceUi(this));
    }

    public double maxEnergyTime; // determined by fuel

    public double energyTime = 0; // how long can fire last

    public double waitTime;

    public double currentTime;

    public void tryConsumeFuel() {
        if (storage[0] != null && storage[1] != null) {
            if (energyTime == 0) {
                if (storage[0].getItem() instanceof Burnable && storage[1].getItem() instanceof Fuel && ((Burnable) storage[0].getItem()).isTargetCompatible(storage[2])) {
                    // starting is triggered when it is a valid burning pair
                    var fuel = (Fuel) storage[1].getItem();
                    storage[1].removeItemsNum(1);
                    if (storage[1].getItemsNum() == 0) {
                        storage[1] = null;
                    }
                    maxEnergyTime = fuel.getEnergyUnit() * Furnace.energyUnitToSeconds + 0.1;
                    energyTime = maxEnergyTime;
                }
            }
        }
    }

    @Override
    public void loop(int dt) {
        double lastRemainingTime = 0;
        if (waitTime > 0 && currentTime >= waitTime) {
            // asserts is guaranteed by start trigger
            assert (storage[0] != null);
            var burned = (Burnable) storage[0].getItem();
            assert (burned.isTargetCompatible(storage[2]));
            var result = burned.getResultItemStack();
            if (storage[2] == null) {
                storage[2] = result;
            } else {
                storage[2].addItemsNum(result.getItemsNum());
            }
            storage[0].removeItemsNum(1);
            if (storage[0].getItemsNum() == 0) {
                storage[0] = null;
            }
            waitTime = 0;
            currentTime = 0;
            lastRemainingTime = currentTime - waitTime;
        }
        if (energyTime > 0) energyTime -= dt / 1000.0;
        if (energyTime < 0) {
            energyTime = 0;
            tryConsumeFuel();
            if (energyTime <= 0) {
                waitTime = 0;
                currentTime = 0;
            }
        }
        if (waitTime > 0) {
            currentTime += dt / 1000.0;
        }
        if (energyTime > 0) {
            if (storage[0] != null
                    && storage[0].getItem() instanceof Burnable
                    && waitTime == 0
                    && ((Burnable) storage[0].getItem()).isTargetCompatible(storage[2])) {
                // start trigger
                waitTime = energyUnitToSeconds;
                currentTime = lastRemainingTime;
            }
        }
        if (waitTime > 0 && storage[0] == null) {
            // aborted
            waitTime = 0;
            currentTime = 0;
        }
    }
}
