package com.black_dog20.moregadgets.init;

import com.black_dog20.moregadgets.handler.enchanments.SoulRipEnchantment;
import com.black_dog20.moregadgets.init.recipehandlers.FireRecipes;
import com.black_dog20.moregadgets.init.recipehandlers.SoulbindingRecipe;
import com.black_dog20.moregadgets.reference.Reference;
import com.black_dog20.moregadgets.utility.RecipeToJSON;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

@SuppressWarnings("deprecation")
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class Recipes {

	public static final SoulbindingRecipe soulRecipe = new SoulbindingRecipe();
	
	public static void init() {
		RecipeSorter.register("moreGadgetsSoulbinding", soulRecipe.getClass(), Category.SHAPELESS, "");
		ForgeRegistries.RECIPES.register(soulRecipe);
		FireRecipes.init();
		generateJSON();
	}
	
	
	
	private static void generateJSON() {
		Boolean devEnv = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
		if(!devEnv) return;
		
		RecipeToJSON toJSON = new RecipeToJSON(Reference.MOD_ID, true);
		
		toJSON.addShapedRecipe(ModItems.unfiredSoulBook, new Object[]{"ls","bg", 'b', Items.BOOK, 'l', ModItems.blueDust, 's', Items.NETHER_STAR, 'g', Items.GHAST_TEAR});
	}

}
