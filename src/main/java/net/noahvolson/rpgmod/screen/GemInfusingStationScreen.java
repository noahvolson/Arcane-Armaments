package net.noahvolson.rpgmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.noahvolson.rpgmod.RpgMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GemInfusingStationScreen extends AbstractContainerScreen<GemInfusingStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RpgMod.MOD_ID,"textures/gui/gem_infusing_station_gui.png");

    public GemInfusingStationScreen(GemInfusingStationMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        renderSkillInfo(pPoseStack, x, y, pMouseX, pMouseY);

        if (this.menu.renderPressedButton(0)) {
            blit(pPoseStack, x + 76, y + 8, 0, 202, 91, 16);
        }
        if (this.menu.renderPressedButton(1)) {
            blit(pPoseStack, x + 76, y + 25, 0, 202, 91, 16);
        }
        if (this.menu.renderPressedButton(2)) {
            blit(pPoseStack, x + 76, y + 42, 0, 202, 91, 16);
        }
        if (this.menu.renderPressedButton(3)) {
            blit(pPoseStack, x + 76, y + 59, 0, 202, 91, 16);
        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_98760_) {
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null) {
            if (x >= 228 && y < 319) {
                if (y >= 60 && y <= 76 && this.menu.clickMenuButton(this.minecraft.player, 0)) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
                    return true;
                }
                else if (y >= 77 && y <= 93 && this.menu.clickMenuButton(this.minecraft.player, 1)) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 1);
                    return true;
                }
                else if (y >= 94 && y <= 110 && this.menu.clickMenuButton(this.minecraft.player, 2)) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 2);
                    return true;
                }
                else if (y >= 111 && y <= 127 && this.menu.clickMenuButton(this.minecraft.player, 3)) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 3);
                    return true;
                }
            }
        }

        return super.mouseClicked(x, y, p_98760_);
    }

    private void renderSkillInfo(PoseStack pPoseStack, int x, int y, int pMouseX, int pMouseY) {
        if (pMouseX >= 228 && pMouseX < 319) {
            if (pMouseY >= 60 && pMouseY <= 76) {
                blit(pPoseStack, x + 76, y + 8, 0, 185, 91, 16);
            }
            else if (pMouseY >= 77 && pMouseY <= 93) {
                blit(pPoseStack, x + 76, y + 25, 0, 185, 91, 16);
            }
            else if (pMouseY >= 94 && pMouseY <= 110) {
                blit(pPoseStack, x + 76, y + 42, 0, 185, 91, 16);
            }
            else if (pMouseY >= 111 && pMouseY <= 127) {
                blit(pPoseStack, x + 76, y + 59, 0, 185, 91, 16);
            }
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}