package com.black_dog20.moregadgets.init;

import com.black_dog20.moregadgets.handler.enchanments.SoulRipEnchantment;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class ModEnchantments {

	public static final SoulRipEnchantment soulRipEnchantment = new SoulRipEnchantment();
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Enchantment> event) {
		IForgeRegistry<Enchantment> r = event.getRegistry();
		r.register(soulRipEnchantment);
	}
}
