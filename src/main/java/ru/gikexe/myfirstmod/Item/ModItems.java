package ru.gikexe.myfirstmod.Item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import ru.gikexe.myfirstmod.MyFirstMod;

public class ModItems {
	public static final Item PURE_CRYSTAL = registerItem("pure_crystal", new Item.Properties().rarity(Rarity.RARE));

	private static Item registerItem(String name, Item.Properties properties) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MyFirstMod.MOD_ID, name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
		Item item = new Item(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	public static void registerModItems() {
		MyFirstMod.LOGGER.info("Registering Mod Items for " + MyFirstMod.MOD_ID);
	}
}
