package net.noahvolson.rpgmod.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.noahvolson.rpgmod.networking.ModMessages;
import net.noahvolson.rpgmod.player.PlayerRpgClassProvider;
import net.noahvolson.rpgmod.rpgclass.RpgClass;

import java.util.function.Supplier;

import static net.noahvolson.rpgmod.rpgclass.RpgClasses.*;

public class AbilityC2SPacket {
    private final int abilityNum;

    public AbilityC2SPacket(int abilityNum) {
        this.abilityNum = abilityNum;
    }

    // Deserialize
    public AbilityC2SPacket(FriendlyByteBuf buf) {
        this.abilityNum = buf.readInt();
    }

    // Serialize
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(abilityNum);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // NOW ACTING ON THE SERVER
            ServerPlayer player = context.getSender();

            if (player != null) {
                player.getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).ifPresent(curClass -> {
                    String rpgClassId = curClass.getRpgClass();
                    RpgClass rpgClass = switch (rpgClassId) {
                        case "MAGE" -> MAGE;
                        case "ROGUE" -> ROGUE;
                        case "WARRIOR" -> WARRIOR;
                        default -> null;
                    };
                    if (rpgClass != null) {
                        switch (abilityNum) {
                            case 1 -> rpgClass.useSkill1(player);
                            case 2 -> rpgClass.useSkill2(player);
                            case 3 -> rpgClass.useSkill3(player);
                            case 4 -> rpgClass.useSkill4(player);
                        }
                    }
                });
            }
        });
    }

}
