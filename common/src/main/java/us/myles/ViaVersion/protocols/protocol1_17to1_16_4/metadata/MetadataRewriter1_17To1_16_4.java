package us.myles.ViaVersion.protocols.protocol1_17to1_16_4.metadata;

import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.Entity1_16_2Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_14;
import us.myles.ViaVersion.api.rewriters.MetadataRewriter;
import us.myles.ViaVersion.protocols.protocol1_16_2to1_16_1.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import us.myles.ViaVersion.protocols.protocol1_17to1_16_4.storage.EntityTracker1_17;

import java.util.List;

public class MetadataRewriter1_17To1_16_4 extends MetadataRewriter {

    public MetadataRewriter1_17To1_16_4(Protocol1_17To1_16_4 protocol) {
        super(protocol, EntityTracker1_17.class);
    }

    @Override
    public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
        if (metadata.getMetaType() == MetaType1_14.Slot) {
            InventoryPackets.toClient((Item) metadata.getValue());
        } else if (metadata.getMetaType() == MetaType1_14.BlockID) {
            int data = (int) metadata.getValue();
            metadata.setValue(protocol.getMappingData().getNewBlockStateId(data));
        }

        if (type == null) return;

        if (type.isOrHasParent(Entity1_16_2Types.EntityType.ENTITY)) {
            if (metadata.getId() >= 7) {
                metadata.setId(metadata.getId() + 1); // Ticks frozen added with id 7
            }
        }
    }

    @Override
    protected EntityType getTypeFromId(int type) {
        return Entity1_16_2Types.getTypeFromId(type);
    }
}