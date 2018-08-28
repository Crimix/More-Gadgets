package com.black_dog20.moregadgets.init.recipehandlers;

import java.util.HashMap;
import java.util.Map.Entry;

import com.black_dog20.moregadgets.entity.EntityItemFire;
import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class FireRecipes {

	public static final HashMap<ItemStack, ItemStack> recipes = new HashMap<ItemStack, ItemStack>();
	
	public static void init() {
		recipes.put(new ItemStack(Items.DYE, 1, 4), new ItemStack(ModItems.blueDust));
		recipes.put(new ItemStack(ModItems.unfiredSoulBook), new ItemStack(ModItems.firedSoulBook));
	}
	
	public static boolean isRecipeInput(EntityItem e){
			for(Entry<ItemStack, ItemStack> r : recipes.entrySet()) {
				if(r.getKey().getItem() == e.getItem().getItem() && r.getKey().getMetadata() == e.getItem().getMetadata())
					return true;
			}
			return false;
	}
	
	public static ItemStack getResult(EntityItem e) {
		ItemStack result = ItemStack.EMPTY;
		for(Entry<ItemStack, ItemStack> r : recipes.entrySet()) {
			if(r.getKey().getItem() == e.getItem().getItem() && r.getKey().getMetadata() == e.getItem().getMetadata()) {
				result = r.getValue();
			}
		}
		if(result.getMaxStackSize() > 1)
			result.setCount(e.getItem().getCount());
		return result;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onEntityAdded(EntityJoinWorldEvent event) {
		if(event.getWorld().isRemote)
			return;
		
		final Entity entity = event.getEntity();
		if (entity instanceof EntityItem && !(entity instanceof EntityItemFire)) {
			EntityItem entityItem = (EntityItem) entity;
			ItemStack stack = entityItem.getItem();
			if (!stack.isEmpty() && isRecipeInput(entityItem)) {
				EntityItemFire newEntity = new EntityItemFire(entityItem);
				entityItem.setDead();
				
				int i = MathHelper.floor(newEntity.posX / 16.0D);
				int j = MathHelper.floor(newEntity.posZ / 16.0D);
				event.getWorld().getChunkFromChunkCoords(i, j).addEntity(newEntity);
				event.getWorld().loadedEntityList.add(newEntity);
				event.getWorld().onEntityAdded(newEntity);
				
				event.setCanceled(true);
			}
		}
	}
}
