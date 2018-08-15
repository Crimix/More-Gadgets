package com.black_dog20.moregadgets.handler;

import com.black_dog20.moregadgets.reference.NBTTags;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class ClientEventHandler {
	
	@SubscribeEvent
	public static void onToolTipEvent(ItemTooltipEvent event) {
		if(event.getItemStack().getTagCompound() == null || !event.getItemStack().getTagCompound().hasKey(NBTTags.SOULBOUND))
			return;
		event.getToolTip().add(I18n.format("toolips.moregadgets:items.soulbound"));
	}
}
