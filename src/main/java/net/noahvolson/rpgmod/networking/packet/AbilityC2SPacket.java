package net.noahvolson.rpgmod.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.noahvolson.rpgmod.entity.rpgclass.AbstractRpgClass;
import net.noahvolson.rpgmod.entity.rpgclass.MageClass;
import net.noahvolson.rpgmod.entity.rpgclass.RogueClass;
import net.noahvolson.rpgmod.entity.rpgclass.WarriorClass;
import net.noahvolson.rpgmod.player.PlayerRpgClass;
import net.noahvolson.rpgmod.player.PlayerRpgClassProvider;

import java.util.function.Supplier;

public class AbilityC2SPacket {
    private final int abilityNum;
    private static int classIndex = 0;
    AbstractRpgClass rpgClass;

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
            AbstractRpgClass[] classes = {new WarriorClass(player), new MageClass(player), new RogueClass(player)};
            rpgClass = classes[classIndex];

            switch (abilityNum) {
                case 1 -> rpgClass.useSlot1();
                case 2 -> rpgClass.useSlot2();
                case 3 -> rpgClass.useSlot3();
                case 4 -> rpgClass.useSlot4();
                case 5 -> {
                    classIndex++;
                    classIndex = classIndex > classes.length - 1 ? 0 : classIndex;

                    rpgClass = classes[classIndex];
                    player.getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).ifPresent(curClass -> {
                        curClass.setRpgClass(rpgClass.getClassType());
                        player.sendSystemMessage(Component.literal("Swapping to " + curClass.getRpgClass().name()).withStyle(ChatFormatting.AQUA));
                    });

                    player.removeAllEffects();
                }
            };
        });
    }

}
