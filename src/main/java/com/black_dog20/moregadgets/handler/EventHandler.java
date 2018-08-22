package com.black_dog20.moregadgets.handler;

import com.black_dog20.moregadgets.network.PacketHandler;
import com.black_dog20.moregadgets.network.message.MessageConfigSync;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class EventHandler {

	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event){
		if(!event.player.world.isRemote){
			PacketHandler.network.sendTo(new MessageConfigSync(), (EntityPlayerMP) event.player);
		}
	}
}