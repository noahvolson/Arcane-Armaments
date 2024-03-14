package net.noahvolson.rpgmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.noahvolson.rpgmod.RpgMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.noahvolson.rpgmod.client.ClientUnlockedSkillsData;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.rpgclass.RpgClass;

import static net.noahvolson.rpgmod.screen.ArmoryMenu.TE_INVENTORY_FIRST_SLOT_INDEX;

public class ArmoryScreen extends AbstractContainerScreen<ArmoryMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RpgMod.MOD_ID,"textures/gui/armory.png");

    private int forgeFrame = -1;
    private int failedFrame = -1;
    private int slowdownCounter = 0;
    private int lastAttemptCounter = 0;
    private boolean resetComplete = false;

    public ArmoryScreen(ArmoryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    private ResourceLocation getResourceLocation(Item item) {
        if (item != null) {
            if (item.getDescriptionId().matches("^item\\.minecraft\\..*")) {
                return new ResourceLocation("textures/item/" + item.getDescriptionId().replace("item.minecraft.", "") + ".png");
            }
            else if (item.getDescriptionId().matches("^block\\.minecraft\\..*")) {
                return new ResourceLocation("textures/block/" + item.getDescriptionId().replace("block.minecraft.", "") + ".png");
            } else {
                return new ResourceLocation("textures/item/" + Items.GUNPOWDER.getDescriptionId().replace("block.minecraft.", "") + ".png");
            }
        } else {
            return new ResourceLocation("textures/item/" + Items.GUNPOWDER.getDescriptionId().replace("block.minecraft.", "") + ".png");
        }
    }

    public static void renderSkillIcon(PoseStack pPoseStack, RpgClass rpgClass, SkillType skillType, int x, int y, int xOffset, int yOffset) {
        RenderSystem.setShaderTexture(0, rpgClass.getBackground());
        GuiComponent.blit(pPoseStack,x + xOffset, y + yOffset,0,0,15,15, 15,15);

        if (skillType == rpgClass.getSkill1() || ClientUnlockedSkillsData.contains(skillType)) {
            RenderSystem.setShaderTexture(0, skillType.getIcon());
            GuiComponent.blit(pPoseStack,x + xOffset, y + yOffset,0,0,15,15, 15,15);
        } else {
            RenderSystem.setShaderTexture(0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/locked.png"));
            GuiComponent.blit(pPoseStack,x + xOffset, y + yOffset,0,0,15,15, 15,15);
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight + 2);

        RpgClass rpgClass = this.menu.getRpgClass();

        if (rpgClass != null) {
            renderSkillInfo(pPoseStack, x, y, pMouseX, pMouseY);

            boolean pressed0 = true;
            boolean pressed1 = ClientUnlockedSkillsData.contains(rpgClass.getSkill2());
            boolean pressed2 = ClientUnlockedSkillsData.contains(rpgClass.getSkill3());
            boolean pressed3 = ClientUnlockedSkillsData.contains(rpgClass.getSkill4());

            if (pressed0) {
                blit(pPoseStack, x + 76, y + 8, 0, 202, 75, 16);
            }
            if (pressed1) {
                blit(pPoseStack, x + 76, y + 25, 0, 202, 75, 16);
            }
            if (pressed2) {
                blit(pPoseStack, x + 76, y + 42, 0, 202, 75, 16);
            }
            if (pressed3) {
                blit(pPoseStack, x + 76, y + 59, 0, 202, 75, 16);
            }

            // Render Skill icons
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            renderSkillIcon(pPoseStack, rpgClass, rpgClass.getSkill1(), x, y, 60, 9);
            renderSkillIcon(pPoseStack, rpgClass, rpgClass.getSkill2(), x, y, 60, 26);
            renderSkillIcon(pPoseStack, rpgClass, rpgClass.getSkill3(), x, y, 60, 43);
            renderSkillIcon(pPoseStack, rpgClass, rpgClass.getSkill4(), x, y, 60, 60);

            // Render Skill craft cost items
            // NOTE: Probably shouldn't be parsing the descriptionId to find the resource location. Looking for a better solution...

            Item skill2Cost = rpgClass.getSkill2().getCraftCost(); // Skill 1 comes preloaded on the weapon, so we can skip it here
            Item skill3Cost = rpgClass.getSkill3().getCraftCost();
            Item skill4Cost = rpgClass.getSkill4().getCraftCost();

            RenderSystem.getShaderColor();
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.7f);
            if (skill2Cost != null) {
                RenderSystem.setShaderTexture(0, getResourceLocation(skill2Cost));
                GuiComponent.blit(pPoseStack,x + 153, y + 26,0,0,16,16, 16,16);
            }
            if (skill3Cost != null) {
                RenderSystem.setShaderTexture(0, getResourceLocation(skill3Cost));
                GuiComponent.blit(pPoseStack,x + 153, y + 43,0,0,16,16, 16,16);
            }
            if (skill3Cost != null) {
                RenderSystem.setShaderTexture(0, getResourceLocation(skill4Cost));
                GuiComponent.blit(pPoseStack,x + 153, y + 60,0,0,16,16, 16,16);
            }
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1f);
            RenderSystem.disableBlend();


            this.font.draw(pPoseStack, I18n.get(rpgClass.getSkill1().getLabel()), x + 80, y + 12, pressed0 ? 10522994 : 4537905);
            this.font.draw(pPoseStack, I18n.get(rpgClass.getSkill2().getLabel()), x + 80, y + 29, pressed1 ? 10522994 : 4537905);
            this.font.draw(pPoseStack, I18n.get(rpgClass.getSkill3().getLabel()), x + 80, y + 46, pressed2 ? 10522994 : 4537905);
            this.font.draw(pPoseStack, I18n.get(rpgClass.getSkill4().getLabel()), x + 80, y + 63, pressed3 ? 10522994 : 4537905);

            if (pMouseX >= x + 76 && pMouseX < x + 151) {
                int highlightColor = 16645499; //pressed 10522994
                if (pMouseY >= y + 8 && pMouseY <= y + 24) {
                    renderSkillDescription(pPoseStack, x, y, rpgClass.getSkill1());
                    this.font.draw(pPoseStack, I18n.get(rpgClass.getSkill1().getLabel()), x + 80, y + 12, highlightColor);
                }
                else if (pMouseY >= y + 25 && pMouseY <= y + 41) {
                    renderSkillDescription(pPoseStack, x, y, rpgClass.getSkill2());
                    this.font.draw(pPoseStack, I18n.get(rpgClass.getSkill2().getLabel()), x + 80, y + 29, highlightColor);
                }
                else if (pMouseY >= y + 42 && pMouseY <= y + 58) {
                    renderSkillDescription(pPoseStack, x, y, rpgClass.getSkill3());
                    this.font.draw(pPoseStack, I18n.get(rpgClass.getSkill3().getLabel()), x + 80, y + 46, highlightColor);
                }
                else if (pMouseY >= y + 59 && pMouseY <= y + 75) {
                    renderSkillDescription(pPoseStack, x, y, rpgClass.getSkill4());
                    this.font.draw(pPoseStack, I18n.get(rpgClass.getSkill4().getLabel()), x + 80, y + 63, highlightColor);
                }
            }
            boolean craftSuccessful = this.menu.getCraftSuccessful();

            // (Entity) Attempt counter will not be reset when we close the menu. Gotta synchronize this manually on the first rendering pass.
            if (!resetComplete) {
                lastAttemptCounter = menu.getAttemptCounter();
                resetComplete = true;
            }

            // Only update animation if the craft status is updated
            if (menu.getAttemptCounter() != lastAttemptCounter) {
                // Animation not in progress
                if (forgeFrame == -1 || failedFrame == -1) {
                    if (craftSuccessful) {
                        startForgeAnim();
                    } else {
                        startFailedAnim();
                    }
                }
                lastAttemptCounter = menu.getAttemptCounter();
            }

            // Animation in progress
            if (forgeFrame > -1) {
                // Draw blank space over the idle pose
                RenderSystem.setShaderTexture(0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/anim_forge/forge_blank.png"));
                GuiComponent.blit(pPoseStack,x + 12, y + 14,0,0,38,32, 38,32);

                // Draw the forgeFrame
                RenderSystem.setShaderTexture(0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/anim_forge/forge_" + forgeFrame + ".png"));
                GuiComponent.blit(pPoseStack,x + 12, y + 14,0,0,38,32, 38,32);
            } else if (failedFrame > -1) {

                // Draw the failedFrame
                RenderSystem.setShaderTexture(0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/anim_unlock_failed/unlock_failed_" + failedFrame + ".png"));
                GuiComponent.blit(pPoseStack,x + 23, y + 22,0,0,15,15, 15,15);
            }
            nextFrames();
        }
        if (menu.getSlot(TE_INVENTORY_FIRST_SLOT_INDEX).hasItem()) {
            // Draw blank space over the offhand slot icon
            RenderSystem.setShaderTexture(0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/blank_slot.png"));
            GuiComponent.blit(pPoseStack,x + 13, y + 47,0,0,16,16, 16,16);
        }
    }
    private void renderSkillDescription(PoseStack pPoseStack, int x, int y, SkillType skill) {
        RenderSystem.setShaderTexture(0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/skill_details.png"));
        GuiComponent.blit(pPoseStack,x + 180, y,0,0,98,112, 98,112);
        this.font.drawWordWrap(FormattedText.of(I18n.get(skill.getLabel())), x + 186, y + 6, 88, menu.getRpgClass().getClassColor());
        this.font.drawWordWrap(FormattedText.of(I18n.get(skill.getDescription())), x + 186, y + 18, 88, 11053224);
        this.font.drawWordWrap(FormattedText.of(I18n.get("skill_cooldown.rpgmod") + ": " + String.format("%.1f", skill.getCooldown() / 20.0) + "s"), x + 186, y + 96, 88, 11053224);

    }


    private void nextFrames() {
        slowdownCounter++;
        if (slowdownCounter % 5 == 0) {
            slowdownCounter = 0;
            if (forgeFrame > -1) { forgeFrame--; }
            if (failedFrame > -1) { failedFrame--; }
        }
    }

    private void startForgeAnim() {
        this.forgeFrame = 4;
    }
    private void startFailedAnim() {
        this.failedFrame = 2;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int p_98760_) {
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null) {
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;
            if (pMouseX >= x + 76 && pMouseX < x + 151) {
                if (pMouseY >= y + 8 && pMouseY <= y + 24 && this.menu.clickMenuButton(this.minecraft.player, 0)) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
                    return true;
                }
                else if (pMouseY >= y + 25 && pMouseY <= y + 41 && this.menu.clickMenuButton(this.minecraft.player, 1)) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 1);
                    return true;
                }
                else if (pMouseY >= y + 42 && pMouseY <= y + 58 && this.menu.clickMenuButton(this.minecraft.player, 2)) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 2);
                    return true;
                }
                else if (pMouseY >= y + 59 && pMouseY <= y + 75 && this.menu.clickMenuButton(this.minecraft.player, 3)) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 3);
                    return true;
                }
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, p_98760_);
    }

    private void renderSkillInfo(PoseStack pPoseStack, int x, int y, int pMouseX, int pMouseY) {
        if (pMouseX >= x + 76 && pMouseX < x + 151) {
            if (pMouseY >= y + 8 && pMouseY <= y + 24) {
                blit(pPoseStack, x + 76, y + 8, 0, 185, 75, 16);
            }
            else if (pMouseY >= y + 25 && pMouseY <= y + 41) {
                blit(pPoseStack, x + 76, y + 25, 0, 185, 75, 16);
            }
            else if (pMouseY >= y + 42 && pMouseY <= y + 58) {
                blit(pPoseStack, x + 76, y + 42, 0, 185, 75, 16);
            }
            else if (pMouseY >= y + 59 && pMouseY <= y + 75) {
                blit(pPoseStack, x + 76, y + 59, 0, 185, 75, 16);
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