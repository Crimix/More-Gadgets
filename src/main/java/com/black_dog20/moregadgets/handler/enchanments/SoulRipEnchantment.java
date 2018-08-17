package com.black_dog20.moregadgets.handler.enchanments;

import org.lwjgl.input.Keyboard;

import com.black_dog20.moregadgets.MoreGadgets;
import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.reference.Reference;
import com.black_dog20.moregadgets.utility.Helper;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class SoulRipEnchantment extends Enchantment{

	public static final SoulRipEnchantment soulRipEnchantment = new SoulRipEnchantment();
	
	@SubscribeEvent
	public static void register(Register<Enchantment> event) {
		event.getRegistry().register(soulRipEnchantment);
	}

	protected SoulRipEnchantment() {
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
		this.setName("more_gadgets_soul_rip");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, this.getName()));
		
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if(event.getEntity() != null && event.getEntity().world.isRemote)
			return;
		
		if(event.getEntity() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if(player.getHeldItemMainhand() != ItemStack.EMPTY && Helper.doesItemHaveEnchantment(player.getHeldItemMainhand(), soulRipEnchantment)) {
				double chance = Math.random();
				if(chance < (MoreGadgets.proxy.getServerConfig().percentageDropSoulPiece / 100.0))
					event.getEntity().dropItem(ModItems.soulFragment, 1);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onToolTipEvent(ItemTooltipEvent event) {
		if(!Helper.doesItemHaveEnchantment(event.getItemStack(), soulRipEnchantment))
			return;
		int soulripLocation = event.getToolTip().indexOf(soulRipEnchantment.getTranslatedName(1));
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			event.getToolTip().add((soulripLocation+1),I18n.format("tooltips.moregadgets:items.soulrip", MoreGadgets.proxy.getServerConfig().percentageDropSoulPiece));
		else
			event.getToolTip().add(soulripLocation+1, I18n.format("tooltips.moregadgets:items.sneak", Keyboard.getKeyName(Keyboard.KEY_LSHIFT) ));
	}
}
