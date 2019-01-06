package com.black_dog20.moregadgets.item.gadgets;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import org.lwjgl.input.Keyboard;

import com.black_dog20.moregadgets.MoreGadgets;
import com.black_dog20.moregadgets.client.gui.GuiShapeShifterToolBag;
import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.item.ItemBase;
import com.black_dog20.moregadgets.network.PacketHandler;
import com.black_dog20.moregadgets.network.message.MessageOpenGuiOnServer;
import com.black_dog20.moregadgets.network.message.MessageShapeShiftTool;
import com.black_dog20.moregadgets.reference.NBTTags;
import com.black_dog20.moregadgets.storage.ShapeShiftingToolBagItemHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if(isTool(event.getItemStack()) && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))) {
			EntityPlayer player = event.getEntityPlayer();
			PacketHandler.network.sendToServer(new MessageOpenGuiOnServer(MoreGadgets.GUI_SHAPESHIFTER, player));
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickItem event) {
		if(isTool(event.getItemStack()) && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))) {
			EntityPlayer player = event.getEntityPlayer();
			PacketHandler.network.sendToServer(new MessageOpenGuiOnServer(MoreGadgets.GUI_SHAPESHIFTER, player));
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onToolTipEvent(ItemTooltipEvent event) {
		if(isTool(event.getItemStack())) {
			event.getToolTip().add(I18n.format("tooltips.moregadgets:shapeshifter"));
			event.getToolTip().add(I18n.format("tooltips.moregadgets:shapeshifter.use", Keyboard.getKeyName(Keyboard.KEY_LCONTROL)));
		}
	}

	@SubscribeEvent
	public void onItemBroken(PlayerDestroyItemEvent event) {
		if(isTool(event.getOriginal())) {
			ItemStack stack = new ShapeShiftingToolBagItemHandler(event.getOriginal()).removeStack(event.getOriginal());
			if(!event.getEntityPlayer().inventory.addItemStackToInventory(stack)) {
				tryToSpawnEntityItemAtPlayer(event.getEntityPlayer(),stack);
			}
		}
	}


	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(LivingUpdateEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();

			if(isTool(player.getHeldItemMainhand())) {
				if(Minecraft.getMinecraft().currentScreen instanceof GuiShapeShifterToolBag)
					return;
				NBTTagCompound nbt = player.getHeldItemMainhand().getTagCompound();
				int count = nbt.getInteger("count");
				if(count % 10 == 0) {
					PacketHandler.network.sendToServer(new MessageShapeShiftTool(player.inventory.currentItem, Minecraft.getMinecraft().objectMouseOver));
					nbt.setInteger("count", 0);
				}

				nbt.setInteger("count", count++);
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTickShapeshiftingNoEquipAnimation(LivingUpdateEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {		

			EntityPlayer player = (EntityPlayer) event.getEntity();
			ItemRenderer IR = Minecraft.getMinecraft().entityRenderer.itemRenderer;
			if(IR!=null)
				try{

					ItemStack prevItem = ItemStack.EMPTY;
					ItemStack currentItem = player.getHeldItemMainhand();

					{
						Field field = ItemRenderer.class.getDeclaredField("itemStackMainHand");
						field.setAccessible(true);
						prevItem = (ItemStack) field.get(IR);
					}

					if(isTool(prevItem) && isTool(currentItem)) {
						float a1=1F;
						{
							Field field = ItemRenderer.class.getDeclaredField("equippedProgressMainHand");
							field.setAccessible(true);
							field.setFloat(IR,a1);
						}
						{
							Field field = ItemRenderer.class.getDeclaredField("prevEquippedProgressMainHand");
							field.setAccessible(true);
							field.setFloat(IR,a1);
						}
						{
							Field field = ItemRenderer.class.getDeclaredField("itemStackMainHand");
							field.setAccessible(true);
							field.set(IR, player.getHeldItemMainhand());
						}
					}
				}catch(Exception e){
					e.printStackTrace();
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

	private static boolean tryToSpawnEntityItemAtPlayer(EntityPlayer entityPlayer, @Nonnull ItemStack stack) {
		if (entityPlayer == null) 
			return false;
		EntityItem item = new EntityItem(entityPlayer.world, entityPlayer.posX, entityPlayer.posY + 0.5, entityPlayer.posZ, stack);
		item.setPickupDelay(40);
		item.lifespan *= 5;
		item.motionX = 0;
		item.motionZ = 0;
		entityPlayer.world.spawnEntity(item);
		return true;
	}

}
