package net.noahvolson.rpgmod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.noahvolson.rpgmod.client.ClientRpgClassData;
import net.noahvolson.rpgmod.client.ClientUnlockedSkillsData;

import java.util.function.Supplier;

public class UnlockedSkillsSyncS2CPacket {

    private final String unlockedSkills;

    public UnlockedSkillsSyncS2CPacket(String unlockedSkills) {
        this.unlockedSkills = unlockedSkills;
    }

    // Deserialize
    public UnlockedSkillsSyncS2CPacket(FriendlyByteBuf buf) {
        this.unlockedSkills = buf.readUtf();
    }

    // Serialize
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(unlockedSkills);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // NOW ACTING ON THE CLIENT
            ClientUnlockedSkillsData.set(unlockedSkills);
        });
    }

}
