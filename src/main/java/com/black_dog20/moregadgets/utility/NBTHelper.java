package com.black_dog20.moregadgets.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	public static NBTTagCompound getPlayerNBT(EntityPlayer player) {

		if (player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			NBTTagCompound nbt = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

			return nbt;
		} else if (!(player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))) {
			NBTTagCompound nbtT = new NBTTagCompound();
			player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, nbtT);
			NBTTagCompound nbt = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			return nbt;
		} else
			return null;
	}
	
	public static boolean doesItemStackHaveTag(ItemStack stack, String tag) {
		return stack.getTagCompound() != null && stack.getTagCompound().hasKey(tag);
	}

}
