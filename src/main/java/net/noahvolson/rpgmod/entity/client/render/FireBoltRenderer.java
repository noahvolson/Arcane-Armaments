package net.noahvolson.rpgmod.entity.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.custom.FireBoltEntity;
import org.jetbrains.annotations.NotNull;

public class FireBoltRenderer extends ArrowRenderer<FireBoltEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(RpgMod.MOD_ID, "textures/entity/fire_bolt.png");

    public FireBoltRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull FireBoltEntity bolt) {
        return TEXTURE;
    }
}
