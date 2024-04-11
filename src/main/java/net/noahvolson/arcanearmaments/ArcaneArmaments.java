package net.noahvolson.arcanearmaments;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.noahvolson.arcanearmaments.block.ModBlocks;
import net.noahvolson.arcanearmaments.block.entity.ModBlockEntities;
import net.noahvolson.arcanearmaments.config.OptionsHolder;
import net.noahvolson.arcanearmaments.item.ModCreativeModeTab;
import net.noahvolson.arcanearmaments.screen.ArmoryScreen;
import net.noahvolson.arcanearmaments.effect.ModEffects;
import net.noahvolson.arcanearmaments.entity.ModEntityTypes;
import net.noahvolson.arcanearmaments.entity.client.render.MeatHookRenderer;
import net.noahvolson.arcanearmaments.item.ModItems;
import net.noahvolson.arcanearmaments.networking.ModMessages;
import net.noahvolson.arcanearmaments.particle.ModParticles;
import net.noahvolson.arcanearmaments.screen.ModMenuTypes;
import net.noahvolson.arcanearmaments.sound.ModSounds;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArcaneArmaments.MOD_ID)
public class ArcaneArmaments {
    public static final String MOD_ID = "arcanearmaments";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ArcaneArmaments() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModParticles.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // must come first
        event.enqueueWork(ModMessages::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTab() == ModCreativeModeTab.ARCANE_ARMAMENTS_TAB.get()) {
            event.accept(ModItems.MAGE_STAFF.get());
            event.accept(ModItems.ROGUE_DAGGER.get());
            event.accept(ModItems.WARRIOR_AXE.get());
            event.accept(ModItems.CLERIC_SCEPTRE.get());
            event.accept(ModBlocks.ARMORY.get());
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.GRAPPLE.get(), MeatHookRenderer::new);
            MenuScreens.register(ModMenuTypes.ARMORY_MENU.get(), ArmoryScreen::new);
        }
    }
}
