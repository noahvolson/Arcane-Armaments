package net.noahvolson.rpgmod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.noahvolson.rpgmod.entity.ModEntityTypes;
import net.noahvolson.rpgmod.entity.spell.*;

import java.util.function.Supplier;

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
            ServerLevel level = player.getLevel();

            Vec3 look = player.getLookAngle();
            double speed = 3D;

            AbstractProjectileAbility ability;
            switch (abilityNum) {
                case 1 -> {
                    /*
                    ability = new FireballSpell(ModEntityTypes.FIREBALL.get(), player, player.level);
                    ability.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
                    player.level.addFreshEntity(ability);
                    */
                    ability = new PoisonDaggerAttack(ModEntityTypes.POISON_DAGGER.get(), player, player.level);
                    ability.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
                    player.level.addFreshEntity(ability);
                }
                case 2 -> {
                    /*
                    ability = new BlinkSpell(ModEntityTypes.BLINK.get(), player, player.level);
                    ability.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
                    player.level.addFreshEntity(ability);
                     */
                    ability = new RuptureDaggerAttack(ModEntityTypes.RUPTURE_DAGGER.get(), player, player.level);
                    ability.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
                    player.level.addFreshEntity(ability);

                }
                case 3 -> {
                    /*
                    ability = new BlizzardSpell(ModEntityTypes.BLIZZARD.get(), player, player.level);
                    ability.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
                    player.level.addFreshEntity(ability);
                    */
                    ability = new ExecuteDaggerAttack(ModEntityTypes.EXECUTE_DAGGER.get(), player, player.level);
                    ability.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
                    player.level.addFreshEntity(ability);
                }
                case 4 -> {
                    ability = new ThunderSpell(ModEntityTypes.THUNDER.get(), player, player.level);
                    ability.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
                    player.level.addFreshEntity(ability);
                }
            }
            if (!player.isCreative()) {
                int spellCost = 1;
                int remainingFood = player.getFoodData().getFoodLevel() - spellCost;
                player.getFoodData().setFoodLevel(Math.max(remainingFood, 0));
                if (remainingFood < 0) {
                    player.setHealth(player.getHealth() + remainingFood);
                }
            }

            player.swing(InteractionHand.OFF_HAND, true);
        });
    }

}
