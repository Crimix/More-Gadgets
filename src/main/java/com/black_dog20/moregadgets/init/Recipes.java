package com.black_dog20.moregadgets.init;

import com.black_dog20.moregadgets.init.recipehandlers.SoulbindingRecipe;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class Recipes {

	public static final SoulbindingRecipe soulRecipe = new SoulbindingRecipe();
	
	public static void init() {
		RecipeSorter.register("moreGadgetsSoulbinding", soulRecipe.getClass(), Category.SHAPELESS, "");
		ForgeRegistries.RECIPES.register(soulRecipe);
	}

}
