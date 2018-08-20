package com.black_dog20.moregadgets.init;

import com.black_dog20.moregadgets.potion.PotionHorseshoeCooldown;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {
	
	public static final PotionHorseshoeCooldown luckyHorseShoeCooldown = new PotionHorseshoeCooldown();
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Potion> event) {
		IForgeRegistry<Potion> r = event.getRegistry();
		r.register(luckyHorseShoeCooldown);
	}
}
