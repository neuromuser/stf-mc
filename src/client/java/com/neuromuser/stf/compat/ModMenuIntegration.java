package com.neuromuser.stf.compat;

import com.neuromuser.stf.config.ClientConfig;
import com.neuromuser.stf.config.CommonConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigSelectScreen::new;
    }

    private static class ConfigSelectScreen extends Screen {
        private final Screen parent;

        ConfigSelectScreen(Screen parent) {
            super(Text.translatable("text.autoconfig.stf.title"));
            this.parent = parent;
        }

        @Override
        protected void init() {
            int centerX = this.width / 2;
            int centerY = this.height / 2;

            this.addDrawableChild(ButtonWidget.builder(
                    Text.translatable("config.stf.open.client"),
                    btn -> MinecraftClient.getInstance().setScreen(
                            AutoConfig.getConfigScreen(ClientConfig.class, this).get()
                    )
            ).dimensions(centerX - 105, centerY - 12, 100, 20).build());

            this.addDrawableChild(ButtonWidget.builder(
                    Text.translatable("config.stf.open.common"),
                    btn -> MinecraftClient.getInstance().setScreen(
                            AutoConfig.getConfigScreen(CommonConfig.class, this).get()
                    )
            ).dimensions(centerX + 5, centerY - 12, 100, 20).build());

            this.addDrawableChild(ButtonWidget.builder(
                    Text.translatable("gui.back"),
                    btn -> MinecraftClient.getInstance().setScreen(parent)
            ).dimensions(centerX - 50, centerY + 20, 100, 20).build());
        }

        @Override
        public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
            this.renderBackground(context);
            super.render(context, mouseX, mouseY, delta);
            context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 40, 0xFFFFFF);
        }

        @Override
        public void close() {
            MinecraftClient.getInstance().setScreen(parent);
        }
    }
}