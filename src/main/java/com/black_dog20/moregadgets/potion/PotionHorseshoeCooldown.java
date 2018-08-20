package com.black_dog20.moregadgets.potion;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionHorseshoeCooldown extends Potion{

	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/misc/potions.png");
	
	public PotionHorseshoeCooldown() {
		super(false, 0xFFFFFF);
        this.setPotionName("luckyHorseshoeCooldown");
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "luckyHorseshoeCooldown"));
        this.setIconIndex(0, 0);
	}
	
	@Override
    public boolean shouldRenderInvText(PotionEffect effect) {
        return true;
    }

    public PotionEffect apply(EntityLivingBase entity, int duration) {
        return apply(entity, duration, 0);
    }

    public PotionEffect apply(EntityLivingBase entity, int duration, int level) {
        PotionEffect effect = new PotionEffect(this, duration, level, false, false);
        entity.addPotionEffect(effect);
        return effect;
    }

    public int getLevel(EntityLivingBase entity) {
        PotionEffect effect = entity.getActivePotionEffect(this);
        if (effect != null) {
            return effect.getAmplifier();
        }
        return 0;
    }

    @Override
    public List<ItemStack> getCurativeItems()
    {
    	List<ItemStack> ret = new ArrayList<ItemStack>();
        return ret;
    }
    
    @Override
    public boolean shouldRender(PotionEffect effect) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        return super.getStatusIconIndex();
    }

}
