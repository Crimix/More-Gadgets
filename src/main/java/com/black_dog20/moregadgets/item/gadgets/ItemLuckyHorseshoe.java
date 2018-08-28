package com.black_dog20.moregadgets.item.gadgets;

import java.util.List;

import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.init.ModPotions;
import com.black_dog20.moregadgets.item.ItemBase;
import com.black_dog20.moregadgets.utility.InventoryHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLuckyHorseshoe extends ItemBase{

	private HorseshoeLevel material;
	
	public ItemLuckyHorseshoe(String name, HorseshoeLevel material) {
		super(name);
		this.material = material;
		this.setMaxStackSize(1);
		this.setMaxDamage(material.getDurability());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onPlayerHurt(LivingHurtEvent event) {
		if(event.getPhase() != EventPriority.HIGHEST)
			return;
		
		if(event.getSource() == DamageSource.OUT_OF_WORLD)
			return;
		
		if(event.getEntity() != null && event.getEntity() instanceof EntityPlayer && !(event.getEntity() instanceof FakePlayer)) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if(player.getHealth() - event.getAmount() <= 0) {
				System.out.println(player.getActivePotionEffect(ModPotions.luckyHorseShoeCooldown));
				if(player.getActivePotionEffect(ModPotions.luckyHorseShoeCooldown) != null)
					return;
				
				if(hasRebreather(player)) {
					player.setHealth(material.health);
					findTier(player).damageItem(1, player);
					player.addPotionEffect(new PotionEffect(ModPotions.luckyHorseShoeCooldown, material.cooldownDuration));
					event.setCanceled(true);
				}
			}
				
		}
	}
	
	private boolean hasRebreather(EntityPlayer player) {
		boolean result = false;
		if(InventoryHelper.hasItem(player, ModItems.emeraldHorseshoe)) {
			if(material.getTier() == HorseshoeLevel.EMERALD.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.diamondHorseshoe)) {
			if(material.getTier() == HorseshoeLevel.DIAMOND.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.goldHorseshoe)) {
			if(material.getTier() == HorseshoeLevel.GOLD.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.ironHorseshoe)) {
			if(material.getTier() == HorseshoeLevel.IRON.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.stoneHorseshoe)) {
			if(material.getTier() == HorseshoeLevel.STONE.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.woodenHorseshoe)) {
			if(material.getTier() == HorseshoeLevel.WOOD.getTier())
				result = true;
		}

		return result;
	}
	
	private ItemStack findTier(EntityPlayer player) {
		if(material.getTier() == HorseshoeLevel.EMERALD.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.emeraldHorseshoe);
		
		if(material.getTier() == HorseshoeLevel.DIAMOND.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.diamondHorseshoe);
		
		if(material.getTier() == HorseshoeLevel.GOLD.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.goldHorseshoe);
		
		if(material.getTier() == HorseshoeLevel.IRON.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.ironHorseshoe);
		
		if(material.getTier() == HorseshoeLevel.STONE.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.stoneHorseshoe);
		
		if(material.getTier() == HorseshoeLevel.WOOD.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.woodenHorseshoe);
		return ItemStack.EMPTY;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("tooltips.moregadgets:horseshoe.tips"));
		tooltip.add(I18n.format("tooltips.moregadgets:horseshoe.health", material.health));
		tooltip.add(I18n.format("tooltips.moregadgets:horseshoe.cooldown", material.cooldownDuration/20));
	}
	
	public static enum HorseshoeLevel
	{
	    WOOD(1, 5, 1, 3600),
	    STONE(2, 10, 2, 3600),
	    IRON(3, 20, 4, 2400),
	    GOLD(4, 15, 6, 2400),
	    DIAMOND(5, 40, 8, 1200),
	    EMERALD(6, 50, 10, 600);
	    
	    private final int tier;
	    private final int durability;
	    private final int health;
	    private final int cooldownDuration;

	    private HorseshoeLevel(int tier, int durability, int health, int cooldownDuration)
	    {
	        this.tier = tier;
	        this.durability = durability;
	        this.health = health;
	        this.cooldownDuration = cooldownDuration;
	    }

	    public int getTier()
	    {
	        return this.tier;
	    }
	    
	    public int getDurability()
	    {
	        return this.durability;
	    }

	    public int getHealth()
	    {
	        return this.health;
	    }
	    
	    public int getCooldown()
	    {
	        return this.cooldownDuration;
	    }
	}
	
}
