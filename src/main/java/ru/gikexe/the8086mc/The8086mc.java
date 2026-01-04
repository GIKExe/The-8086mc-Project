package ru.gikexe.the8086mc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import ru.gikexe.the8086mc.block.ModBlocks;
import ru.gikexe.the8086mc.item.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class The8086mc implements ModInitializer {
	public static final String MOD_ID = "the8086mc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		// CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
		// 	dispatcher.register(Commands.literal("test_command").executes(context -> {
		// 		context.getSource().sendSuccess(() -> Component.literal("Called /test_command."), false);
		// 		return 1;
		// 	}));
		// });
	}

	public static ResourceLocation getLocation(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}

	public static ResourceKey<Item> getItemKey(String name) {
		return ResourceKey.create(Registries.ITEM, getLocation(name));
	}

	public static ResourceKey<Block> getBlockKey(String name) {
		return ResourceKey.create(Registries.BLOCK, getLocation(name));
	}
}