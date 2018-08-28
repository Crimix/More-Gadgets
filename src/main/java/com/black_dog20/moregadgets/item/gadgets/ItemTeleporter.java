package com.black_dog20.moregadgets.item.gadgets;

import com.black_dog20.moregadgets.MoreGadgets;
import com.black_dog20.moregadgets.item.ItemBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemTeleporter extends ItemBase{

	public ItemTeleporter(String name) {
		super(name);
		this.setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.openGui(MoreGadgets.instance, MoreGadgets.GUI_TELEPORT, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
