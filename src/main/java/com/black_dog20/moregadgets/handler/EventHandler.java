package com.black_dog20.moregadgets.handler;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventHandler {
	
	AttributeModifier healthModifier = new AttributeModifier("MoreGadgets", 4, 1);
	
	  @SubscribeEvent
	    public void setMaxHealthOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
	        IAttributeInstance health = event.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
	        health.removeModifier(healthModifier);
	        health.applyModifier(healthModifier);
	    }
}