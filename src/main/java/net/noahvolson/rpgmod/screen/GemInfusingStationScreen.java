package net.noahvolson.rpgmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.noahvolson.rpgmod.RpgMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GemInfusingStationScreen extends AbstractContainerScreen<GemInfusingStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RpgMod.MOD_ID,"textures/gui/gem_infusing_station_gui.png");

    private final int[] buttonDownCounter = {0,0,0,0};

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
        this.addWidget(new Button(x + 76, y + 8, 92, 17, Component.literal(""), (button) -> onButtonClicked(button, pPoseStack, 0)));
        this.addWidget(new Button(x + 76, y + 25, 92, 17, Component.literal(""), (button) -> onButtonClicked(button, pPoseStack, 1)));
        this.addWidget(new Button(x + 76, y + 42, 92, 17, Component.literal(""), (button) -> onButtonClicked(button, pPoseStack, 2)));
        this.addWidget(new Button(x + 76, y + 59, 92, 17, Component.literal(""), (button) -> onButtonClicked(button, pPoseStack, 3)));

        if (buttonDownCounter[0] > 0) {
            blit(pPoseStack, x + 76, y + 8, 0, 202, 91, 16);
            buttonDownCounter[0]--;
        }
        if (buttonDownCounter[1] > 0) {
            blit(pPoseStack, x + 76, y + 25, 0, 202, 91, 16);
            buttonDownCounter[1]--;
        }
        if (buttonDownCounter[2] > 0) {
            blit(pPoseStack, x + 76, y + 42, 0, 202, 91, 16);
            buttonDownCounter[2]--;
        }
        if (buttonDownCounter[3] > 0) {
            blit(pPoseStack, x + 76, y + 59, 0, 202, 91, 16);
            buttonDownCounter[3]--;
        }

    }
    private void onButtonClicked(Button button, PoseStack pPoseStack, int index) {
        buttonDownCounter[index] = 20;
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