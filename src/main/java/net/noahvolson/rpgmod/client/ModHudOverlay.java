package net.noahvolson.rpgmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
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
                GuiComponent.blit(poseStack,x - 90 + (i * 8), y - 38,0,0,7,7,
                        7,7);
            }

            if (halfHeart) {
                RenderSystem.setShaderTexture(0, HALF_VENOM_HEART);
                GuiComponent.blit(poseStack,x - 90 + (i * 8), y - 38,0,0,7,7,
                        7,7);
            }
        }
    }));
}
