package com.black_dog20.moregadgets.handler.enchanments;

import org.lwjgl.input.Keyboard;

import com.black_dog20.moregadgets.MoreGadgets;
import com.black_dog20.moregadgets.init.ModEnchantments;
import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.utility.Helper;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SoulRipEnchantment extends Enchantment{

	public SoulRipEnchantment() {
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
		//this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "soul_rip"));
		this.setName("moregadgets_soul_rip");
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		if(event.getEntity() != null && event.getEntity().world.isRemote)
			return;
		
		if(event.getEntity() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if(player.getHeldItemMainhand() != ItemStack.EMPTY && Helper.doesItemHaveEnchantment(player.getHeldItemMainhand(), ModEnchantments.soulRipEnchantment)) {
				double chance = Math.random();
				if(chance < (MoreGadgets.proxy.getServerConfig().percentageDropSoulPiece / 100.0))
					event.getEntity().dropItem(ModItems.soulFragment, 1);
			}
		}
	}
	
	@Override
	public boolean isAllowedOnBooks() {

		return true;
	}
	
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1 + enchantmentLevel * 1000;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 5000;
    }
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onToolTipEvent(ItemTooltipEvent event) {
		if(!Helper.doesItemHaveEnchantment(event.getItemStack(), ModEnchantments.soulRipEnchantment))
			return;
		int soulripLocation = event.getToolTip().indexOf(ModEnchantments.soulRipEnchantment.getTranslatedName(1));
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			event.getToolTip().add((soulripLocation+1),I18n.format("tooltips.moregadgets:items.soulrip", MoreGadgets.proxy.getServerConfig().percentageDropSoulPiece));
		else
			event.getToolTip().add(soulripLocation+1, I18n.format("tooltips.moregadgets:items.sneak", Keyboard.getKeyName(Keyboard.KEY_LSHIFT) ));
	}
}
