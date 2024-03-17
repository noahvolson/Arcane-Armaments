package net.noahvolson.arcanearmaments.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.noahvolson.arcanearmaments.client.ClientRpgClassData;

import java.util.function.Supplier;

public class RpgClassSyncS2CPacket {

    private final String rpgClass;

    public RpgClassSyncS2CPacket(String rpgClass) {
        this.rpgClass = rpgClass;
    }

    // Deserialize
    public RpgClassSyncS2CPacket(FriendlyByteBuf buf) {
        this.rpgClass = buf.readUtf();
    }

    // Serialize
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(rpgClass);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // NOW ACTING ON THE CLIENT
            ClientRpgClassData.set(rpgClass);
        });
    }

}
