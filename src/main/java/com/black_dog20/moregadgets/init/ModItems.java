package com.black_dog20.moregadgets.init;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.moregadgets.item.ItemBase;
import com.black_dog20.moregadgets.item.ItemFoodBase;
import com.black_dog20.moregadgets.item.ItemHealthFood;
import com.black_dog20.moregadgets.item.gadgets.ItemLuckyHorseshoe;
import com.black_dog20.moregadgets.item.gadgets.ItemLuckyHorseshoe.HorseshoeLevel;
import com.black_dog20.moregadgets.item.gadgets.ItemRebreather;
import com.black_dog20.moregadgets.item.gadgets.ItemRebreather.RebreatherLevel;
import com.black_dog20.moregadgets.item.gadgets.ItemShapeShiftingToolBag;
import com.black_dog20.moregadgets.item.gadgets.ItemTeleporter;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModItems {
	
	public static final List<Item> items = new ArrayList<>();
	
	public static final ItemBase blueDust = register(new ItemBase("burnt_dust"));
	public static final ItemBase airFilter = register(new ItemBase("air_filter"));
	public static final ItemBase woodenRebreather = register(new ItemRebreather("wooden_rebreather", RebreatherLevel.WOOD));
	public static final ItemBase stoneRebreather = register(new ItemRebreather("stone_rebreather", RebreatherLevel.STONE));
	public static final ItemBase ironRebreather = register(new ItemRebreather("iron_rebreather", RebreatherLevel.IRON));
	public static final ItemBase goldRebreather = register(new ItemRebreather("gold_rebreather", RebreatherLevel.GOLD));
	public static final ItemBase diamondRebreather = register(new ItemRebreather("diamond_rebreather", RebreatherLevel.DIAMOND));
	public static final ItemBase emeraldRebreather = register(new ItemRebreather("emerald_rebreather", RebreatherLevel.EMERALD));
	
	public static final ItemBase woodenHorseshoe = register(new ItemLuckyHorseshoe("wooden_horseshoe", HorseshoeLevel.WOOD ));
	public static final ItemBase stoneHorseshoe = register(new ItemLuckyHorseshoe("stone_horseshoe", HorseshoeLevel.STONE));
	public static final ItemBase ironHorseshoe = register(new ItemLuckyHorseshoe("iron_horseshoe", HorseshoeLevel.IRON));
	public static final ItemBase goldHorseshoe = register(new ItemLuckyHorseshoe("gold_horseshoe", HorseshoeLevel.GOLD));
	public static final ItemBase diamondHorseshoe = register(new ItemLuckyHorseshoe("diamond_horseshoe", HorseshoeLevel.DIAMOND));
	public static final ItemBase emeraldHorseshoe = register(new ItemLuckyHorseshoe("emerald_horseshoe", HorseshoeLevel.EMERALD));
	
	public static final ItemFoodBase witherBread = register(new ItemHealthFood("wither_bread", 0, 20, 5, 15, false));
	public static final ItemFoodBase enderBread = register(new ItemHealthFood("ender_bread", 20, 20, 10, 30, false));
	
	public static final ItemBase soulbinder = register((ItemBase) new ItemBase("soul_binder").setMaxStackSize(1));
	public static final ItemBase soulFragment = register(new ItemBase("soul_fragment"));
	public static final ItemBase unfiredSoulBook = register((ItemBase) new ItemBase("unfired_soul_book").setMaxStackSize(1));
	public static final ItemBase firedSoulBook = register((ItemBase) new ItemBase("fired_soul_book").setMaxStackSize(1));
	
	public static final ItemBase tool = notregister(new ItemShapeShiftingToolBag("shape_shifting_tool_bag"));
	public static final ItemBase teleporter = notregister(new ItemTeleporter("teleporter"));
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		r.registerAll(items.toArray(new Item[0]));
	}
	
	private static ItemBase register(ItemBase input) {
		items.add(input);
		return input;
	}
	
	private static ItemFoodBase register(ItemFoodBase input) {
		items.add(input);
		return input;
	}
	
	private static ItemBase notregister(ItemBase input) {
		return input;
	}
}
