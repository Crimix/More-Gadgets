package com.black_dog20.moregadgets.network.message;

import com.black_dog20.moregadgets.MoreGadgets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageOpenGuiOnServer implements IMessage, IMessageHandler<MessageOpenGuiOnServer, IMessage> {

	private int id = -1;
	private int x;
	private int y;
	private int z;
	
	@Override
	public IMessage onMessage(MessageOpenGuiOnServer message, MessageContext context) {
		EntityPlayer player = context.getServerHandler().player;
		player.getServer().addScheduledTask(() -> {
			player.openGui(MoreGadgets.instance, message.id, player.world, message.x, message.y, message.z);
		});
		return null;
	}

	public MessageOpenGuiOnServer() {}
	
	public MessageOpenGuiOnServer(int id, EntityPlayer player) { 
		this.id = id;
		x = (int) player.posX;
		y = (int) player.posY;
		z = (int) player.posZ;
	}
	
	public MessageOpenGuiOnServer(int id, BlockPos pos) { 
		this.id = id;
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}
}
