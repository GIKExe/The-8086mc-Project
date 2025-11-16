package ru.gikexe.the8086mc.Item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MetalShearsItem extends Item{
	public MetalShearsItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getRecipeRemainder(ItemStack stack) {
		ItemStack damagedStack = stack.copy();
		damagedStack.setDamageValue(damagedStack.getDamageValue() + 1);
		if (damagedStack.getDamageValue() >= damagedStack.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return damagedStack;
	}
}
