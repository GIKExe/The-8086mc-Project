package ru.gikexe.the8086mc;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
	public static final Block METALLURGICAL_SILICON_BLOCK = registerBlock("metallurgical_silicon_block",
		BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F) );

	private static Block registerBlock(String name, BlockBehaviour.Properties properties) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(The8086mc.MOD_ID, name);
		ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
		Block block = new Block(properties.setId(key));
		return Registry.register(BuiltInRegistries.BLOCK, key, block);
	}

	public static void registerModBlocks() {
		The8086mc.LOGGER.info("Registering Mod Block for " + The8086mc.MOD_ID);
	}
}
