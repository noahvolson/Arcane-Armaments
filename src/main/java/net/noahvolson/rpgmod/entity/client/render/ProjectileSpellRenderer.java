package net.noahvolson.rpgmod.entity.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.spell.AbstractProjectileSpell;
import org.jetbrains.annotations.NotNull;

public class ProjectileSpellRenderer extends ArrowRenderer<AbstractProjectileSpell> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(RpgMod.MOD_ID, "textures/entity/invisible_arrow.png");

    public ProjectileSpellRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractProjectileSpell p_114482_) {
        return TEXTURE;
    }
}
