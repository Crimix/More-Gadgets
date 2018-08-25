package com.black_dog20.moregadgets.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageShapeShiftToolClient implements IMessage, IMessageHandler<MessageShapeShiftToolClient, IMessage> {

	
	@Override
	public IMessage onMessage(MessageShapeShiftToolClient message, MessageContext context) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			Minecraft.getMinecraft().entityRenderer.itemRenderer.resetEquippedProgress(EnumHand.MAIN_HAND);
			Minecraft.getMinecraft().getItemRenderer().resetEquippedProgress(EnumHand.MAIN_HAND);
		});
		return null;
	}

	public MessageShapeShiftToolClient() {}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}
}
