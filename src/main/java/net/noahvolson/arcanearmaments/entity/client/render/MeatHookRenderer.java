package net.noahvolson.arcanearmaments.entity.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.entity.client.model.MeatHookModel;
import net.noahvolson.arcanearmaments.entity.skill.warrior.GrappleAttack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
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

    @Override
    // Need to override the default LivingEntity rendering behavior (as this is a projectile)
    public void actuallyRender(PoseStack poseStack, GrappleAttack animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(-animatable.getXRot()));
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
