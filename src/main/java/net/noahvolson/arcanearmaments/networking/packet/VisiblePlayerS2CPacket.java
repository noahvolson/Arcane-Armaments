
package net.noahvolson.arcanearmaments.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.noahvolson.arcanearmaments.client.ClientInvisiblePlayersData;

import java.util.function.Supplier;

public class VisiblePlayerS2CPacket {

    private final String uuid;

    public VisiblePlayerS2CPacket(String player) {
        this.uuid = player;
    }

    // Deserialize
    public VisiblePlayerS2CPacket(FriendlyByteBuf buf) {
        this.uuid = buf.readUtf();
    }

    // Serialize
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(uuid);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // NOW ACTING ON THE CLIENT
            ClientInvisiblePlayersData.players.remove(uuid);
        });
    }

}
