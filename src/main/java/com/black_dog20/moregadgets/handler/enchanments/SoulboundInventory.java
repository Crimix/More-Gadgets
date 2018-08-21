package com.black_dog20.moregadgets.handler.enchanments;

import com.black_dog20.moregadgets.intergration.galacticraft.GalacticraftIntergration;
import com.black_dog20.moregadgets.reference.NBTTags;
import com.black_dog20.moregadgets.utility.NBTHelper;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;

public class SoulboundInventory {

	public final NonNullList<ItemStack> mainInventory;
	public final NonNullList<ItemStack> armorInventory;
	public final NonNullList<ItemStack> offHandInventory;
	public final NonNullList<ItemStack> baublesInventory;
	public final NonNullList<ItemStack> galacticraftInventory;
	public final EntityPlayer player;

	private SoulboundInventory(EntityPlayer player, boolean load) {
		this.player = player;
		this.mainInventory = NonNullList.<ItemStack>withSize(player.inventory.mainInventory.size(), ItemStack.EMPTY);
		this.armorInventory = NonNullList.<ItemStack>withSize(player.inventory.armorInventory.size(), ItemStack.EMPTY);
		this.offHandInventory = NonNullList.<ItemStack>withSize(player.inventory.offHandInventory.size(), ItemStack.EMPTY);
		
		if(Loader.isModLoaded("baubles")) {
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			this.baublesInventory = NonNullList.<ItemStack>withSize(baubles.getSlots(), ItemStack.EMPTY);
		} else {
			this.baublesInventory = NonNullList.<ItemStack>withSize(1,ItemStack.EMPTY);
		}
		
		IInventory galacticraft = GalacticraftIntergration.getInventory(player);
		if(galacticraft != null) {
			this.galacticraftInventory = NonNullList.<ItemStack>withSize(galacticraft.getSizeInventory(), ItemStack.EMPTY);
		} else {
			this.galacticraftInventory = NonNullList.<ItemStack>withSize(1,ItemStack.EMPTY);
		}
		
		if(load) {
			readFromNBT();
		}
		else {
			copyMain();
			copyArmor();
			copyOffHand();
			if(Loader.isModLoaded("baubles"))
				copyBaubles();
			if(galacticraft != null)
				copyGalacticraft();
		}
	}

	public SoulboundInventory(EntityPlayer player) {
		this(player, false);
	}

	public static SoulboundInventory GetForPlayer(EntityPlayer player) {
		return new SoulboundInventory(player, true);
	}

