package io.github.soundsofthesun.terminal.attachment;

import io.github.soundsofthesun.terminal.Terminal;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;

public class TAttachmentTypes {
    public static void initialize(){}

    public static final AttachmentType<StationAttachedData> STATION_ATTACHMENT_TYPE = AttachmentRegistry.create(
            Identifier.of(Terminal.MOD_ID, "station_data"),
            builder->builder
                    .initializer(()->StationAttachedData.DEFAULT)
                    .persistent(StationAttachedData.CODEC)
                    .syncWith(
                            StationAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

        public static final AttachmentType<TransitAttachedData> TRANSIT_ATTACHMENT_TYPE = AttachmentRegistry.create(
            Identifier.of(Terminal.MOD_ID, "transit_data"),
            builder->builder
                    .initializer(()->TransitAttachedData.DEFAULT)
                    .persistent(TransitAttachedData.CODEC)
                    .syncWith(
                            TransitAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

        public static final AttachmentType<TerminalAttachedData> TERMINAL_ATTACHED_DATA = AttachmentRegistry.create(
            Identifier.of(Terminal.MOD_ID, "terminal_data"),
            builder->builder
                    .initializer(()->TerminalAttachedData.DEFAULT)
                    .persistent(TerminalAttachedData.CODEC)
                    .syncWith(
                            TerminalAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );


        public static final AttachmentType<NameAttachedData> NAME_ATTACHED_DATA = AttachmentRegistry.create(
            Identifier.of(Terminal.MOD_ID, "name_data"),
            builder->builder
                    .initializer(()->NameAttachedData.DEFAULT)
                    .persistent(NameAttachedData.CODEC)
                    .syncWith(
                            NameAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );



}
