package com.black_dog20.moregadgets.proxies;

import com.black_dog20.moregadgets.config.Server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IProxy {

	void registerPreInit();

	Server getServerConfig();
	
	EntityPlayer getPlayerFromMessageContext(MessageContext ctx);
	
	EntityPlayer getPlayerByIDFromMessageContext(int id, MessageContext ctx);

}
