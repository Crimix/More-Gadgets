package com.black_dog20.moregadgets.init.recipehandlers;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.moregadgets.entity.EntityItemFire;
import com.black_dog20.moregadgets.handler.enchanments.SoulRipEnchantment;
import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class FireRecipes {

	public static final List<FireRecipe> recipes = new ArrayList<>();
	
	public static void init() {
		recipes.add(new FireRecipe(new ItemStack(Items.DYE, 1, 4), new ItemStack(ModItems.blueDust)));
		recipes.add(new FireRecipe(new ItemStack(ModItems.unfiredSoulBook), ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(SoulRipEnchantment.soulRipEnchantment, 1))));
	}
	
	public static boolean isRecipeInput(EntityItem e){
			for(FireRecipe r : recipes) {
				if(r.sameAsEntityItem(e))
					return true;
			}
			return false;
	}
	
	public static ItemStack getResult(EntityItem e) {
		ItemStack result = ItemStack.EMPTY;
		for(FireRecipe r : recipes) {
			if(r.sameAsEntityItem(e))
				result = r.getResult();
		}
		result.setCount(e.getItem().getCount());
		return result;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onEntityAdded(EntityJoinWorldEvent event) {

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
	
	public static class FireRecipe {
		
		private ItemStack input;
		private ItemStack result;
		
		public FireRecipe(ItemStack a, ItemStack b) {
			this.input = a;
			this.result = b;
		}
		
		public ItemStack getInput() {
			return input;
		}
		
		public ItemStack getResult() {
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof FireRecipe))
				return false;
			
			FireRecipe other = (FireRecipe) obj;
			return this.input.getItem() == other.getInput().getItem() && this.input.getMetadata() == other.getInput().getMetadata();
		}
		
		public boolean sameAsEntityItem(EntityItem obj) {
			return this.input.getItem() == obj.getItem().getItem() && this.input.getMetadata() == obj.getItem().getMetadata();
		}
	}
}
