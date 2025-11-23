package io.github.soundsofthesun.terminal.util;

import net.minecraft.block.enums.RailShape;
import net.minecraft.util.math.Direction;

public class TUtil {
    public static RailShape getRailShape(Direction dir1, Direction dir2) {
        for (RailShape shape : RailShape.values()) {
            if (shape.getName().contains(dir1.getId()) && shape.getName().contains(dir2.getId())) {
                return shape;
            }
        }
        return RailShape.ASCENDING_EAST;
    }
}
