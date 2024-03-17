package net.noahvolson.arcanearmaments.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.noahvolson.arcanearmaments.player.PlayerRpgClassProvider;
import net.noahvolson.arcanearmaments.player.PlayerUnlockedSkillsProvider;
import net.noahvolson.arcanearmaments.rpgclass.RpgClass;

import java.util.function.Supplier;

import static net.noahvolson.arcanearmaments.rpgclass.RpgClasses.*;

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
                player.getCapability(PlayerUnlockedSkillsProvider.PLAYER_UNLOCKED_SKILLS).ifPresent(unlockedSkills -> {
                    player.getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).ifPresent(curClass -> {
                        String rpgClassId = curClass.getRpgClass();
                        RpgClass rpgClass = switch (rpgClassId) {
                            case "MAGE" -> MAGE;
                            case "ROGUE" -> ROGUE;
                            case "WARRIOR" -> WARRIOR;
                            case "CLERIC" -> CLERIC;
                            default -> null;
                        };
                        if (rpgClass != null) {
                            switch (abilityNum) {
                                case 1 -> { rpgClass.useSkill1(player); }
                                case 2 -> { if (unlockedSkills.contains(rpgClass.getSkill2())) { rpgClass.useSkill2(player); } }
                                case 3 -> { if (unlockedSkills.contains(rpgClass.getSkill3())) { rpgClass.useSkill3(player); } }
                                case 4 -> { if (unlockedSkills.contains(rpgClass.getSkill4())) { rpgClass.useSkill4(player); } }
                            }
                        }
                    });
                });
            }
        });
    }

}
