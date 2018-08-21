package com.black_dog20.moregadgets.handler.enchanments;

import java.util.ListIterator;

import javax.annotation.Nonnull;

import com.black_dog20.moregadgets.reference.NBTTags;
import com.black_dog20.moregadgets.reference.Reference;
import com.black_dog20.moregadgets.utility.NBTHelper;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * Class inspired heavily by what EnderIO is doing
 * https://github.com/SleepyTrousers/EnderIO/blob/732b7cd3c85e1913e5b8603437914af923ea7c5c/enderio-base/src/main/java/crazypants/enderio/base/enchantment/HandlerSoulBound.java
 * The events are based on the findings from EnderIO in regards to when items are dropped.
 * The way I am handling the saving of soulbound items is different to EnderIO and also it retains the slot location if possible.
 */
@EventBusSubscriber(modid = Reference.MOD_ID)
public class SoulBoundHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onToolTipEvent(ItemTooltipEvent event) {
		if(event.getItemStack().getTagCompound() == null || !event.getItemStack().getTagCompound().hasKey(NBTTags.SOULBOUND))
			return;
		int place = event.getToolTip().indexOf("");
		if(place != -1)
			event.getToolTip().add(place, I18n.format("tooltips.moregadgets:items.soulbound"));
		else
			event.getToolTip().add(I18n.format("tooltips.moregadgets:items.soulbound"));
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public static void onPlayerDeath(LivingDeathEvent event) {
		if(event.getEntity() != null && event.getEntity() instanceof EntityPlayer && !(event.getEntity() instanceof FakePlayer)) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (player.world.getGameRules().getBoolean("keepInventory"))
				return;
			if(event.isCanceled())
				return;
			SoulboundInventory soul = new SoulboundInventory(player);
			if(event.isCanceled())
				return;
			soul.writeToNBT();
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerDrop(PlayerDropsEvent event) {
		if (event.getEntityPlayer() == null || event.getEntityPlayer() instanceof FakePlayer || event.isCanceled()) {
			return;
		}
		EntityPlayer player = event.getEntityPlayer();
		
		if (player.world.getGameRules().getBoolean("keepInventory")) {
			return;
		}

		ListIterator<EntityItem> iter = event.getDrops().listIterator();
		while (iter.hasNext()) {
			EntityItem ei = iter.next();
			ItemStack item = ei.getItem();
			if (isStackSoulBound(item)) {
				iter.remove();
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerDropLate(PlayerDropsEvent event) {
		if (event.getEntityPlayer() == null || event.getEntityPlayer() instanceof FakePlayer || event.isCanceled()) {
			return;
		}
		EntityPlayer player = event.getEntityPlayer();
		
		if (player.world.getGameRules().getBoolean("keepInventory")) {
			return;
		}

		SoulboundInventory soul = SoulboundInventory.GetForPlayer(player);
		ListIterator<EntityItem> iter = event.getDrops().listIterator();
		while (iter.hasNext()) {
			EntityItem ei = iter.next();
			ItemStack item = ei.getItem();
			if (isStackSoulBound(item) && soul.addItemStackToInventory(item)) {
				iter.remove();
			}
		}
	}


	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerSpawn(PlayerEvent.Clone event) {
		if (!event.isWasDeath() || event.isCanceled())
			return;
		
		if (event.getOriginal() == null || event.getEntityPlayer() == null || event.getEntityPlayer() instanceof FakePlayer) 
			return;
		
		EntityPlayer player = event.getEntityPlayer();
		EntityPlayer original = event.getOriginal();
		
		if (player.world.getGameRules().getBoolean("keepInventory"))
			return;

		if (original == player || original.inventory == player.inventory || (original.inventory.armorInventory == player.inventory.armorInventory && original.inventory.mainInventory == player.inventory.mainInventory))
			return;

		SoulboundInventory soul = new SoulboundInventory(player);
		soul.readFromNBT();

		for (int i = 0; i < soul.armorInventory.size(); i++) {
			ItemStack item = soul.armorInventory.get(i);
			if (player.inventory.armorInventory.get(i).isEmpty()) {
				player.inventory.armorInventory.set(i, item);
				soul.armorInventory.set(i, ItemStack.EMPTY);
			}
			else {
				if(player.inventory.addItemStackToInventory(item))
					soul.armorInventory.set(i, ItemStack.EMPTY);
			}
		}

		for (int i = 0; i < soul.mainInventory.size(); i++) {
			ItemStack item = soul.mainInventory.get(i);
			if (addStackToPlayerInventory(player, i, item)) {
				soul.mainInventory.set(i, ItemStack.EMPTY);
			}
			else {
				if(player.inventory.addItemStackToInventory(item))
					soul.mainInventory.set(i, ItemStack.EMPTY);
			}
		}
		
		for (int i = 0; i < soul.offHandInventory.size(); i++) {
			ItemStack item = soul.offHandInventory.get(i);
			if (player.inventory.offHandInventory.get(i).isEmpty()) {
				player.inventory.offHandInventory.set(i, item);
				soul.offHandInventory.set(i, ItemStack.EMPTY);
			}
			else {
				if(player.inventory.addItemStackToInventory(item))
					soul.offHandInventory.set(i, ItemStack.EMPTY);
			}
		}
		
		soul.writeToNBT();
		
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerSpawnLate(PlayerEvent.Clone event) {
		if (!event.isWasDeath() || event.isCanceled())
			return;
		
		if (event.getOriginal() == null || event.getEntityPlayer() == null || event.getEntityPlayer() instanceof FakePlayer) 
			return;
		
		EntityPlayer player = event.getEntityPlayer();
		EntityPlayer original = event.getOriginal();
		if (player.world.getGameRules().getBoolean("keepInventory"))
			return;

		if (original == player || original.inventory == player.inventory || (original.inventory.armorInventory == player.inventory.armorInventory && original.inventory.mainInventory == player.inventory.mainInventory))
			return;

		SoulboundInventory soul = new SoulboundInventory(player);
		soul.readFromNBT();

		for (int i = 0; i < soul.armorInventory.size(); i++) {
			ItemStack item = soul.armorInventory.get(i);
			if (player.inventory.armorInventory.get(i).isEmpty()) {
				player.inventory.armorInventory.set(i, item);
				soul.armorInventory.set(i, ItemStack.EMPTY);
			}
			else {
				if(player.inventory.addItemStackToInventory(item) || tryToSpawnEntityItemAtPlayer(original, item))
					soul.armorInventory.set(i, ItemStack.EMPTY);
			}
		}

		for (int i = 0; i < soul.mainInventory.size(); i++) {
			ItemStack item = soul.mainInventory.get(i);
			if (addStackToPlayerInventory(player, i, item)) {
				soul.mainInventory.set(i, ItemStack.EMPTY);
			}
			else {
				if(player.inventory.addItemStackToInventory(item) || tryToSpawnEntityItemAtPlayer(original, item))
					soul.mainInventory.set(i, ItemStack.EMPTY);
			}
		}
		
		for (int i = 0; i < soul.offHandInventory.size(); i++) {
			ItemStack item = soul.offHandInventory.get(i);
			if (player.inventory.offHandInventory.get(i).isEmpty()) {
				player.inventory.offHandInventory.set(i, item);
				soul.offHandInventory.set(i, ItemStack.EMPTY);
			}
			else {
				if(player.inventory.addItemStackToInventory(item) || tryToSpawnEntityItemAtPlayer(original, item))
					soul.offHandInventory.set(i, ItemStack.EMPTY);
			}
		}
		
		
		/* Needs to be done here, else it will not sync a.k.a the items are destroyed */
		if(Loader.isModLoaded("baubles")) {
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for (int i = 0; i < soul.baublesInventory.size(); i++) {
				ItemStack item = soul.baublesInventory.get(i);
				ItemStack rest = baubles.insertItem(i, item, false);
				if (rest == ItemStack.EMPTY) {
					soul.baublesInventory.set(i, ItemStack.EMPTY);
				}
				else {
					if(player.inventory.addItemStackToInventory(rest) || tryToSpawnEntityItemAtPlayer(original, rest))
						soul.baublesInventory.set(i, ItemStack.EMPTY);
				}
			}
		}
		
		soul.clear();
	}


	private static boolean addStackToPlayerInventory(EntityPlayer player, int i, ItemStack item) {
		if (item == null || player == null)
			return false;
		if(player.inventory.getStackInSlot(i) == ItemStack.EMPTY) {
			player.inventory.setInventorySlotContents(i, item);
			return true;
		} else {
			if(player.inventory.getFirstEmptyStack() == -1)
				return false;
			else {
				player.inventory.setInventorySlotContents(player.inventory.getFirstEmptyStack(), item);
				return true;
			}
		}
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

	private static boolean isStackSoulBound(ItemStack item) {
		return NBTHelper.doesItemStackHaveTag(item, NBTTags.SOULBOUND);
	}
}
