package com.black_dog20.moregadgets.network.message;


import com.black_dog20.moregadgets.config.ModConfig;
import com.black_dog20.moregadgets.config.ServerConfig;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageConfigSync implements IMessage, IMessageHandler<MessageConfigSync, IMessage> {

	@Override
	public IMessage onMessage(MessageConfigSync message, MessageContext context) {

		return null;
	}

	public MessageConfigSync() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(ModConfig.server.percentageDropSoulPiece);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		ServerConfig.onServer = true;
		ServerConfig.server.percentageDropSoulPiece = buf.readInt();
	}
}
