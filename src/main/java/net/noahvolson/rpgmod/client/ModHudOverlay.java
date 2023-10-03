package net.noahvolson.rpgmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.SkillFactory;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.player.PlayerRpgClassProvider;
import net.noahvolson.rpgmod.rpgclass.RpgClass;

import java.util.Objects;

import static net.noahvolson.rpgmod.rpgclass.RpgClasses.*;

public class ModHudOverlay {
    private static final ResourceLocation FULL_VENOM_HEART = new ResourceLocation(RpgMod.MOD_ID,
            "textures/gui/venom_heart_full.png");
    private static final ResourceLocation HALF_VENOM_HEART = new ResourceLocation(RpgMod.MOD_ID,
            "textures/gui/venom_heart_half.png");
    private static final ResourceLocation BERSERK_HEARTS = new ResourceLocation(RpgMod.MOD_ID,
            "textures/gui/berserk_hearts.png");
    private static final ResourceLocation CLASS_HOTBAR = new ResourceLocation(RpgMod.MOD_ID,
            "textures/gui/class_hotbar.png");
    private static final ResourceLocation TRINKET_HOTBAR = new ResourceLocation(RpgMod.MOD_ID,
            "textures/gui/trinket_hotbar.png");


    public static final IGuiOverlay HUD_VENOM = (((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, FULL_VENOM_HEART);

        Player player = gui.getMinecraft().player;
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
            GuiComponent.blit(poseStack,x - 90, y - 46,0,0,81,16, 81,16);
        }
    }));

    public static final IGuiOverlay MOVED_ARMOR = (((gui, poseStack, partialTick, width, height) -> {
        gui.setupOverlayRenderState(true, false);
        Minecraft minecraft = gui.getMinecraft();
        if (minecraft.player != null && !minecraft.player.isCreative() && !minecraft.player.isSpectator()) {
            minecraft.getProfiler().push("armor");

            RenderSystem.enableBlend();
            int left = (width / 2) + 10;
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

    public static final IGuiOverlay HUD_CLASS_HOTBAR = (((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CLASS_HOTBAR);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        GuiComponent.blit(poseStack,x - 200, y - 23,0,0,85,24, 85,24);

        Player player = gui.getMinecraft().player;
        String rpgClassId = ClientRpgClassData.getRpgClass();
        if (player != null && rpgClassId != null) {
            RpgClass rpgClass = switch (rpgClassId) {
                case "MAGE" -> MAGE;
                case "ROGUE" -> ROGUE;
                case "WARRIOR" -> WARRIOR;
                case "CLERIC" -> CLERIC;
                default -> null;
            };
            if (rpgClass != null) {
                RenderSystem.setShaderTexture(0, rpgClass.getSkill1().getIcon());
                GuiComponent.blit(poseStack,x - 195, y - 18,0,0,15,15, 15,15);
                RenderSystem.setShaderTexture(0, rpgClass.getSkill2().getIcon());
                GuiComponent.blit(poseStack,x - 175, y - 18,0,0,15,15, 15,15);
                RenderSystem.setShaderTexture(0, rpgClass.getSkill3().getIcon());
                GuiComponent.blit(poseStack,x - 155, y - 18,0,0,15,15, 15,15);
                RenderSystem.setShaderTexture(0, rpgClass.getSkill4().getIcon());
                GuiComponent.blit(poseStack,x - 135, y - 18,0,0,15,15, 15,15);
            }
        }
    }));

    public static final IGuiOverlay HUD_BERSERK_OFFHAND = (((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;
        Player player = gui.getMinecraft().player;
        String rpgClassId = ClientRpgClassData.getRpgClass();

        if (player != null && rpgClassId != null && rpgClassId.equals(WARRIOR.getId()) && player.hasEffect(ModEffects.COOLDOWN_1.get())) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            int frame = (int) player.level.getGameTime() % 32;
            RenderSystem.setShaderTexture(0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/berserk_offhand/berserk_offhand_" + String.format("%02d", frame) + ".png"));
            GuiComponent.blit(poseStack,x - 121, y - 23,0,0,24,24, 24,24);
        }
    }));

    public static final IGuiOverlay HUD_TRINKET_HOTBAR = (((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, TRINKET_HOTBAR);

        GuiComponent.blit(poseStack,x + 97, y - 23,0,0,64,24, 64,24);

    }));

    public static final IGuiOverlay HUD_COOLDOWNS = (((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        Player player = gui.getMinecraft().player;
        String rpgClassId = ClientRpgClassData.getRpgClass();

        if (player != null && rpgClassId != null) {
            RpgClass rpgClass = switch (rpgClassId) {
                case "MAGE" -> MAGE;
                case "ROGUE" -> ROGUE;
                case "WARRIOR" -> WARRIOR;
                case "CLERIC" -> CLERIC;
                default -> null;
            };
            if (rpgClass != null) {

                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                Tesselator tesselator1 = Tesselator.getInstance();
                BufferBuilder bufferbuilder1 = tesselator1.getBuilder();

                drawCooldown(player, ModEffects.COOLDOWN_1.get(), rpgClass.getSkill1().getCooldown(), bufferbuilder1, x, y, 0);
                drawCooldown(player, ModEffects.COOLDOWN_2.get(), rpgClass.getSkill2().getCooldown(), bufferbuilder1, x, y, 1);
                drawCooldown(player, ModEffects.COOLDOWN_3.get(), rpgClass.getSkill3().getCooldown(), bufferbuilder1, x, y, 2);
                drawCooldown(player, ModEffects.COOLDOWN_4.get(), rpgClass.getSkill4().getCooldown(), bufferbuilder1, x, y, 3);
                drawCooldown(player, ModEffects.COOLDOWN_6.get(), rpgClass.getSkill1().getTurnoverCooldown(), bufferbuilder1, x, y, 4);
            }
        }
    }));

    private static void drawCooldown(Player player, MobEffect effect, int cooldown, BufferBuilder bufferbuilder1, int x, int y, int index) {
        if (player.hasEffect(effect)) {
            if (cooldown > 0) {
                int remainingTicks = Objects.requireNonNull(player.getEffect(effect)).getDuration();
                float f = (float) remainingTicks / cooldown;
                fillRect(bufferbuilder1, x - (196 - (index * 20)), y - 19 + Mth.floor(16.0F * (1.0F - f)), 16, Mth.ceil(16.0F * f), 255, 255, 255, 127);
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }
        }
    }

    private static void fillRect(BufferBuilder p_115153_, int p_115154_, int p_115155_, int p_115156_, int p_115157_, int p_115158_, int p_115159_, int p_115160_, int p_115161_) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        p_115153_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        p_115153_.vertex((double)(p_115154_ + 0), (double)(p_115155_ + 0), 0.0D).color(p_115158_, p_115159_, p_115160_, p_115161_).endVertex();
        p_115153_.vertex((double)(p_115154_ + 0), (double)(p_115155_ + p_115157_), 0.0D).color(p_115158_, p_115159_, p_115160_, p_115161_).endVertex();
        p_115153_.vertex((double)(p_115154_ + p_115156_), (double)(p_115155_ + p_115157_), 0.0D).color(p_115158_, p_115159_, p_115160_, p_115161_).endVertex();
        p_115153_.vertex((double)(p_115154_ + p_115156_), (double)(p_115155_ + 0), 0.0D).color(p_115158_, p_115159_, p_115160_, p_115161_).endVertex();
        BufferUploader.drawWithShader(p_115153_.end());
    }

}
