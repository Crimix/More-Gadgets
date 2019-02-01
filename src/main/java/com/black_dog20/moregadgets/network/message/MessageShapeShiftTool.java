package com.black_dog20.moregadgets.network.message;

import com.black_dog20.moregadgets.item.gadgets.ItemShapeShiftingToolBag;
import com.black_dog20.moregadgets.storage.ShapeShiftingToolBagItemHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageShapeShiftTool implements IMessage, IMessageHandler<MessageShapeShiftTool, IMessage> {

	private boolean isEntityResult;
	private boolean isMis;
	private int id;
	private int x;
	private int y;
	private int z;
	private int slot;

	@Override
	public IMessage onMessage(MessageShapeShiftTool message, MessageContext context) {
		EntityPlayer player = context.getServerHandler().player;	
		if(ItemShapeShiftingToolBag.isTool(player.inventory.getStackInSlot(message.slot))) {
			ShapeShiftingToolBagItemHandler toolBag = new ShapeShiftingToolBagItemHandler(player.inventory.getStackInSlot(message.slot));
			toolBag.updateStack(player.inventory.getStackInSlot(message.slot).copy());
			ItemStack newStack = player.inventory.getStackInSlot(message.slot);
			if(!message.isEntityResult) {
				IBlockState state = player.getEntityWorld().getBlockState(new BlockPos(message.x,message.y,message.z));

				if(state == null)
					return null;

				if(state.getBlock().isToolEffective("pickaxe", state)) {
					newStack = toolBag.findToolForClass("pickaxe");
				} else if(state.getBlock().isToolEffective("shovel", state)) {
					newStack = toolBag.findToolForClass("shovel");
				} else if(state.getBlock().isToolEffective("axe", state)) {
					newStack = toolBag.findToolForClass("axe");
				} else {
					newStack = toolBag.findToolForClass("sword");
				}
			} else {
				newStack = toolBag.findToolForClass("sword");
			}
			if(newStack != player.inventory.getStackInSlot(message.slot) && !ItemStack.areItemsEqual(newStack, player.inventory.getStackInSlot(message.slot))) {
				player.inventory.setInventorySlotContents(message.slot, newStack);
				return null;
			}
		}
		return null;
	}

	public MessageShapeShiftTool() {}

	public MessageShapeShiftTool(int slot, RayTraceResult result) {
		if(result == null) {
			isMis = true;
			isEntityResult = false;
		} else {
			isMis = result.typeOfHit == Type.MISS;
			isEntityResult = result.typeOfHit == Type.ENTITY;
			this.slot = slot;
			if(isEntityResult)
				id = result.entityHit.getEntityId();
			else {
				x = result.getBlockPos().getX();
				y = result.getBlockPos().getY();
				z = result.getBlockPos().getZ();
			}
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isEntityResult);
		buf.writeBoolean(isMis);
		buf.writeInt(id);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(slot);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		isEntityResult = buf.readBoolean();
		isMis = buf.readBoolean();
		id = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		slot = buf.readInt();
	}
}
