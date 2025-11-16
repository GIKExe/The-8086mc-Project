package ru.gikexe.the8086mc.Item;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import ru.gikexe.the8086mc.The8086mc;
import ru.gikexe.the8086mc.Block.ModBlocks;

public class ModItems {
	public static final Item CARBON_QUARTZ_POWDER = registerItem("carbon_quartz_powder");
	public static final Item METALLURGICAL_SILICON = registerItem("metallurgical_silicon");
	public static final Item METALLURGICAL_SILICON_BLOCK = registerItem("metallurgical_silicon_block", ModBlocks.METALLURGICAL_SILICON_BLOCK);
	public static final Item IRON_HAMMER = registerItem("iron_hammer",
		newProperties().craftRemainder(ModItems.IRON_HAMMER).pickaxe(ToolMaterial.IRON, 1.0F, -2.8F).durability(64),
		IronHammerItem::new );
	public static final Item METAL_SHEARS = registerItem("metal_shears",
		newProperties().craftRemainder(ModItems.METAL_SHEARS).durability(64),
		MetalShearsItem::new );
	public static final Item COPPER_PLATE = registerItem("copper_plate");
	public static final Item COPPER_WIRE = registerItem("copper_wire");
	public static final Item COPPER_CONTACT = registerItem("copper_contact");
	public static final Item IRON_PLATE = registerItem("iron_plate");


	private static Properties newProperties() {
		return new Properties();
	}

	// регистрации обычных предметов
	private static Item registerItem(String name) {
		return registerItem(name, newProperties());
	}

	private static Item registerItem(String name, Properties properties) {
		return registerItem(name, properties, Item::new);
	}

	private static Item registerItem(String name, Properties properties, Function<Properties, Item> function) {
		ResourceKey<Item> key = The8086mc.getItemKey(name);
		Item item = (Item) function.apply(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	// регистрации предметов привязанных к блокам
	private static Item registerItem(String name, Block block) {
		return registerItem(name, newProperties(), block);
	}

	private static Item registerItem(String name, Properties properties, Block block) {
		return registerItem(name, properties, BlockItem::new, block);
	}

	private static Item registerItem(String name, Properties properties, BiFunction<Block, Item.Properties, BlockItem> function, Block block) {
		ResourceKey<Item> key = The8086mc.getItemKey(name);
		Item item = (Item) function.apply(block, properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}


	public static void registerModItems() {
		The8086mc.LOGGER.info("Registering Mod Items for " + The8086mc.MOD_ID);
	}
}
