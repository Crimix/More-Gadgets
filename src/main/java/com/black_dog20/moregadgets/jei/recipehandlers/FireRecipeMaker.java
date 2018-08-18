package com.black_dog20.moregadgets.jei.recipehandlers;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.moregadgets.init.recipehandlers.FireRecipes;
import com.black_dog20.moregadgets.init.recipehandlers.FireRecipes.FireRecipe;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public final class FireRecipeMaker {

	private FireRecipeMaker() {
	}

	public static List<FireRecipeWrapper> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
		List<FireRecipe> map = FireRecipes.recipes;

		List<FireRecipeWrapper> recipes = new ArrayList<>();

		for (FireRecipe entry : map) {
			ItemStack input = entry.getInput();
			ItemStack output = entry.getResult().copy();

			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			FireRecipeWrapper recipe = new FireRecipeWrapper(inputs, output);
			recipes.add(recipe);
		}

		return recipes;
	}

}