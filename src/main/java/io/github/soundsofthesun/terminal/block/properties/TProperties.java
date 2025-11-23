package io.github.soundsofthesun.terminal.block.properties;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class TProperties {

    public enum LIGHT_STATE implements StringIdentifiable {
        GREEN(0, "green"), YELLOW(1, "yellow"), RED(2, "red"), OFF(3, "off"), PENDING(4, "pending");

        private final String id;
        private final int index;

        LIGHT_STATE(int index, String id) {
            this.id = id;
            this.index = index;
        }

        @Override
        public String asString() {
            return this.id;
        }

        public static final StringIdentifiable.EnumCodec<LIGHT_STATE> CODEC = StringIdentifiable.createCodec(LIGHT_STATE::values);

    }

    public static final EnumProperty<LIGHT_STATE> LIGHT_PROPERTY = EnumProperty.of("light_state", LIGHT_STATE.class);


    public enum ACTIVE_STATE implements StringIdentifiable {
        INACTIVE(0, "inactive"), ACTIVE(1, "active");

        private final String id;
        private final int index;

        ACTIVE_STATE(int index, String id) {
            this.id = id;
            this.index = index;
        }

        @Override
        public String asString() {
            return this.id;
        }

        public int getIndex() { return this.index; }

        public static final StringIdentifiable.EnumCodec<ACTIVE_STATE> CODEC = StringIdentifiable.createCodec(ACTIVE_STATE::values);
    }

    public static final EnumProperty<ACTIVE_STATE> ACTIVE_PROPERTY = EnumProperty.of("active_state", ACTIVE_STATE.class);
}
