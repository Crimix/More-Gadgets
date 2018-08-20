package com.black_dog20.moregadgets.entity;

import javax.annotation.Nonnull;

import com.black_dog20.moregadgets.init.recipehandlers.FireRecipes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemFire extends EntityItem {
    public boolean changed;
    public int changeTicks;

	public EntityItemFire(World world) {
		super(world);
		isImmuneToFire = true;
	}

	public EntityItemFire(EntityItem toConvert) {
		this(toConvert.getEntityWorld());
		NBTTagCompound copyTag = new NBTTagCompound();
		readFromNBT(toConvert.writeToNBT(copyTag));
	}

	public boolean isEntityInvulnerable(@Nonnull DamageSource source) {
		if (source == DamageSource.IN_FIRE) {
			if (!changed) {
				ItemStack newStack = ItemStack.EMPTY;
				if (FireRecipes.isRecipeInput(this)) {
					newStack = FireRecipes.getResult(this);
				}
                setItem(newStack);
				changed = true;
			}
			return true;
		} else {
			return super.isEntityInvulnerable(source);
		}
	}

	public boolean isBurning() {
		return false;
	}
}


