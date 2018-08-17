package com.black_dog20.moregadgets.handler;

import com.black_dog20.moregadgets.item.ItemHealthFood;
import com.black_dog20.moregadgets.network.PacketHandler;
import com.black_dog20.moregadgets.network.message.MessageConfigSync;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class EventHandler {

	@SubscribeEvent
	public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
		if (!event.isWasDeath()) {
			return;
		}
		IAttributeInstance oldHealth = event.getOriginal().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		IAttributeInstance health = event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

		AttributeModifier oldHealthModifier = oldHealth.getModifier(ItemHealthFood.MORE_GADGETS_HEALTH);
		if(oldHealthModifier != null) {
			health.removeModifier(ItemHealthFood.MORE_GADGETS_HEALTH);
			health.applyModifier(oldHealthModifier);
		}
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event){
		if(!event.player.world.isRemote){
			PacketHandler.network.sendTo(new MessageConfigSync(), (EntityPlayerMP) event.player);
		}
	}
}