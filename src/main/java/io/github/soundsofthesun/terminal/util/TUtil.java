package io.github.soundsofthesun.terminal.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.RailShape;

public class TUtil {
    public static RailShape getRailShape(Direction dir1, Direction dir2) {
        for (RailShape shape : RailShape.values()) {
            if (shape.getName().contains(dir1.getName()) && shape.getName().contains(dir2.getName())) {
                return shape;
            }
        }
        return RailShape.ASCENDING_EAST;
    }
}
