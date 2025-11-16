package ru.gikexe.the8086mc.Block;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import ru.gikexe.the8086mc.The8086mc;

public class ModBlocks {
	public static final Block METALLURGICAL_SILICON_BLOCK = register("metallurgical_silicon_block",
		Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F) );

	public static Block register(ResourceKey<Block> resourceKey, Function<Properties, Block> function, Properties properties) {
		Block block = (Block)function.apply(properties.setId(resourceKey));
		return Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
	}

	public static Block register(ResourceKey<Block> resourceKey, Properties properties) {
		return register(resourceKey, Block::new, properties);
	}

	private static ResourceKey<Block> modBlockId(String string) {
		return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(The8086mc.MOD_ID, string));
	}

	private static Block register(String string, Function<Properties, Block> function, Properties properties) {
		return register(modBlockId(string), function, properties);
	}

	private static Block register(String string, Properties properties) {
		return register(string, Block::new, properties);
	}

	public static void registerModBlocks() {
		The8086mc.LOGGER.info("Registering Mod Block for " + The8086mc.MOD_ID);
	}
}
