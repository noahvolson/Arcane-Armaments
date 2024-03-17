package net.noahvolson.arcanearmaments.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.networking.packet.AbilityC2SPacket;
import net.noahvolson.arcanearmaments.networking.packet.RpgClassSyncS2CPacket;
import net.noahvolson.arcanearmaments.networking.packet.UnlockedSkillsSyncS2CPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ArcaneArmaments.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")    // used for package compatibility
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(AbilityC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AbilityC2SPacket::new)
                .encoder(AbilityC2SPacket::toBytes)
                .consumerMainThread(AbilityC2SPacket::handle)
                .add();

        net.messageBuilder(RpgClassSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RpgClassSyncS2CPacket::new)
                .encoder(RpgClassSyncS2CPacket::toBytes)
                .consumerMainThread(RpgClassSyncS2CPacket::handle)
                .add();

        net.messageBuilder(UnlockedSkillsSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UnlockedSkillsSyncS2CPacket::new)
                .encoder(UnlockedSkillsSyncS2CPacket::toBytes)
                .consumerMainThread(UnlockedSkillsSyncS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
