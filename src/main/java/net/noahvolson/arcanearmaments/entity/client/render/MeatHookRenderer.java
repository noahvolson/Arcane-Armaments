package net.noahvolson.arcanearmaments.entity.client.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.entity.client.model.MeatHookModel;
import net.noahvolson.arcanearmaments.entity.skill.warrior.GrappleAttack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MeatHookRenderer extends GeoEntityRenderer<GrappleAttack> {


    public MeatHookRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MeatHookModel());
    }

    @Override
    public ResourceLocation getTextureLocation(GrappleAttack instance) {
        return new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/entity/meathook.png");
    }


    @Override
    public RenderType getRenderType(GrappleAttack animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
