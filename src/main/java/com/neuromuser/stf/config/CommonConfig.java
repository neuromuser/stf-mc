package com.neuromuser.stf.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "stf_common")
public class CommonConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public float globalExpMultiplier = 1.0f;

    @ConfigEntry.Gui.Tooltip
    public float statIncreaseMultiplier = 1.0f;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    public int baseMaxHealth = 1;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 200)
    public int maxStatLevel = 100;

    @Override
    public void validatePostLoad() {
        if (globalExpMultiplier < 0.2f) globalExpMultiplier = 0.2f;
        if (globalExpMultiplier > 5.0f) globalExpMultiplier = 5.0f;
        if (statIncreaseMultiplier < 0.2f) statIncreaseMultiplier = 0.2f;
        if (statIncreaseMultiplier > 5.0f) statIncreaseMultiplier = 5.0f;
    }
}
