package ru.gikexe.myfirstmod;

import net.fabricmc.api.ModInitializer;
import ru.gikexe.myfirstmod.Item.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFirstMod implements ModInitializer {
	public static final String MOD_ID = "myfirstmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}