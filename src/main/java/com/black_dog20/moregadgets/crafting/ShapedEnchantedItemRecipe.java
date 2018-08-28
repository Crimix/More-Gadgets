package com.black_dog20.moregadgets.crafting;

import java.util.Collections;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedEnchantedItemRecipe extends ShapedOreRecipe {

	public ShapedEnchantedItemRecipe(@Nullable final ResourceLocation group, final ItemStack result, final CraftingHelper.ShapedPrimer primer) {
		super(group, result, primer);
	}

	@Override
	public String getGroup() {
		return group == null ? "" : group.toString();
	}

	public static class Factory implements IRecipeFactory {

		@Override
		public IRecipe parse(final JsonContext context, final JsonObject json) {
			final String group = JsonUtils.getString(json, "group", "");
			final CraftingHelper.ShapedPrimer primer = RecipeUtil.parseShaped(context, json);
			JsonObject resultJson = JsonUtils.getJsonObject(json, "result");
			final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

			String enchantName = JsonUtils.getString(resultJson, "enchantment");
	        int enchantLvl = JsonUtils.getInt(resultJson, "enchantment_lvl");

	        Enchantment enchantment = Enchantment.getEnchantmentByLocation(enchantName);
	        System.out.println(enchantment);
	        if (enchantment == null)
	            throw new JsonSyntaxException("Unknown enchantment '" + enchantName + "'");

	        Map<Enchantment, Integer> enchMap = Collections.singletonMap(enchantment, enchantLvl);
	        EnchantmentHelper.setEnchantments(enchMap, result);
			
			return new ShapedEnchantedItemRecipe(group.isEmpty() ? null : new ResourceLocation(group), result, primer);
		}
	}
}