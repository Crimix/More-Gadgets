package com.black_dog20.moregadgets.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class EnchantedIngredientFactory implements IIngredientFactory{

    @Nonnull
    @Override
    public Ingredient parse(JsonContext context, JsonObject json) {

        final ItemStack itemStack = CraftingHelper.getItemStack(json, context);
        String enchantName = JsonUtils.getString(json, "enchantment");
        int enchantLvl = JsonUtils.getInt(json, "enchantment_lvl");

        Enchantment enchantment = Enchantment.getEnchantmentByLocation(enchantName);
        if (enchantment == null)
            throw new JsonSyntaxException("Unknown enchantment '" + enchantName + "'");

        Map<Enchantment, Integer> enchMap = Collections.singletonMap(enchantment, enchantLvl);
        EnchantmentHelper.setEnchantments(enchMap, itemStack);

        return new IngredientEnchantedItem(itemStack, enchantment, enchantLvl);
    }
}