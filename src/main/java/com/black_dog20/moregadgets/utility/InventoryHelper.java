package com.black_dog20.moregadgets.utility;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryHelper {

	
	public static ItemStack findItemStackOfType(EntityPlayer player, Item type) {
		return findItemStackOfType(player, new ItemStack(type));
	}
	
	public static ItemStack findItemStackOfType(EntityPlayer player, ItemStack type) {
		ItemStack copy = type.copy();
		copy.setItemDamage(OreDictionary.WILDCARD_VALUE);
		int i = player.inventory.getSlotFor(copy);
		if(i == -1)
			return ItemStack.EMPTY;
		return player.inventory.getStackInSlot(i);
	}
	
	public static boolean hasItem(EntityPlayer player, Item type) {
		return hasItemStack(player, new ItemStack(type));
	}
	
	public static boolean hasItemStack(EntityPlayer player, ItemStack type) {
		ItemStack copy = type.copy();
		copy.setItemDamage(OreDictionary.WILDCARD_VALUE);
		List<NonNullList<ItemStack>> allInventories =  Arrays.<NonNullList<ItemStack>>asList(player.inventory.mainInventory, player.inventory.armorInventory, player.inventory.offHandInventory);
        label23:
        	
        	
        for (List<ItemStack> list : allInventories)
        {
            Iterator<ItemStack> iterator = list.iterator();

            while (true)
            {
                if (!iterator.hasNext())
                {
                    continue label23;
                }

                ItemStack itemstack = (ItemStack)iterator.next();

                if (!itemstack.isEmpty() && itemstack.isItemEqualIgnoreDurability(copy))
                {
                    break;
                }
            }

            return true;
        }

        return false;
	}
	
	public static int countItem(EntityPlayer player, Item type) {
		return countItemStack(player, new ItemStack(type));
	}
	
	public static int countItemStack(EntityPlayer player, ItemStack type) {
		ItemStack copy = type.copy();
		copy.setItemDamage(OreDictionary.WILDCARD_VALUE);
		List<NonNullList<ItemStack>> allInventories =  Arrays.<NonNullList<ItemStack>>asList(player.inventory.mainInventory, player.inventory.armorInventory, player.inventory.offHandInventory);
        int count = 0;
		label23:
        	
        	
        for (List<ItemStack> list : allInventories)
        {
            Iterator<ItemStack> iterator = list.iterator();

            while (true)
            {
                if (!iterator.hasNext())
                {
                    continue label23;
                }

                ItemStack itemstack = (ItemStack)iterator.next();

                if (!itemstack.isEmpty() && itemstack.isItemEqualIgnoreDurability(copy))
                {
                	count++;
                    break;
                }
            }
        }

        return count;
	}
	
}
