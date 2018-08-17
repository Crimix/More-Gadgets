package com.black_dog20.moregadgets.item.gadgets;

import java.util.List;

import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.item.ItemBase;
import com.black_dog20.moregadgets.utility.InventoryHelper;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRebreather extends ItemBase{

	private RebreatherLevel material;
	
	public ItemRebreather(String name, RebreatherLevel material) {
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

	@SubscribeEvent
	public void onLivingUpdatePlayer(LivingUpdateEvent event) {
		if(event.getEntity() instanceof EntityPlayer && !event.getEntity().world.isRemote) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			
			if(hasRebreather(player) && player.isInsideOfMaterial(Material.WATER)) {
				NBTTagCompound nbt = player.getEntityData();
				int tick = nbt.getInteger("rebreatherTick");
				if(tick == 0)
					tick = 1;
				if(tick%material.getEfficiency() == 0) {
					nbt.setInteger("rebreatherAir", player.getAir());
					tick = 1;
					System.out.println("dmg");
					findTier(player).damageItem(1, player);
				}
				else {
					int air = 300;
					if(nbt.hasKey("rebreatherAir"))
						air = nbt.getInteger("rebreatherAir");
					player.setAir(air);
					tick++;
				}
				nbt.setInteger("rebreatherTick", tick);
			} else if(player.getEntityData().getInteger("rebreatherAir") != 300 && !player.isInsideOfMaterial(Material.WATER)) {
				player.getEntityData().setInteger("rebreatherAir", 300);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRender(EntityViewRenderEvent.FogDensity event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(hasRebreather(player) && player.isInsideOfMaterial(Material.WATER)) {
			event.setDensity(0F);
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (hasRebreather(player) && player.isInsideOfMaterial(Material.WATER)) {
				if(player.onGround)
					event.setNewSpeed(event.getOriginalSpeed()*5);
				else
					event.setNewSpeed(event.getOriginalSpeed()*25);
			}
		}
	}
	
	private boolean hasRebreather(EntityPlayer player) {
		boolean result = false;
		if(InventoryHelper.hasItem(player, ModItems.emeraldRebreather)) {
			if(material.getTier() == RebreatherLevel.EMERALD.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.diamondRebreather)) {
			if(material.getTier() == RebreatherLevel.DIAMOND.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.goldRebreather)) {
			if(material.getTier() == RebreatherLevel.GOLD.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.ironRebreather)) {
			if(material.getTier() == RebreatherLevel.IRON.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.stoneRebreather)) {
			if(material.getTier() == RebreatherLevel.STONE.getTier())
				result = true;
		}else if(InventoryHelper.hasItem(player, ModItems.woodenRebreather)) {
			if(material.getTier() == RebreatherLevel.WOOD.getTier())
				result = true;
		}

		return result;
	}
	
	private ItemStack findTier(EntityPlayer player) {
		if(material.getTier() == RebreatherLevel.EMERALD.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.emeraldRebreather);
		
		if(material.getTier() == RebreatherLevel.DIAMOND.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.diamondRebreather);
		
		if(material.getTier() == RebreatherLevel.GOLD.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.goldRebreather);
		
		if(material.getTier() == RebreatherLevel.IRON.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.ironRebreather);
		
		if(material.getTier() == RebreatherLevel.STONE.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.stoneRebreather);
		
		if(material.getTier() == RebreatherLevel.WOOD.getTier())
			return InventoryHelper.findItemStackOfType(player, ModItems.woodenRebreather);
		return ItemStack.EMPTY;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("tooltips.moregadgets:rebreather.efficiency", material.efficiency));
	}
	
	public static enum RebreatherLevel
	{
	    WOOD(1, 600, 2),
	    STONE(2, 800, 3),
	    IRON(3, 1400, 4),
	    GOLD(4, 1200, 4),
	    DIAMOND(5, 1800, 5),
	    EMERALD(6, 2000, 6);
	    
	    private final int tier;
	    private final int durability;
	    private final int efficiency;

	    private RebreatherLevel(int tier, int durability, int efficiency)
	    {
	        this.tier = tier;
	        this.durability = durability;
	        this.efficiency = efficiency;
	    }

	    public int getTier()
	    {
	        return this.tier;
	    }
	    
	    public int getDurability()
	    {
	        return this.durability;
	    }

	    public int getEfficiency()
	    {
	        return this.efficiency;
	    }
	}
	
}
