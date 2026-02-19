package com.neuromuser.stf.ui;

import com.neuromuser.stf.components.ModComponents;
import com.neuromuser.stf.components.PlayerDataComponent;
import com.neuromuser.stf.config.ClientConfig;
import com.neuromuser.stf.config.CommonConfig;
import com.neuromuser.stf.exercise.ExerciseManager;
import com.neuromuser.stf.network.ClientNetworkHelper;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FitnessScreen extends BaseOwoScreen<FlowLayout> {
    private final Map<ExerciseManager.StatType, LabelComponent> statLabels = new HashMap<>();
    private final Map<ExerciseManager.StatType, FlowLayout> statBars = new HashMap<>();
    private final Map<ExerciseManager.StatType, ButtonComponent> upgradeButtons = new HashMap<>();
    private final Map<Integer, ButtonComponent> exerciseButtons = new HashMap<>();

    private LabelComponent xpLabel;
    private LabelComponent pointsLabel;
    private Integer currentHoveredIndex = null;
    private ExerciseManager.StatType currentHoveredUpgrade = null;

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        ClientConfig clientConfig = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
        CommonConfig commonConfig = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();

        FlowLayout mainPanel = Containers.verticalFlow(Sizing.content(), Sizing.content());
        mainPanel.surface(Surface.DARK_PANEL).padding(Insets.of(15));

        LabelComponent titleLabel = Components.label(Text.translatable("screen.stf.fitness.title"));
        titleLabel.shadow(true);
        mainPanel.child(titleLabel);

        FlowLayout topInfo = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        topInfo.gap(10).padding(Insets.of(5));

        FlowLayout xpPanel = (FlowLayout) Containers.verticalFlow(Sizing.content(), Sizing.content()).padding(Insets.of(5));
        xpPanel.child(Components.label(Text.translatable("screen.stf.fitness.overall_level")));
        xpLabel = Components.label(Text.empty());
        xpPanel.child(xpLabel);

        FlowLayout pointsPanel = (FlowLayout) Containers.verticalFlow(Sizing.content(), Sizing.content()).padding(Insets.of(5));
        pointsPanel.child(Components.label(Text.translatable("screen.stf.fitness.skill_points")));
        pointsLabel = Components.label(Text.empty());
        pointsPanel.child(pointsLabel);

        topInfo.child(xpPanel).child(pointsPanel);
        mainPanel.child(topInfo);

        FlowLayout contentArea = Containers.horizontalFlow(Sizing.content(), Sizing.content()).gap(10);
        FlowLayout leftPanel = Containers.verticalFlow(Sizing.content(), Sizing.content()).gap(10);

        FlowLayout exercisesPanel = Containers.verticalFlow(Sizing.content(), Sizing.content());
        exercisesPanel.surface(Surface.DARK_PANEL).padding(Insets.of(8));
        exercisesPanel.child(Components.label(Text.translatable("screen.stf.fitness.exercises")));

        for (int i = 0; i < ClientConfig.MAX_EXERCISES; i++) {
            ClientConfig.ExerciseConfig exerciseConfig = clientConfig.getExercise(i);
            if (exerciseConfig == null || !exerciseConfig.enabled) continue;

            final int index = i;
            ButtonComponent btn = Components.button(Text.translatable(exerciseConfig.name), button -> onExercise(index));
            btn.horizontalSizing(Sizing.fixed(120));

            MutableText tooltip = Text.empty();
            for (ExerciseManager.StatType stat : ExerciseManager.StatType.values()) {
                int gain = exerciseConfig.getExpForStat(stat);
                if (gain > 0) {
                    int actual = Math.round(gain * commonConfig.globalExpMultiplier);
                    tooltip.append(Text.translatable(getStatTranslationKey(stat))).append(Text.literal(": +" + actual + " XP\n"));
                }
            }
            btn.tooltip(tooltip);

            exerciseButtons.put(i, btn);
            exercisesPanel.child(btn);
        }
        leftPanel.child(exercisesPanel);

        FlowLayout statsPanel = Containers.verticalFlow(Sizing.content(), Sizing.content());
        statsPanel.surface(Surface.DARK_PANEL).padding(Insets.of(8));
        statsPanel.child(Components.label(Text.translatable("screen.stf.fitness.stats")));

        FlowLayout statsList = Containers.verticalFlow(Sizing.content(), Sizing.content()).gap(2);

        for (ExerciseManager.StatType stat : ExerciseManager.StatType.values()) {
            FlowLayout statRow = (FlowLayout) Containers.horizontalFlow(Sizing.content(), Sizing.content()).gap(4).verticalAlignment(VerticalAlignment.CENTER);

            LabelComponent nameLabel = Components.label(Text.translatable(getStatTranslationKey(stat)));
            nameLabel.horizontalSizing(Sizing.fixed(90));
            nameLabel.tooltip(Text.translatable("stat.stf.description." + stat.name().toLowerCase()));

            LabelComponent valueLabel = Components.label(Text.empty());
            valueLabel.horizontalSizing(Sizing.fixed(40));
            statLabels.put(stat, valueLabel);

            FlowLayout progressBar = createProgressBar(stat);
            statBars.put(stat, progressBar);

            ButtonComponent upgradeBtn = Components.button(Text.literal("+"), btn -> onUpgrade(stat));
            upgradeBtn.sizing(Sizing.fixed(20), Sizing.fixed(18));
            upgradeBtn.tooltip(Text.translatable("screen.stf.fitness.upgrade_tooltip"));
            upgradeButtons.put(stat, upgradeBtn);

            statRow.child(nameLabel).child(valueLabel).child(progressBar).child(upgradeBtn);
            statsList.child(statRow);
        }

        statsPanel.child(statsList);
        contentArea.child(leftPanel).child(statsPanel);
        mainPanel.child(contentArea);
        rootComponent.child(mainPanel);

        updateUI();
    }

    @Override
    public void tick() {
        super.tick();
        Integer lastExerciseHover = currentHoveredIndex;
        ExerciseManager.StatType lastUpgradeHover = currentHoveredUpgrade;

        currentHoveredIndex = null;
        currentHoveredUpgrade = null;

        for (Map.Entry<Integer, ButtonComponent> entry : exerciseButtons.entrySet()) {
            if (entry.getValue().isHovered()) {
                currentHoveredIndex = entry.getKey();
                break;
            }
        }

        for (Map.Entry<ExerciseManager.StatType, ButtonComponent> entry : upgradeButtons.entrySet()) {
            if (entry.getValue().isHovered()) {
                currentHoveredUpgrade = entry.getKey();
                break;
            }
        }

        if (!Objects.equals(lastExerciseHover, currentHoveredIndex) || lastUpgradeHover != currentHoveredUpgrade) {
            updateUI();
        }
    }

    private FlowLayout createProgressBar(ExerciseManager.StatType stat) {
        FlowLayout bar = Containers.horizontalFlow(Sizing.fixed(80), Sizing.fixed(8));

        bar.surface((context, component) -> {
            context.fill(component.x(), component.y(), component.x() + component.width(), component.y() + component.height(), 0xFF1A1A1A);

            assert client != null;
            if (client.player == null) return;
            PlayerDataComponent data = ModComponents.PLAYER_DATA.get(client.player);

            int expGain = 0;

            if (currentHoveredUpgrade == stat && data.getAvailableLevelPoints() > 0 && !data.isStatAtMaxLevel(stat)) {
                expGain = 100;
            } else if (currentHoveredIndex != null) {
                ClientConfig clientConfig = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
                CommonConfig commonConfig = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();
                ClientConfig.ExerciseConfig exerciseConfig = clientConfig.getExercise(currentHoveredIndex);
                if (exerciseConfig != null) {
                    expGain = Math.round(exerciseConfig.getExpForStat(stat) * commonConfig.globalExpMultiplier);
                }
            }

            if (expGain > 0) {
                int max = 100 + (data.getStatLevel(stat) * 2);
                float progress = data.getStatProgress(stat);

                int currentWidth = (int) (progress * component.width());
                int previewWidth = (int) (((float) expGain / max) * component.width());

                float alpha = (float) ((Math.sin(System.currentTimeMillis() / 150.0) + 1.0) / 2.0);
                int color = ((int) (alpha * 100 + 80) << 24) | 0x00FF00;

                int startX = component.x() + currentWidth;
                int endX = Math.min(startX + previewWidth, component.x() + component.width());

                context.fill(startX, component.y(), endX, component.y() + component.height(), color);
            }
        });

        FlowLayout fill = Containers.horizontalFlow(Sizing.fill(0), Sizing.fill(100));
        fill.surface(Surface.flat(0xFF00AA00));
        bar.child(fill);

        return bar;
    }

    public void updateUI() {
        assert client != null;
        if (client.player == null) return;
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(client.player);

        pointsLabel.text(Text.literal(String.valueOf(data.getAvailableLevelPoints())));

        int overallLevel = data.getOverallLevel();
        int overallExp = data.getExperiencePoints();
        int requiredExp = 100;

        xpLabel.text(Text.literal("Level " + overallLevel + " - XP: " + overallExp % 100 + " / " + requiredExp));

        for (ExerciseManager.StatType stat : ExerciseManager.StatType.values()) {
            int level = data.getStatLevel(stat);
            int max = 100 + (level * 2);
            float progress = data.getStatProgress(stat);

            statLabels.get(stat).text(Text.literal("Lv " + level));
            statBars.get(stat).tooltip(Text.literal((int)(progress * max) + " / " + max + " XP"));

            FlowLayout bar = statBars.get(stat);
            bar.children().get(0).horizontalSizing(Sizing.fill((int)(progress * 100)));

            upgradeButtons.get(stat).active(data.getAvailableLevelPoints() > 0 && !data.isStatAtMaxLevel(stat));
        }
    }

    private void onExercise(int exerciseIndex) {
        assert client != null;
        client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        ClientNetworkHelper.sendCompleteExercise(exerciseIndex);
    }

    private void onUpgrade(ExerciseManager.StatType stat) {
        assert client != null;
        client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f));
        ClientNetworkHelper.sendUpgradeStat(stat);
    }

    private String getStatTranslationKey(ExerciseManager.StatType stat) {
        return "stat.stf." + stat.name().toLowerCase();
    }

    @Override
    public boolean shouldPause() { return true; }
}