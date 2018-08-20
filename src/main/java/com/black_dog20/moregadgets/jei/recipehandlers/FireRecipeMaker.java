package com.black_dog20.moregadgets.jei.recipehandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.black_dog20.moregadgets.init.recipehandlers.FireRecipes;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public final class FireRecipeMaker {

	private FireRecipeMaker() {
	}

	public static List<FireRecipeWrapper> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
		HashMap<ItemStack, ItemStack> map = FireRecipes.recipes;

		List<FireRecipeWrapper> recipes = new ArrayList<>();

		for (Entry<ItemStack, ItemStack> entry : map.entrySet()) {
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();

			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			FireRecipeWrapper recipe = new FireRecipeWrapper(inputs, output);
			recipes.add(recipe);
		}

		return recipes;
	}

}