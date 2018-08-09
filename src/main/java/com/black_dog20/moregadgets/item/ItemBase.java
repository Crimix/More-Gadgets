package com.black_dog20.moregadgets.item;

import java.util.List;

import javax.annotation.Nullable;

import com.black_dog20.moregadgets.client.render.IItemModelRegister;
import com.black_dog20.moregadgets.creativetab.CreativeTab;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item implements IItemModelRegister{

	public ItemBase(String name) {
		super();
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
		this.setUnlocalizedName(this.getRegistryName().toString());
		this.setCreativeTab(CreativeTab.TAB);
	}
	
	
    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn, String text) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(text);
	}

}
