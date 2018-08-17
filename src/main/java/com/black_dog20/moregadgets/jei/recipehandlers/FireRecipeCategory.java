package com.black_dog20.moregadgets.jei.recipehandlers;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.moregadgets.jei.RecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
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

	protected final IDrawableStatic staticArrow;
	protected final IDrawableAnimated arrow;
	private final String localizedName;
	private final IDrawable background;

	public FireRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation gui = new ResourceLocation("gadgetron:textures/gui/machine.png");
		staticArrow = guiHelper.createDrawable(gui, 176, 14, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.LEFT, false);
		
		background = guiHelper.createDrawable(gui, 52, 27, 87, 31);
		localizedName = I18n.format("gadgetron.container.extractor");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}

	@Override
	public String getModName() {
		return I18n.format("itemGroup.more_gadgets");
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 27, 7);		
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FireRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 3, 7);
		guiItemStacks.init(outputSlot, false, 63, 7);

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