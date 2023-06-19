package net.noahvolson.rpgmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_ABILITY_BINDINGS = "key.category.rpgmod.ability_bindings";
    public static final String KEY_ABILITY_1 = "key.rpgmod.ability_1";
    public static final String KEY_ABILITY_2 = "key.rpgmod.ability_2";
    public static final String KEY_ABILITY_3 = "key.rpgmod.ability_3";
    public static final String KEY_ABILITY_4 = "key.rpgmod.ability_4";
    public static final String KEY_SWAP_CLASS = "key.rpgmod.swap_class";

    public static final KeyMapping ABILITY_1_KEY = new KeyMapping(KEY_ABILITY_1, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_ABILITY_BINDINGS);

    public static final KeyMapping ABILITY_2_KEY = new KeyMapping(KEY_ABILITY_2, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, KEY_CATEGORY_ABILITY_BINDINGS);

    public static final KeyMapping ABILITY_3_KEY = new KeyMapping(KEY_ABILITY_3, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_ABILITY_BINDINGS);

    public static final KeyMapping ABILITY_4_KEY = new KeyMapping(KEY_ABILITY_4, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_T, KEY_CATEGORY_ABILITY_BINDINGS);

    public static final KeyMapping SWAP_CLASS_KEY = new KeyMapping(KEY_SWAP_CLASS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_ABILITY_BINDINGS);
}
