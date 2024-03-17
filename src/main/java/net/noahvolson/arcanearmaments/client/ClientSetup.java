package net.noahvolson.arcanearmaments.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.entity.ModEntityTypes;
import net.noahvolson.arcanearmaments.entity.client.render.ProjectileSpellRenderer;

@Mod.EventBusSubscriber(modid = ArcaneArmaments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {

        // All skills that create an entity must register their types here

        // mage spells
        EntityRenderers.register(ModEntityTypes.BLINK.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.FIREBALL.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.BLIZZARD.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.THUNDER.get(), ProjectileSpellRenderer::new);

        // rogue abilities
        EntityRenderers.register(ModEntityTypes.VENOM_DAGGER.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.RUPTURE_DAGGER.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.EXECUTE_DAGGER.get(), ProjectileSpellRenderer::new);

        // warrior abilities
        EntityRenderers.register(ModEntityTypes.BERSERK.get(), ProjectileSpellRenderer::new);
        EntityRenderers.register(ModEntityTypes.GRAPPLE.get(), ProjectileSpellRenderer::new);

        // cleric abilities
        EntityRenderers.register(ModEntityTypes.SMITING_RAY.get(), ProjectileSpellRenderer::new);
    }
}
