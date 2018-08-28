package com.black_dog20.moregadgets.init;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.moregadgets.block.BlockBase;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModBlocks {

	public static final List<Block> blocks = new ArrayList<>();
	public static final List<ItemBlock> items = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();
		r.registerAll(blocks.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		for(ItemBlock ib : items) {
			ib.setRegistryName(ib.getBlock().getRegistryName());
			r.register(ib);
		}
	}
	
	private static BlockBase register(BlockBase input) {
		blocks.add(input);
		items.add(new ItemBlock(input));
		return input;
	}
	
}
