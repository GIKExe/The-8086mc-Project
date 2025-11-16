package ru.gikexe.the8086mc.Item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IronHammerItem extends Item {
	public IronHammerItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getRecipeRemainder(ItemStack stack) {
		// Создаем копию текущего стека молота, чтобы не изменять оригинал напрямую
		ItemStack damagedStack = stack.copy();
		// Наносим 1 единицу урона копии
		damagedStack.setDamageValue(damagedStack.getDamageValue() + 1);

		// Если прочность исчерпана, возвращаем пустой стек, иначе - поврежденный молот
		if (damagedStack.getDamageValue() >= damagedStack.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return damagedStack;
	}
}