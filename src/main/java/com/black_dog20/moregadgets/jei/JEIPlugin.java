package com.black_dog20.moregadgets.jei;

import com.black_dog20.moregadgets.init.ModItems;
import com.black_dog20.moregadgets.jei.recipehandlers.FireRecipeCategory;
import com.black_dog20.moregadgets.jei.recipehandlers.FireRecipeMaker;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin{

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(
				new FireRecipeCategory(guiHelper)
		);
	}

	@Override
	public void register(IModRegistry registry) {
		String[] soulboundInventories = new String[] { "item.moregadgets:soul_binder.info2", "soulbound.support.main", "soulbound.support.offhand", "soulbound.support.armor", "soulbound.support.baubles", "soulbound.support.galacticraft" };
		
		registry.addIngredientInfo(new ItemStack(ModItems.soulFragment), ItemStack.class, "item.moregadgets:soul_fragment.info","item.moregadgets:soul_fragment.info2");
		registry.addIngredientInfo(new ItemStack(ModItems.soulbinder), ItemStack.class, "tooltips.moregadgets:soul_binder.use", "item.moregadgets:soul_binder.info");
		registry.addIngredientInfo(new ItemStack(ModItems.soulbinder), ItemStack.class, soulboundInventories);
		registry.addIngredientInfo(new ItemStack(ModItems.soulbinder), ItemStack.class, "item.moregadgets:soul_binder.info3");
		
		IJeiHelpers jeiHelper = registry.getJeiHelpers();
		registry.addRecipes(FireRecipeMaker.getRecipes(jeiHelper), RecipeCategoryUid.FIRE);
	}
		

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		// TODO Auto-generated method stub
		
	}

}
