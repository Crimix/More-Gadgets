package com.black_dog20.moregadgets.jei.recipehandlers;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.moregadgets.jei.RecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class FireRecipeCategory implements IRecipeCategory<FireRecipeWrapper> {
	
	protected static final int inputSlot = 0;
	protected static final int outputSlot = 1;

	private final String localizedName;
	private final IDrawable background;
	private final IDrawable icon;

	public FireRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation gui = new ResourceLocation("moregadgets:textures/gui/fire_recipe.png");
		
		icon = guiHelper.createDrawable(gui, 176, 0, 15, 14);
		background = guiHelper.createDrawable(gui, 72, 17, 67, 64);
		localizedName = I18n.format("container.moregadgets.fire");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public String getModName() {
		return I18n.format("itemGroup.more_gadgets");
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FireRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 3, 2);
		guiItemStacks.init(outputSlot, false, 48, 46);

		guiItemStacks.set(ingredients);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return new ArrayList<String>();
	}

	@Override
	public String getUid() {
		return RecipeCategoryUid.FIRE;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}
}