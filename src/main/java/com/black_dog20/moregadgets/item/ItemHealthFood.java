package com.black_dog20.moregadgets.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemHealthFood extends ItemFoodBase {

	public static final UUID MORE_GADGETS_HEALTH = UUID.fromString("f799c85b-6c66-4710-96af-7133a286e2e5");
	private int maxHealthGained;
	private int minHealth;
	
	public ItemHealthFood(String name, int minHealth, int maxHealthGained, int amount, float saturation, boolean isWolfFood) {
		super(name, amount, saturation, isWolfFood);
		this.maxHealthGained = maxHealthGained;
		this.minHealth = minHealth;
		this.setAlwaysEdible();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
        IAttributeInstance health = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier healthModifier = health.getModifier(MORE_GADGETS_HEALTH);
        if(healthModifier == null)
        	healthModifier = new AttributeModifier(MORE_GADGETS_HEALTH,"MoreGadgetsHealth", 1, 0);
        else
        	healthModifier =  new AttributeModifier(MORE_GADGETS_HEALTH,"MoreGadgetsHealth", healthModifier.getAmount()+1, 0); 
        if(minHealth <= healthModifier.getAmount() && healthModifier.getAmount() <= minHealth + maxHealthGained) {
        	health.removeModifier(MORE_GADGETS_HEALTH);
        	health.applyModifier(healthModifier);
        }
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(Minecraft.getMinecraft().player != null) {
        IAttributeInstance health = Minecraft.getMinecraft().player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier healthModifier = health.getModifier(MORE_GADGETS_HEALTH);
        
        tooltip.add(I18n.format("tooltips.moregadgets:healthfood.heal", this.getHealAmount(stack)));
        
        if(healthModifier == null)
        	healthModifier = new AttributeModifier(MORE_GADGETS_HEALTH,"MoreGadgetsHealth", 1, 0);
        if(minHealth <= healthModifier.getAmount() && healthModifier.getAmount() < minHealth + maxHealthGained)
        	tooltip.add(I18n.format("tooltips.moregadgets:healthfood.can_gain", 1, maxHealthGained));
        else if (healthModifier.getAmount() >= minHealth + maxHealthGained)
        	tooltip.add(I18n.format("tooltips.moregadgets:healthfood.cannot_gain"));
        else
        	tooltip.add(I18n.format("tooltips.moregadgets:healthfood.not_ready"));
		}
	}
	
	@SubscribeEvent
	public void onPlayerCloneEvent(PlayerEvent.Clone event) {
		if (!event.isWasDeath()) {
			return;
		}
		IAttributeInstance oldHealth = event.getOriginal().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		IAttributeInstance health = event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

		AttributeModifier oldHealthModifier = oldHealth.getModifier(ItemHealthFood.MORE_GADGETS_HEALTH);
		if(oldHealthModifier != null) {
			health.removeModifier(ItemHealthFood.MORE_GADGETS_HEALTH);
			health.applyModifier(oldHealthModifier);
			event.getEntityPlayer().setHealth(event.getEntityPlayer().getHealth() + (float)oldHealthModifier.getAmount());
		}
	}

}
