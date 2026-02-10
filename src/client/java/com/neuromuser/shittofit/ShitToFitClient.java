package com.neuromuser.shittofit;

import com.neuromuser.shittofit.network.ClientNetworkHelper;
import com.neuromuser.shittofit.ui.FitnessScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ShitToFitClient implements ClientModInitializer {
        private static KeyBinding fitnessMenuKey;

        @Override
        public void onInitializeClient() {
                ShitToFit.LOGGER.info("Client initialization for {}!", ShitToFit.MOD_ID);

                ClientNetworkHelper.registerClientPackets();

                fitnessMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                        "key.stf.fitness_menu",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_K,
                        "category.stf.fitness"
                ));

                ClientTickEvents.END_CLIENT_TICK.register(client -> {
                        while (fitnessMenuKey.wasPressed()) {
                                if (client.player != null) {
                                        client.setScreen(new FitnessScreen());
                                        if (client.currentScreen instanceof FitnessScreen screen) {
                                                screen.updateUI();
                                        }
                                }
                        }
                });
        }
}