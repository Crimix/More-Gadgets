package com.black_dog20.moregadgets.proxies;

import com.black_dog20.moregadgets.config.ModConfig;
import com.black_dog20.moregadgets.config.Server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerProxy extends CommonProxy {

	@Override
	public void registerPreInit() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Server getServerConfig(){
		return ModConfig.server;
	}
	
	@Override
	public EntityPlayer getPlayerFromMessageContext(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}

	@Override
	public EntityPlayer getPlayerByIDFromMessageContext(int id, MessageContext ctx) {
		return null;
	}

}
