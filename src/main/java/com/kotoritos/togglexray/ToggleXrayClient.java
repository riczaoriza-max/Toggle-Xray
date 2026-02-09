package com.kotoritos.togglexray;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class ToggleXrayClient implements ClientModInitializer {
    public static final String MOD_ID = "toggle_xray";
    private static final KeyBinding TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(
        new KeyBinding(
            "key.toggle_xray.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F6,
            "category.toggle_xray"
        )
    );

    private static boolean xrayEnabled = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TOGGLE_KEY.wasPressed()) {
                xrayEnabled = !xrayEnabled;
                if (client.player != null) {
                    client.player.sendMessage(
                        Text.translatable(xrayEnabled
                            ? "text.toggle_xray.enabled"
                            : "text.toggle_xray.disabled"),
                        true
                    );
                }
                if (client.worldRenderer != null) {
                    client.worldRenderer.reload();
                }
            }
        });

        BlockRenderEvents.BEFORE.register((world, state, pos, blockEntity, random) -> {
            if (!xrayEnabled) {
                return true;
            }
            return XrayBlockFilter.shouldRender(state);
        });
    }
}
