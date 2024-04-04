package net.noahvolson.arcanearmaments.entity.client.model;

import net.minecraft.resources.ResourceLocation;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.entity.skill.warrior.GrappleAttack;
import software.bernie.geckolib.model.GeoModel;

public class MeatHookModel extends GeoModel<GrappleAttack> {
    @Override
    public ResourceLocation getModelResource(GrappleAttack object) {
        return new ResourceLocation(ArcaneArmaments.MOD_ID, "geo/meathook.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrappleAttack object) {
        return new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/entity/meathook.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrappleAttack animatable) {
        return new ResourceLocation(ArcaneArmaments.MOD_ID, "animations/meathook.animation.json");
    }
}
