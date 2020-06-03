package net.maple3142.craft2d;

import net.maple3142.craft2d.block.BreakableBlock;
import net.maple3142.craft2d.item.Tool;

public class BreakingTimeCalculator {
    public static int hardnessToTime = 1000;

    public static int calculate(Tool tool, BreakableBlock target) { // ms
        if (target == null) return 0;
        double h = target.getHardness();
        if (h < 0) return -1;
        if (h == 0) return 0;
        var mul = tool == null ? 1 : tool.getHardnessMultiplier(target);
        return (int) (h * mul * hardnessToTime);
    }
}
