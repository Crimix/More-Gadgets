package com.black_dog20.moregadgets.handler;

import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = Reference.MOD_ID)
public class ClientEventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onToolTipEvent(ItemTooltipEvent event) {
		if(event.getItemStack().getItem() == ModItems.soulFragment)
			event.getToolTip().add(I18n.format("tooltips.moregadgets:soulFragment.drop", I18n.format("enchantment.moregadgets.soul_rip")));
		else if(event.getItemStack().getItem() == ModItems.blueDust)
			event.getToolTip().add(I18n.format("tooltips.moregadgets:blue_dust.create", new ItemStack(Items.DYE, 1, 4).getDisplayName()));
		else if(event.getItemStack().getItem() == ModItems.unfiredSoulBook)
			event.getToolTip().add(I18n.format("tooltips.moregadgets:unfired_soul_book.drop", I18n.format("enchantment.moregadgets.soul_rip")));
	}
}
