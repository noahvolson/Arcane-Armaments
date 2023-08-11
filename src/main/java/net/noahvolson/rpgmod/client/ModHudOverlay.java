package net.noahvolson.rpgmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.ForgeConfig;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.effect.ModEffects;

public class ModHudOverlay {
    private static final ResourceLocation FULL_VENOM_HEART = new ResourceLocation(RpgMod.MOD_ID,
            "textures/gui/venom_heart_full.png");
    private static final ResourceLocation HALF_VENOM_HEART = new ResourceLocation(RpgMod.MOD_ID,
            "textures/gui/venom_heart_half.png");
    private static final ResourceLocation BERSERK_HEARTS = new ResourceLocation(RpgMod.MOD_ID,
            "textures/gui/berserk_hearts.png");

    public static final IGuiOverlay HUD_VENOM = (((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, FULL_VENOM_HEART);

        Player player = (Player) gui.getMinecraft().getCameraEntity();
        if (player != null && player.hasEffect(ModEffects.VENOM.get()) && !player.isCreative()) {
            int health = Mth.ceil(player.getHealth());
            int fullHearts = health / 2;
            boolean halfHeart = health % 2 == 1;

            int i;
            for(i = 0; i < fullHearts; i++) {
                GuiComponent.blit(poseStack,x - 90 + (i * 8), y - 38,0,0,7,7, 7,7);
            }

            if (halfHeart) {
                RenderSystem.setShaderTexture(0, HALF_VENOM_HEART);
                GuiComponent.blit(poseStack,x - 90 + (i * 8), y - 38,0,0,7,7, 7,7);
            }
        }
    }));

    public static final IGuiOverlay HUD_BERSERK = (((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, BERSERK_HEARTS);

        Player player = (Player) gui.getMinecraft().getCameraEntity();
        if (player != null && player.hasEffect(ModEffects.BERSERK.get()) && !player.isCreative()) {
            GuiComponent.blit(poseStack,x - 90, y - 46,0,0,81,16,
                    81,16);
        }
    }));

    public static final IGuiOverlay MOVED_ARMOR = (((gui, poseStack, partialTick, width, height) -> {
        gui.setupOverlayRenderState(true, false);
        Minecraft minecraft = gui.getMinecraft();
        if (!minecraft.player.isCreative()) {
            minecraft.getProfiler().push("armor");

            RenderSystem.enableBlend();
            int left = width / 2 + 10;
            int top = height - 49;

            int level = minecraft.player.getArmorValue();
            for (int i = 1; level > 0 && i < 20; i += 2)
            {
                if (i < level)
                {
                    GuiComponent.blit(poseStack, left, top, 34, 9, 9, 9, 256, 256);
                }
                else if (i == level)
                {
                    GuiComponent.blit(poseStack, left, top, 25, 9, 9, 9, 256, 256);
                }
                else if (i > level)
                {
                    GuiComponent.blit(poseStack, left, top, 16, 9, 9, 9,256, 256);
                }
                left += 8;
            }

            RenderSystem.disableBlend();
            minecraft.getProfiler().pop();
        }
    }));

    // For cooldown animation:
    //         LocalPlayer localplayer = Minecraft.getInstance().player;
    //         float f = localplayer == null ? 0.0F : localplayer.getCooldowns().getCooldownPercent(p_115176_.getItem(), Minecraft.getInstance().getFrameTime());
    //         if (f > 0.0F) {
    //            RenderSystem.disableDepthTest();
    //            RenderSystem.disableTexture();
    //            RenderSystem.enableBlend();
    //            RenderSystem.defaultBlendFunc();
    //            Tesselator tesselator1 = Tesselator.getInstance();
    //            BufferBuilder bufferbuilder1 = tesselator1.getBuilder();
    //            this.fillRect(bufferbuilder1, p_115177_, p_115178_ + Mth.floor(16.0F * (1.0F - f)), 16, Mth.ceil(16.0F * f), 255, 255, 255, 127);
    //            RenderSystem.enableTexture();
    //            RenderSystem.enableDepthTest();
    //         }
}
