package net.noahvolson.rpgmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.noahvolson.rpgmod.block.ModBlocks;
import net.noahvolson.rpgmod.block.entity.ModBlockEntities;
import net.noahvolson.rpgmod.config.OptionsHolder;
import net.noahvolson.rpgmod.screen.ArmoryScreen;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.ModEntityTypes;
import net.noahvolson.rpgmod.entity.client.render.MeatHookRenderer;
import net.noahvolson.rpgmod.item.ModItems;
import net.noahvolson.rpgmod.networking.ModMessages;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.screen.ModMenuTypes;
import net.noahvolson.rpgmod.sound.ModSounds;
import org.slf4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RpgMod.MOD_ID)
public class RpgMod {
    public static final String MOD_ID = "rpgmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public RpgMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModParticles.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // must come first
        event.enqueueWork(ModMessages::register);

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
