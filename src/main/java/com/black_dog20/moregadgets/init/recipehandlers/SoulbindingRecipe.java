package com.black_dog20.moregadgets.init.recipehandlers;

import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.reference.NBTTags;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SoulbindingRecipe implements IRecipe {

	ResourceLocation registryName = new ResourceLocation(Reference.MOD_ID, "soulbinding");
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		ItemStack fragment = null;
		ItemStack target = null;

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);

			if (!stack.isEmpty())
			{
				if (stack.getItem() == ModItems.soulbinder)
				{
					if (fragment == null)
					{
						fragment = stack;
					}
					else
					{
						return false;
					}
				}
				else
				{
					if (target == null)
					{
							target = stack;
					}
					else
					{
						return false;
					}
				}
			}
		}
		return fragment != null && target != null && (!target.hasTagCompound() || !target.getTagCompound().hasKey(NBTTags.SOULBOUND));
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
	{
		inv.clear();
		return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
	}
	
	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return new ItemStack(ModItems.soulbinder);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack target = null;

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);

			if (!stack.isEmpty())
			{
				if (stack.getItem() != ModItems.soulbinder)
				{
					target = stack;
				}
			}
		}

		ItemStack result = target.copy();
		if (result.getTagCompound() == null)
		{
			result.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = result.getTagCompound();
		nbt.setBoolean(NBTTags.SOULBOUND, true);
		return result;
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return true;
	}

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		registryName = name;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return registryName;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return IRecipe.class;
	}

}
