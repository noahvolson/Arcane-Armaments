package net.noahvolson.rpgmod.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.ModEntityTypes;
import net.noahvolson.rpgmod.entity.client.render.ProjectileSpellRenderer;

@Mod.EventBusSubscriber(modid = RpgMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {

        // mage spells
        EntityRenderers.register(ModEntityTypes.BLINK.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.FIREBALL.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.BLIZZARD.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.THUNDER.get(), ProjectileSpellRenderer::new);

        // rogue spells
        EntityRenderers.register(ModEntityTypes.DAGGER.get(), ProjectileSpellRenderer::new);
    }
}
