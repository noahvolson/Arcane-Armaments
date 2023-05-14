package net.noahvolson.rpgmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_ABILITY_BINDINGS = "key.category.rpgmod.ability_bindings";
    public static final String KEY_ABILITY_1 = "key.rpgmod.ability_1";

    public static final KeyMapping ABILITY_1_KEY = new KeyMapping(KEY_ABILITY_1, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_ABILITY_BINDINGS);
}
