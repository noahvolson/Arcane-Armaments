package net.noahvolson.rpgmod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.noahvolson.rpgmod.entity.ModEntityTypes;
import net.noahvolson.rpgmod.entity.custom.FireBoltEntity;

import java.util.function.Supplier;

public class AbilityC2SPacket {

    public AbilityC2SPacket() {

    }

    public AbilityC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // NOW ACTING ON THE SERVER
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            //EntityType.COW.spawn(level, null, null, player.blockPosition(), MobSpawnType.COMMAND, true, false);
            Vec3 look = player.getLookAngle();
            double speed = 3D;

            // TODO fix this: https://moddingtutorials.org/1.16.5/arrows
            //EntityType.FIREBALL.spawn(level,null,player,player.blockPosition().above(), MobSpawnType.COMMAND, true, false).setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);

            FireBoltEntity fireBolt = new FireBoltEntity(ModEntityTypes.FIRE_BOLT.get(), player, player.level);
            fireBolt.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
            player.level.addFreshEntity(fireBolt);

            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - 1);

            player.swing(InteractionHand.OFF_HAND, true);
        });
        return true;
    }

}
