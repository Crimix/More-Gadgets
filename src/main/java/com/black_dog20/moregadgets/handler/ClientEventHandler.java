package com.black_dog20.moregadgets.handler;

import com.black_dog20.moregadgets.init.ModEnchantments;
import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.utility.Helper;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onToolTipEvent(ItemTooltipEvent event) {
		if(event.getItemStack().getItem() == ModItems.soulFragment)
			event.getToolTip().add(I18n.format("tooltips.moregadgets:soulFragment.drop", I18n.format("enchantment.moregadgets_soul_rip")));
		else if(event.getItemStack().getItem() == ModItems.blueDust)
			event.getToolTip().add(I18n.format("tooltips.moregadgets:blue_dust.create", new ItemStack(Items.DYE, 1, 4).getDisplayName()));
		else if(event.getItemStack().getItem() == ModItems.firedSoulBook)
			event.getToolTip().add(I18n.format("tooltips.moregadgets:blue_dust.create", new ItemStack(ModItems.unfiredSoulBook).getDisplayName()));
		else if(event.getItemStack().getItem() == ModItems.soulbinder)
			event.getToolTip().add(I18n.format("tooltips.moregadgets:soul_binder.use"));
		else if(Helper.shouldItemHaveEnchantmentButDoesNot(event.getItemStack(), ModEnchantments.soulRipEnchantment, 1))
			event.getToolTip().add(I18n.format("tooltips.moregadgets:enchantment_wrong", I18n.format("enchantment.moregadgets_soul_rip")));
	}
}
