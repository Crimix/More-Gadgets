package com.black_dog20.moregadgets.item;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHealthFood extends ItemFoodBase {

	public static final UUID MORE_GADGETS_HEALTH = UUID.fromString("f799c85b-6c66-4710-96af-7133a286e2e5");
	private int maxHealthGained;
	private int minHealth;
	
	public ItemHealthFood(String name, int minHealth, int maxHealthGained, int amount, float saturation, boolean isWolfFood) {
		super(name, amount, saturation, isWolfFood);
		this.maxHealthGained = maxHealthGained;
		this.minHealth = minHealth;
		this.setAlwaysEdible();
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

}
