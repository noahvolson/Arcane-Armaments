package net.noahvolson.rpgmod.entity.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.client.model.MeatHookModel;
import net.noahvolson.rpgmod.entity.skill.warrior.GrappleAttack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class MeatHookRenderer extends GeoProjectilesRenderer<GrappleAttack> {


    public MeatHookRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MeatHookModel());
    }

    @Override
    public ResourceLocation getTextureLocation(GrappleAttack instance) {
        return new ResourceLocation(RpgMod.MOD_ID, "textures/entity/meathook.png");
    }


    @Override
    public RenderType getRenderType(GrappleAttack animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.2f, 1.2f, 1.2f); // Use to scale the entity
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
