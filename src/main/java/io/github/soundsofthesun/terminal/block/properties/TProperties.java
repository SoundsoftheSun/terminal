package io.github.soundsofthesun.terminal.block.properties;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class TProperties {

    public enum LIGHT_STATE implements StringRepresentable {
        GREEN(3, "green"), YELLOW(2, "yellow"), RED(1, "red"), OFF(0, "off"), PENDING(4, "pending");

        private final String id;
        private final int index;

        LIGHT_STATE(int index, String id) {
            this.id = id;
            this.index = index;
        }

        @Override
        public String getSerializedName() {
            return this.id;
        }

        public int getIndex() { return this.index; }

        public static final StringRepresentable.EnumCodec<LIGHT_STATE> CODEC = StringRepresentable.fromEnum(LIGHT_STATE::values);

    }

    public static final EnumProperty<LIGHT_STATE> LIGHT_PROPERTY = EnumProperty.create("light_state", LIGHT_STATE.class);


    public enum ACTIVE_STATE implements StringRepresentable {
        INACTIVE(0, "inactive"), ACTIVE(1, "active");

        private final String id;
        private final int index;

        ACTIVE_STATE(int index, String id) {
            this.id = id;
            this.index = index;
        }

        @Override
        public String getSerializedName() {
            return this.id;
        }

        public int getIndex() { return this.index; }

        public static final StringRepresentable.EnumCodec<ACTIVE_STATE> CODEC = StringRepresentable.fromEnum(ACTIVE_STATE::values);
    }

    public static final EnumProperty<ACTIVE_STATE> ACTIVE_PROPERTY = EnumProperty.create("active_state", ACTIVE_STATE.class);
}
