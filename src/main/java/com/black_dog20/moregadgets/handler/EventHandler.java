package com.black_dog20.moregadgets.handler;

import java.util.Map;

import com.black_dog20.moregadgets.MoreGadgets;
import com.black_dog20.moregadgets.init.ModEnchantments;
import com.black_dog20.moregadgets.network.PacketHandler;
import com.black_dog20.moregadgets.network.message.MessageConfigSync;
import com.black_dog20.moregadgets.network.message.MessageOpenGuiOnServer;
import com.black_dog20.moregadgets.reference.Reference;
import com.black_dog20.moregadgets.utility.Helper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class EventHandler {
	
	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event){
		if(!event.player.world.isRemote){
			PacketHandler.network.sendTo(new MessageConfigSync(), (EntityPlayerMP) event.player);
		}
	}
	
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if(Helper.shouldItemHaveEnchantmentButDoesNot(event.getItemStack(), ModEnchantments.soulRipEnchantment, 1)) {
			ItemStack stack = event.getItemStack();
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
			if(map.size() == 1) {
				map.clear();
				map.put(ModEnchantments.soulRipEnchantment, 1);
				EnchantmentHelper.setEnchantments(map, stack);
			}
			else {
				map.put(ModEnchantments.soulRipEnchantment, 1);
				EnchantmentHelper.setEnchantments(map, stack);
			}
			stack.getTagCompound().removeTag("moregadgets_enchantment_recipe");
			stack.getTagCompound().removeTag("moregadgets_enchantment_level_recipe");
		}
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickItem event) {
		if(Helper.shouldItemHaveEnchantmentButDoesNot(event.getItemStack(), ModEnchantments.soulRipEnchantment, 1)) {
			ItemStack stack = event.getItemStack();
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
			if(map.size() == 1) {
				map.clear();
				map.put(ModEnchantments.soulRipEnchantment, 1);
				EnchantmentHelper.setEnchantments(map, stack);
			}
			else {
				map.put(ModEnchantments.soulRipEnchantment, 1);
				EnchantmentHelper.setEnchantments(map, stack);
			}
			stack.getTagCompound().removeTag("moregadgets_enchantment_recipe");
			stack.getTagCompound().removeTag("moregadgets_enchantment_level_recipe");
		}
	}

}