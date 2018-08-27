package com.black_dog20.moregadgets.storage;

import javax.annotation.Nonnull;

import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.reference.NBTTags;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class ShapeShiftingToolBagItemHandler extends ItemStackHandler {

	private final static int SLOTS = 4;
	private final static String[] toolArray = new String[]{ "sword", "pickaxe", "axe", "shovel" };
	private final ItemStack bag;

	public ShapeShiftingToolBagItemHandler(ItemStack bag) {
		super(SLOTS);
		this.bag = bag;
		this.read();
	}

	@Override
	protected void onContentsChanged(int slot)
	{
		this.write();
	}

	@Override
	@Nonnull
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
	{
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		validateSlotIndex(slot);

		if(!(stack.getItem().getToolClasses(stack).contains(toolArray[slot]) || stack.getItem() instanceof ItemSword && slot == 0))
			return stack;

		ItemStack result = super.insertItem(slot, stack, simulate);
		this.write();
		return result;
	}
	
	@Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

       ItemStack stack = super.extractItem(slot, amount, simulate);
       this.write();
       return stack;
    }

	public ItemStack findToolForClass(String toolClass) {
		if(toolClass.equals("sword")) {
			ItemStack temp = getStackInSlot(0).copy();
			return copyTo(temp);
		}

		for(ItemStack stack : stacks) {
			if(stack.getItem().getToolClasses(stack).contains(toolClass)) {
				ItemStack temp = stack.copy();
				return copyTo(temp);
			}
		}

		ItemStack temp = new ItemStack(ModItems.tool);
		return copyTo(temp);
	}

	public void updateStack(ItemStack stack) {
		if(stack.isEmpty())
			return;
		
		for(int i = 0; i < stacks.size(); i++)
			if(stacks.get(i).getItem() == stack.getItem()) {
				if(stack.getTagCompound() != null && stack.getTagCompound().hasKey(NBTTags.SHAPE_SHIFTING_ITEM_INVENTORY))
					stack.getTagCompound().removeTag(NBTTags.SHAPE_SHIFTING_ITEM_INVENTORY);
				if(stack.getTagCompound() != null && stack.getTagCompound().hasKey(NBTTags.SOULBOUND) && stack.getTagCompound().hasKey(NBTTags.SOULBOUND_TEMP)) {
					stack.getTagCompound().removeTag(NBTTags.SOULBOUND);
					stack.getTagCompound().removeTag(NBTTags.SOULBOUND_TEMP);
				}
				setStackInSlot(i, stack);
			}

		this.write();
	}
	
	public ItemStack removeStack(ItemStack stack) {
		for(int i = 0; i < stacks.size(); i++)
			if(stacks.get(i).getItem() == stack.getItem())
				setStackInSlot(i, ItemStack.EMPTY);
		this.write();
		
		return copyTo(new ItemStack(ModItems.tool));
	}

	public void read() {
		if(!bag.hasTagCompound())
			bag.setTagCompound(new NBTTagCompound());

		NBTTagCompound nbt = bag.getTagCompound();
		if(nbt.hasKey(NBTTags.SHAPE_SHIFTING_ITEM_INVENTORY))
			this.deserializeNBT(nbt.getCompoundTag(NBTTags.SHAPE_SHIFTING_ITEM_INVENTORY));
	}

	public NBTTagCompound write() {
		if(!bag.hasTagCompound())
			bag.setTagCompound(new NBTTagCompound());

		NBTTagCompound nbt = bag.getTagCompound();
		nbt.setTag(NBTTags.SHAPE_SHIFTING_ITEM_INVENTORY, this.serializeNBT());
		return nbt;
	}

	public ItemStack copyTo(ItemStack newStack) {
		if(!newStack.isEmpty()) {
			if(!newStack.hasTagCompound())
				newStack.setTagCompound(new NBTTagCompound());

			NBTTagCompound nbt = newStack.getTagCompound();
			nbt.setTag(NBTTags.SHAPE_SHIFTING_ITEM_INVENTORY, this.serializeNBT());
			if(bag.hasTagCompound() && bag.getTagCompound().hasKey(NBTTags.SOULBOUND) && bag.getTagCompound().hasKey(NBTTags.SOULBOUND_TEMP)) {
				nbt.setBoolean(NBTTags.SOULBOUND, true);
				if(newStack.getItem() != ModItems.tool)
					nbt.setBoolean(NBTTags.SOULBOUND_TEMP, true);
			}
			if(bag.getItem() == ModItems.tool && bag.hasTagCompound() && bag.getTagCompound().hasKey(NBTTags.SOULBOUND)) {
				nbt.setBoolean(NBTTags.SOULBOUND, true);
				nbt.setBoolean(NBTTags.SOULBOUND_TEMP, true);
			}
		} else {
			newStack = copyTo(new ItemStack(ModItems.tool));
		}

		return newStack;

	}
}
