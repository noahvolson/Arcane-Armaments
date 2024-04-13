package net.noahvolson.arcanearmaments.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.noahvolson.arcanearmaments.client.ClientUnlockedSkillsData;
import net.noahvolson.arcanearmaments.entity.skill.SkillType;
import net.noahvolson.arcanearmaments.rpgclass.RpgClass;

import static net.noahvolson.arcanearmaments.screen.ArmoryMenu.TE_INVENTORY_FIRST_SLOT_INDEX;

public class ArmoryScreen extends AbstractContainerScreen<ArmoryMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ArcaneArmaments.MOD_ID,"textures/gui/armory.png");

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

    public static void renderSkillIcon(GuiGraphics guiGraphics, RpgClass rpgClass, SkillType skillType, int x, int y, int xOffset, int yOffset) {
        RenderSystem.setShaderTexture(0, rpgClass.getBackground());
        guiGraphics.blit(rpgClass.getBackground(),x + xOffset, y + yOffset,0,0,15,15, 15,15);

        if (skillType == rpgClass.getSkill1() || ClientUnlockedSkillsData.contains(skillType)) {
            RenderSystem.setShaderTexture(0, skillType.getIcon());
            guiGraphics.blit(skillType.getIcon(),x + xOffset, y + yOffset,0,0,15,15, 15,15);
        } else {
            //RenderSystem.setShaderTexture(0, new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/locked.png"));
            guiGraphics.blit(new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/locked.png"),x + xOffset, y + yOffset,0,0,15,15, 15,15);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight + 2);

        RpgClass rpgClass = this.menu.getRpgClass();

        if (rpgClass != null) {
            renderButtonHighlight(guiGraphics, x, y, pMouseX, pMouseY);

            boolean pressed0 = true;
            boolean pressed1 = ClientUnlockedSkillsData.contains(rpgClass.getSkill2());
            boolean pressed2 = ClientUnlockedSkillsData.contains(rpgClass.getSkill3());
            boolean pressed3 = ClientUnlockedSkillsData.contains(rpgClass.getSkill4());

            if (pressed0) {
                guiGraphics.blit(TEXTURE, x + 76, y + 8, 0, 202, 75, 16);
            }
            if (pressed1) {
                guiGraphics.blit(TEXTURE, x + 76, y + 25, 0, 202, 75, 16);
            }
            if (pressed2) {
                guiGraphics.blit(TEXTURE, x + 76, y + 42, 0, 202, 75, 16);
            }
            if (pressed3) {
                guiGraphics.blit(TEXTURE, x + 76, y + 59, 0, 202, 75, 16);
            }

            // Render Skill icons
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            renderSkillIcon(guiGraphics, rpgClass, rpgClass.getSkill1(), x, y, 60, 9);
            renderSkillIcon(guiGraphics, rpgClass, rpgClass.getSkill2(), x, y, 60, 26);
            renderSkillIcon(guiGraphics, rpgClass, rpgClass.getSkill3(), x, y, 60, 43);
            renderSkillIcon(guiGraphics, rpgClass, rpgClass.getSkill4(), x, y, 60, 60);

            // Render Skill craft cost items
            // NOTE: Probably shouldn't be parsing the descriptionId to find the resource location. Looking for a better solution...

            Item skill2Cost = rpgClass.getSkill2().getCraftCost(); // Skill 1 comes preloaded on the weapon, so we can skip it here
            Item skill3Cost = rpgClass.getSkill3().getCraftCost();
            Item skill4Cost = rpgClass.getSkill4().getCraftCost();

            RenderSystem.getShaderColor();
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.7f);
            if (skill2Cost != null) {
                //RenderSystem.setShaderTexture(0, getResourceLocation(skill2Cost));
                guiGraphics.blit(getResourceLocation(skill2Cost),x + 153, y + 26,0,0,16,16, 16,16);
            }
            if (skill3Cost != null) {
                //RenderSystem.setShaderTexture(0, getResourceLocation(skill3Cost));
                guiGraphics.blit(getResourceLocation(skill3Cost),x + 153, y + 43,0,0,16,16, 16,16);
            }
            if (skill3Cost != null) {
                //RenderSystem.setShaderTexture(0, getResourceLocation(skill4Cost));
                guiGraphics.blit(getResourceLocation(skill4Cost),x + 153, y + 60,0,0,16,16, 16,16);
            }
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1f);
            RenderSystem.disableBlend();

            guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(rpgClass.getSkill1().getLabel())), x + 80, y + 12, 88, pressed0 ? 10522994 : 4537905);
            guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(rpgClass.getSkill2().getLabel())), x + 80, y + 29, 88, pressed1 ? 10522994 : 4537905);
            guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(rpgClass.getSkill3().getLabel())), x + 80, y + 46, 88, pressed2 ? 10522994 : 4537905);
            guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(rpgClass.getSkill4().getLabel())), x + 80, y + 63, 88, pressed3 ? 10522994 : 4537905);

            if (pMouseX >= x + 76 && pMouseX < x + 151) {
                int highlightColor = 16645499; //pressed 10522994
                if (pMouseY >= y + 8 && pMouseY <= y + 24) {
                    renderSkillDescription(guiGraphics, x, y, rpgClass.getSkill1());
                    guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(rpgClass.getSkill1().getLabel())), x + 80, y + 12, 88, highlightColor);
                }
                else if (pMouseY >= y + 25 && pMouseY <= y + 41) {
                    renderSkillDescription(guiGraphics, x, y, rpgClass.getSkill2());
                    guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(rpgClass.getSkill2().getLabel())), x + 80, y + 29, 88, highlightColor);
                }
                else if (pMouseY >= y + 42 && pMouseY <= y + 58) {
                    renderSkillDescription(guiGraphics, x, y, rpgClass.getSkill3());
                    guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(rpgClass.getSkill3().getLabel())), x + 80, y + 46, 88, highlightColor);
                }
                else if (pMouseY >= y + 59 && pMouseY <= y + 75) {
                    renderSkillDescription(guiGraphics, x, y, rpgClass.getSkill4());
                    guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(rpgClass.getSkill4().getLabel())), x + 80, y + 63, 88, highlightColor);
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
                //RenderSystem.setShaderTexture(0, new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/anim_forge/forge_blank.png"));
                guiGraphics.blit(new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/anim_forge/forge_blank.png"),x + 12, y + 14,0,0,38,32, 38,32);

                // Draw the forgeFrame
                //RenderSystem.setShaderTexture(0, new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/anim_forge/forge_" + forgeFrame + ".png"));
                guiGraphics.blit(new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/anim_forge/forge_" + forgeFrame + ".png"),x + 12, y + 14,0,0,38,32, 38,32);
            } else if (failedFrame > -1) {

                // Draw the failedFrame
                //RenderSystem.setShaderTexture(0, new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/anim_unlock_failed/unlock_failed_" + failedFrame + ".png"));
                guiGraphics.blit(new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/anim_unlock_failed/unlock_failed_" + failedFrame + ".png"),x + 23, y + 22,0,0,15,15, 15,15);
            }
            nextFrames();
        }
        if (menu.getSlot(TE_INVENTORY_FIRST_SLOT_INDEX).hasItem()) {
            // Draw blank space over the offhand slot icon
            //RenderSystem.setShaderTexture(0, new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/blank_slot.png"));
            guiGraphics.blit(new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/blank_slot.png"),x + 13, y + 47,0,0,16,16, 16,16);
        }
    }
    private void renderSkillDescription(GuiGraphics guiGraphics, int x, int y, SkillType skill) {
        //RenderSystem.setShaderTexture(0, new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/skill_details.png"));
        guiGraphics.blit(new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/skill_details.png"),x + 180, y,0,0,98,112, 98,112);
        guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(skill.getLabel())), x + 186, y + 6, 88, menu.getRpgClass().getClassColor());
        guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get(skill.getDescription())), x + 186, y + 18, 88, 11053224);
        guiGraphics.drawWordWrap(this.font, FormattedText.of(I18n.get("skill_cooldown.arcanearmaments") + ": " + String.format("%.1f", skill.getCooldown() / 20.0) + "s"), x + 186, y + 96, 88, 11053224);

    }


    private void nextFrames() {
        slowdownCounter++;
        if (slowdownCounter % 10 == 0) {
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

    private void renderButtonHighlight(GuiGraphics guiGraphics, int x, int y, int pMouseX, int pMouseY) {
        if (pMouseX >= x + 76 && pMouseX < x + 151) {
            if (pMouseY >= y + 8 && pMouseY <= y + 24) {
                guiGraphics.blit(TEXTURE, x + 76, y + 8, 0, 185, 75, 16);
            }
            else if (pMouseY >= y + 25 && pMouseY <= y + 41) {
                guiGraphics.blit(TEXTURE, x + 76, y + 25, 0, 185, 75, 16);
            }
            else if (pMouseY >= y + 42 && pMouseY <= y + 58) {
                guiGraphics.blit(TEXTURE, x + 76, y + 42, 0, 185, 75, 16);
            }
            else if (pMouseY >= y + 59 && pMouseY <= y + 75) {
                guiGraphics.blit(TEXTURE, x + 76, y + 59, 0, 185, 75, 16);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}