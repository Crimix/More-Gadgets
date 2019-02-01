package com.black_dog20.moregadgets.proxies;

import java.lang.reflect.Field;

import com.black_dog20.moregadgets.MoreGadgets;
import com.black_dog20.moregadgets.client.gui.GuiHealth;
import com.black_dog20.moregadgets.config.ModConfig;
import com.black_dog20.moregadgets.config.Server;
import com.black_dog20.moregadgets.config.ServerConfig;
import com.black_dog20.moregadgets.handler.ClientEventHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerPreInit() {
		MinecraftForge.EVENT_BUS.register(new GuiHealth());
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}
	
	@Override
	public Server getServerConfig(){
		if(ServerConfig.onServer)
			return ServerConfig.server;
		else
			return ModConfig.server;
	}

	@Override
	public EntityPlayer getPlayerFromMessageContext(MessageContext ctx) {
		switch (ctx.side) {
		case CLIENT:
			EntityPlayer entityClientPlayerMP = Minecraft.getMinecraft().player;
			return entityClientPlayerMP;
		case SERVER:
			EntityPlayer entityPlayerMP = ctx.getServerHandler().player;
			return entityPlayerMP;
		}
		return null;
	}

	@Override
	public EntityPlayer getPlayerByIDFromMessageContext(int id, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			EntityPlayer entityClientPlayer = (EntityPlayer) Minecraft.getMinecraft().world.getEntityByID(id);
			return entityClientPlayer;
		}
		return null;
	}

}
