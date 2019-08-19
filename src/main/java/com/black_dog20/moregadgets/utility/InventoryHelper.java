package com.black_dog20.moregadgets.utility;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.black_dog20.moregadgets.MoreGadgets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryHelper {

	
	public static ItemStack findItemStackOfType(EntityPlayer player, Item type) {
		return findItemStackOfType(player, new ItemStack(type));
	}
	
	public static ItemStack findItemStackOfType(EntityPlayer player, ItemStack type) {
		ItemStack copy = type.copy();
		copy.setItemDamage(OreDictionary.WILDCARD_VALUE);
		int i = getSlotFor(copy, player.inventory);
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
		Field f5 = ReflectionHelper.findField(InventoryPlayer.class, "allInventories", "field_184440_g");
		
		try {
			@SuppressWarnings("unchecked") 
			List<NonNullList<ItemStack>> allInventories = (List<NonNullList<ItemStack>>) f5.get(player.inventory);
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
		} catch (IllegalArgumentException | IllegalAccessException e) {
			MoreGadgets.logger.error("Something went wrong in reflecting in InventoryHelper.hasItemStack" + e);
			e.printStackTrace();
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
	
    public static int getSlotFor(ItemStack stack, InventoryPlayer ip)
    {
        for (int i = 0; i < ip.mainInventory.size(); ++i)
        {
            if (!((ItemStack)ip.mainInventory.get(i)).isEmpty() && stackEqualExact(stack, ip.mainInventory.get(i)))
            {
                return i;
            }
        }

        return -1;
    }
    
    private static boolean stackEqualExact(ItemStack stack1, ItemStack stack2)
    {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
	
}
