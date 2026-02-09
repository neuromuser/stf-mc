package com.neuromuser.shittofit;

import net.fabricmc.api.ClientModInitializer;

public class ShitToFitClient implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
                ShitToFit.LOGGER.info(
                    "Client initialization for {}!", ShitToFit.MOD_ID);
        }
}
