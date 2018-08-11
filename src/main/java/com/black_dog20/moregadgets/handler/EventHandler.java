package com.black_dog20.moregadgets.handler;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventHandler {

	  @SubscribeEvent
	    public void setMaxHealthOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
	        IAttributeInstance health = event.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
	        //health.removeModifier(new AttributeModifier("NanoTech", 1, 1));
	        health.applyModifier(new AttributeModifier("NanoTech", 1, 1));
	    }
}