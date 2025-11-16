package ru.gikexe.the8086mc.Item;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import ru.gikexe.the8086mc.The8086mc;
import ru.gikexe.the8086mc.Block.ModBlocks;

public class ModItems {
	public static final Item CARBON_QUARTZ_POWDER = registerItem("carbon_quartz_powder");
	public static final Item METALLURGICAL_SILICON = registerItem("metallurgical_silicon",
		new Properties().rarity(Rarity.UNCOMMON));
	public static final Item METALLURGICAL_SILICON_BLOCK = registerBlock(ModBlocks.METALLURGICAL_SILICON_BLOCK,
		new Properties().rarity(Rarity.UNCOMMON));
	public static final Item IRON_HAMMER = registerItem("iron_hammer", IronHammerItem::new,
		new Properties().craftRemainder(ModItems.IRON_HAMMER).pickaxe(ToolMaterial.IRON, 1.0F, -2.8F).durability(64));
	public static final Item METAL_SHEARS = registerItem("metal_shears", MetalShearsItem::new,
		new Properties().craftRemainder(ModItems.METAL_SHEARS).durability(64));
	public static final Item COPPER_PLATE = registerItem("copper_plate");
	public static final Item COPPER_WIRE = registerItem("copper_wire");
	public static final Item COPPER_CONTACT = registerItem("copper_contact");

	// private static Function<Item.Properties, Item> createBlockItemWithCustomItemName(Block block) {
	// 	return properties -> new BlockItem(block, properties.useItemDescriptionPrefix());
	// }

	private static ResourceKey<Item> modItemId(String string) {
		return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(The8086mc.MOD_ID, string));
	}

	private static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> resourceKey) {
		return ResourceKey.create(Registries.ITEM, resourceKey.location());
	}

	public static Item registerBlock(Block block) {
		return registerBlock(block, BlockItem::new);
	}

	public static Item registerBlock(Block block, Item.Properties properties) {
		return registerBlock(block, BlockItem::new, properties);
	}

	public static Item registerBlock(Block block, UnaryOperator<Item.Properties> unaryOperator) {
		return registerBlock(
			block, (BiFunction<Block, Item.Properties, Item>)((blockx, properties) -> new BlockItem(blockx, (Item.Properties)unaryOperator.apply(properties)))
		);
	}

	public static Item registerBlock(Block block, Block... blocks) {
		Item item = registerBlock(block);

		for (Block block2 : blocks) {
			Item.BY_BLOCK.put(block2, item);
		}

		return item;
	}

	public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> biFunction) {
		return registerBlock(block, biFunction, new Item.Properties());
	}

	@SuppressWarnings("deprecation")
	public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> biFunction, Item.Properties properties) {
		return registerItem(
			blockIdToItemId(block.builtInRegistryHolder().key()), propertiesx -> (Item)biFunction.apply(block, propertiesx), properties.useBlockDescriptionPrefix()
		);
	}

	public static Item registerItem(String string, Function<Item.Properties, Item> function) {
		return registerItem(modItemId(string), function, new Item.Properties());
	}

	public static Item registerItem(String string, Function<Item.Properties, Item> function, Item.Properties properties) {
		return registerItem(modItemId(string), function, properties);
	}

	public static Item registerItem(String string, Item.Properties properties) {
		return registerItem(modItemId(string), Item::new, properties);
	}

	public static Item registerItem(String string) {
		return registerItem(modItemId(string), Item::new, new Item.Properties());
	}

	public static Item registerItem(ResourceKey<Item> resourceKey, Function<Item.Properties, Item> function) {
		return registerItem(resourceKey, function, new Item.Properties());
	}

	public static Item registerItem(ResourceKey<Item> resourceKey, Function<Item.Properties, Item> function, Item.Properties properties) {
		Item item = (Item)function.apply(properties.setId(resourceKey));
		if (item instanceof BlockItem blockItem) {
			blockItem.registerBlocks(Item.BY_BLOCK, item);
		}

		return Registry.register(BuiltInRegistries.ITEM, resourceKey, item);
	}

	public static void registerModItems() {
		The8086mc.LOGGER.info("Registering Mod Items for " + The8086mc.MOD_ID);
	}
}
