package com.black_dog20.moregadgets.init;

import com.black_dog20.moregadgets.item.ItemBase;
import com.black_dog20.moregadgets.item.gadgets.ItemRebreather;
import com.black_dog20.moregadgets.item.gadgets.ItemRebreather.RebreatherLevel;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModItems {
	
	public static final ItemBase blueDust = new ItemBase("blue_dust");
	public static final ItemBase airFilter = new ItemBase("air_filter");
	public static final ItemRebreather woodenRebreather = new ItemRebreather("wooden_rebreather", RebreatherLevel.WOOD );
	public static final ItemRebreather stoneRebreather = new ItemRebreather("stone_rebreather", RebreatherLevel.STONE);
	public static final ItemRebreather ironRebreather = new ItemRebreather("iron_rebreather", RebreatherLevel.IRON);
	public static final ItemRebreather goldRebreather = new ItemRebreather("gold_rebreather", RebreatherLevel.GOLD);
	public static final ItemRebreather diamondRebreather = new ItemRebreather("diamond_rebreather", RebreatherLevel.DIAMOND);
	public static final ItemRebreather emeraldRebreather = new ItemRebreather("emerald_rebreather", RebreatherLevel.EMERALD);
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		
		r.register(blueDust);
		r.register(airFilter);
		r.register(woodenRebreather);
		r.register(stoneRebreather);
		r.register(ironRebreather);
		r.register(goldRebreather);
		r.register(diamondRebreather);
		r.register(emeraldRebreather);
	}
}
