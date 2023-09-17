package net.noahvolson.rpgmod.entity.client.model;

import net.minecraft.resources.ResourceLocation;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.skill.warrior.MeatHookAttack;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MeatHookModel extends AnimatedGeoModel<MeatHookAttack> {
    @Override
    public ResourceLocation getModelResource(MeatHookAttack object) {
        return new ResourceLocation(RpgMod.MOD_ID, "geo/meathook.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MeatHookAttack object) {
        return new ResourceLocation(RpgMod.MOD_ID, "textures/entity/meathook.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MeatHookAttack animatable) {
        return new ResourceLocation(RpgMod.MOD_ID, "animations/meathook.animation.json");
    }
}
