package com.black_dog20.moregadgets.creativetab;

import java.util.Comparator;

import com.black_dog20.moregadgets.init.ModBlocks;
import com.black_dog20.moregadgets.init.ModEnchantments;
import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CreativeTab{
	
	public static final CreativeTabs TAB = (new CreativeTabs(Reference.MOD_ID.toLowerCase()) {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.ironHorseshoe);
		}

		@Override
		public String getTranslatedTabLabel() {
			return Reference.MOD_NAME;
		}
		
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
			super.displayAllRelevantItems(list);
			list.sort(Comparator.comparing(i -> ModBlocks.items.indexOf(i.getItem())));
			list.sort(Comparator.comparing(i -> ModItems.items.indexOf(i.getItem())));
			list.add(ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(ModEnchantments.soulRipEnchantment, 1)));
		}
		
	});
}
