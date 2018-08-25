package com.black_dog20.moregadgets.item.gadgets;

import com.black_dog20.moregadgets.MoreGadgets;
import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.item.ItemBase;
import com.black_dog20.moregadgets.network.PacketHandler;
import com.black_dog20.moregadgets.network.message.MessageShapeShiftTool;
import com.black_dog20.moregadgets.reference.NBTTags;
import com.black_dog20.moregadgets.storage.ShapeShiftingToolBagItemHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShapeShiftingToolBag extends ItemBase{

	public ItemShapeShiftingToolBag(String name) {
		super(name);
		this.setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		new ShapeShiftingToolBagItemHandler(stack);
	}

	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if(isTool(event.getItemStack()) && !event.getWorld().isRemote && event.getEntityPlayer().isSneaking()) {
			EntityPlayer player = event.getEntityPlayer();
			event.getEntityPlayer().openGui(MoreGadgets.instance, MoreGadgets.GUI_SHAPESHIFTER, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
	}

	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickItem event) {
		if(isTool(event.getItemStack()) && !event.getWorld().isRemote && event.getEntityPlayer().isSneaking()) {
			EntityPlayer player = event.getEntityPlayer();
			event.getEntityPlayer().openGui(MoreGadgets.instance, MoreGadgets.GUI_SHAPESHIFTER, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onToolTipEvent(ItemTooltipEvent event) {
		if(isTool(event.getItemStack())) {
			event.getToolTip().add(I18n.format("tooltips.moregadgets:shapeshifter"));
			event.getToolTip().add(I18n.format("tooltips.moregadgets:shapeshifter.use"));
		}
	}
	
	@SubscribeEvent
	public void onItemBroken(PlayerDestroyItemEvent event) {
		if(isTool(event.getOriginal())) {
			ItemStack stack = new ShapeShiftingToolBagItemHandler(event.getOriginal()).removeStack(event.getOriginal());
			event.getEntityPlayer().setHeldItem(EnumHand.MAIN_HAND, stack);
		}
	}
	

	private int count = 0;
	@SubscribeEvent
	public void onPlayerTick(LivingUpdateEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();

			if(isTool(player.getHeldItemMainhand()) && player.world.isRemote) {
				if(count % 10 == 0) {
					PacketHandler.network.sendToServer(new MessageShapeShiftTool(player.inventory.currentItem, Minecraft.getMinecraft().objectMouseOver));
					count = 0;
				}
				count++;
			}
		}
	}

	public static boolean isTool(ItemStack stack) {
		if(stack.isEmpty())
			return false;

		if(stack.getItem() == ModItems.tool)
			return true;

		return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTTags.SHAPE_SHIFTING_ITEM_INVENTORY);
	}

}
