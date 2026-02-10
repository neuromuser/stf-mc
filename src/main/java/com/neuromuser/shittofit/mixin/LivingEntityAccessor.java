package com.neuromuser.shittofit.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("itemUseTimeLeft")
    int getItemUseTimeLeft();

    @Accessor("itemUseTimeLeft")
    void setItemUseTimeLeft(int value);

    @Accessor("activeItemStack")
    ItemStack getActiveItemStack();

    @Accessor("activeItemStack")
    void setActiveItemStack(ItemStack stack);
}