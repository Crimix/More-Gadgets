package com.black_dog20.moregadgets.client.render;

import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MOD_ID)
public final class ModelHandler {
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent evt) {

		for(Block block : Block.REGISTRY) {
			if(block instanceof IItemModelRegister)
				((IItemModelRegister) block).initModel();
		}
		
		for(Item item : Item.REGISTRY) {
			if(item instanceof IItemModelRegister)
				((IItemModelRegister) item).initModel();
		}
		OBJLoader.INSTANCE.addDomain(Reference.MOD_ID.toLowerCase());
		for(Block block : Block.REGISTRY) {
			if(block instanceof ISpecialModel)
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
	}
	
	public static void registerBlockToState(Block b, int meta, ModelResourceLocation location) {
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(b),
				meta,
				location
				);
	}
	
}