	private void copyMain() {
		NonNullList<ItemStack> old = player.inventory.mainInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTHelper.doesItemStackHaveTag(old.get(i), NBTTags.SOULBOUND)) {
				this.mainInventory.set(i, old.get(i).copy());
				old.get(i).setCount(0);
			}
		}
	}

	private void copyArmor() {
		NonNullList<ItemStack> old = player.inventory.armorInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTHelper.doesItemStackHaveTag(old.get(i), NBTTags.SOULBOUND)) {
				this.armorInventory.set(i, old.get(i).copy());
				old.get(i).setCount(0);
			}
		}
	}

	private void copyOffHand() {
		NonNullList<ItemStack> old = player.inventory.offHandInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTHelper.doesItemStackHaveTag(old.get(i), NBTTags.SOULBOUND)) {
				this.offHandInventory.set(i, old.get(i));
				old.get(i).setCount(0);
			}
		}
	}

	private void copyBaubles() {
		IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
		for(int i = 0; i < baubles.getSlots(); i++) {
			if(NBTHelper.doesItemStackHaveTag(baubles.getStackInSlot(i), NBTTags.SOULBOUND)) {
				baublesInventory.set(i, baubles.getStackInSlot(i).copy());
				baubles.setStackInSlot(i, ItemStack.EMPTY);
			}
		}
	}
	
	private void copyGalacticraft() {
		IInventory galacticraft = GalacticraftIntergration.getInventory(player);
		for(int i = 0; i < galacticraft.getSizeInventory(); i++) {
			if(NBTHelper.doesItemStackHaveTag(galacticraft.getStackInSlot(i), NBTTags.SOULBOUND)) {
				galacticraftInventory.set(i, galacticraft.removeStackFromSlot(i));
			}
		}
	}

	public void writeToNBT()
	{
		NBTTagList nbtTagListIn = new NBTTagList();
		for (int i = 0; i < this.mainInventory.size(); ++i)
		{
			if (!((ItemStack)this.mainInventory.get(i)).isEmpty())
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				((ItemStack)this.mainInventory.get(i)).writeToNBT(nbttagcompound);
				nbtTagListIn.appendTag(nbttagcompound);
			}
		}

		for (int j = 0; j < this.armorInventory.size(); ++j)
		{
			if (!((ItemStack)this.armorInventory.get(j)).isEmpty())
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)(j + 100));
				((ItemStack)this.armorInventory.get(j)).writeToNBT(nbttagcompound1);
				nbtTagListIn.appendTag(nbttagcompound1);
			}
		}

		for (int k = 0; k < this.offHandInventory.size(); ++k)
		{
			if (!((ItemStack)this.offHandInventory.get(k)).isEmpty())
			{
				NBTTagCompound nbttagcompound2 = new NBTTagCompound();
				nbttagcompound2.setByte("Slot", (byte)(k + 150));
				((ItemStack)this.offHandInventory.get(k)).writeToNBT(nbttagcompound2);
				nbtTagListIn.appendTag(nbttagcompound2);
			}
		}

		NBTTagList baublesList = new NBTTagList();

		for (int l = 0; l < this.baublesInventory.size(); ++l)
		{
			if (!((ItemStack)this.baublesInventory.get(l)).isEmpty())
			{
				NBTTagCompound nbttagcompound2 = new NBTTagCompound();
				nbttagcompound2.setByte("Slot", (byte)(l));
				((ItemStack)this.baublesInventory.get(l)).writeToNBT(nbttagcompound2);
				baublesList.appendTag(nbttagcompound2);
			}
		}

		NBTTagList galacticraftList = new NBTTagList();

		for (int l = 0; l < this.galacticraftInventory.size(); ++l)
		{
			if (!((ItemStack)this.galacticraftInventory.get(l)).isEmpty())
			{
				NBTTagCompound nbttagcompound2 = new NBTTagCompound();
				nbttagcompound2.setByte("Slot", (byte)(l));
				((ItemStack)this.galacticraftInventory.get(l)).writeToNBT(nbttagcompound2);
				galacticraftList.appendTag(nbttagcompound2);
			}
		}

		NBTHelper.getPlayerNBT(player).setTag(NBTTags.SOUL_INVENTORY, nbtTagListIn);
		NBTHelper.getPlayerNBT(player).setTag(NBTTags.SOUL_INVENTORY + "_baubles", baublesList);
		NBTHelper.getPlayerNBT(player).setTag(NBTTags.SOUL_INVENTORY + "_galacticraft", galacticraftList);
	}

	public void readFromNBT()
	{
		this.mainInventory.clear();
		this.armorInventory.clear();
		this.offHandInventory.clear();
		this.baublesInventory.clear();

		NBTTagList nbtTagListIn = (NBTTagList) NBTHelper.getPlayerNBT(player).getTag(NBTTags.SOUL_INVENTORY);
		if(nbtTagListIn != null) {

			for (int i = 0; i < nbtTagListIn.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound = nbtTagListIn.getCompoundTagAt(i);
				int j = nbttagcompound.getByte("Slot") & 255;
				ItemStack itemstack = new ItemStack(nbttagcompound);

				if (!itemstack.isEmpty())
				{
					if (j >= 0 && j < this.mainInventory.size())
					{
						this.mainInventory.set(j, itemstack);
					}
					else if (j >= 100 && j < this.armorInventory.size() + 100)
					{
						this.armorInventory.set(j - 100, itemstack);
					}
					else if (j >= 150 && j < this.offHandInventory.size() + 150)
					{
						this.offHandInventory.set(j - 150, itemstack);
					}
				}
			}
		}

		NBTTagList babulesList = (NBTTagList) NBTHelper.getPlayerNBT(player).getTag(NBTTags.SOUL_INVENTORY + "_baubles");
		if(babulesList != null) {
			for (int i = 0; i < babulesList.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound = babulesList.getCompoundTagAt(i);
				int j = nbttagcompound.getByte("Slot") & 255;
				ItemStack itemstack = new ItemStack(nbttagcompound);

				if (!itemstack.isEmpty())
				{
					this.baublesInventory.set(j, itemstack);	
				}
			}
		}
		
		NBTTagList galacticraftList = (NBTTagList) NBTHelper.getPlayerNBT(player).getTag(NBTTags.SOUL_INVENTORY + "_galacticraft");
		if(galacticraftList != null) {
			for (int i = 0; i < galacticraftList.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound = galacticraftList.getCompoundTagAt(i);
				int j = nbttagcompound.getByte("Slot") & 255;
				ItemStack itemstack = new ItemStack(nbttagcompound);

				if (!itemstack.isEmpty())
				{
					this.galacticraftInventory.set(j, itemstack);	
				}
			}
		}

	}


	public void clear() {
		NBTHelper.getPlayerNBT(player).removeTag(NBTTags.SOUL_INVENTORY);
		NBTHelper.getPlayerNBT(player).removeTag(NBTTags.SOUL_INVENTORY + "_baubles");
		NBTHelper.getPlayerNBT(player).removeTag(NBTTags.SOUL_INVENTORY + "_galacticraft");
	}

	public boolean addItemStackToInventory(ItemStack stack)
	{
		int i = getFirstStackWhichCanMerge(stack);
		if(i == -1) {
			i = getFirstEmptyStack();
			if(i == -1)
				return false;
			else {
				mainInventory.set(i, stack);
				return true;
			}
		} else {
			mainInventory.set(i, stack);
			return true;
		}
	}

	public int getFirstEmptyStack()
	{
		for (int i = 0; i < this.mainInventory.size(); ++i)
		{
			if (((ItemStack)this.mainInventory.get(i)).isEmpty())
			{
				return i;
			}
		}

		return -1;
	}

	public int getFirstStackWhichCanMerge(ItemStack stack)
	{
		for (int i = 0; i < this.mainInventory.size(); ++i)
		{
			if (this.mainInventory.get(i).getItem() == stack.getItem() && this.mainInventory.get(i).getMaxStackSize() >= (this.mainInventory.get(i).getCount() + stack.getCount()))
			{
				return i;
			}
		}

		return -1;
	}

}
