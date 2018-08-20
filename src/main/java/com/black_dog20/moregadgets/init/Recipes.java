package com.black_dog20.moregadgets.init;

import com.black_dog20.moregadgets.init.recipehandlers.FireRecipes;
import com.black_dog20.moregadgets.init.recipehandlers.SoulbindingRecipe;
import com.black_dog20.moregadgets.reference.Reference;
import com.black_dog20.moregadgets.utility.RecipeToJSON;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
		generateJSON();
	}
	
	public static void postInit() {
		FireRecipes.init();
	}
	
	
	private static void generateJSON() {
		Boolean devEnv = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
		if(!devEnv) return;
		
		RecipeToJSON toJSON = new RecipeToJSON(Reference.MOD_ID, true);
		
		toJSON.addShapedRecipe(ModItems.unfiredSoulBook, new Object[]{"ls","bg", 'b', Items.BOOK, 'l', ModItems.blueDust, 's', Items.NETHER_STAR, 'g', Items.GHAST_TEAR});
		toJSON.addShapedRecipe(ModItems.witherBread, new Object[]{"lsl","gbg", "ggg", 'b', Items.BREAD, 'l', ModItems.soulFragment, 's', Items.NETHER_STAR, 'g', Blocks.SOUL_SAND});
		toJSON.addShapedRecipe(ModItems.enderBread, new Object[]{"lsl","gbg", "ggg", 'b', Items.BREAD, 'l', ModItems.soulFragment, 's', Items.DRAGON_BREATH, 'g', Items.ENDER_EYE});
		toJSON.addShapedRecipe(ModItems.airFilter, new Object[]{"lsl","scs", "lsl", 'c', Blocks.REEDS, 'l', ModItems.blueDust, 's', Items.STRING});
		toJSON.addShapelessRecipe(ModItems.soulbinder, ModItems.soulFragment, Items.ENDER_EYE, Items.NETHER_STAR, Items.GHAST_TEAR);
		
		toJSON.addShapedRecipe(ModItems.woodenRebreather, new Object[]{" w ","waw", "www", 'w', "logWood", 'a', ModItems.airFilter});
		toJSON.addShapedRecipe(ModItems.stoneRebreather, new Object[]{" w ","waw", "www", 'w', "stone", 'a', ModItems.airFilter});
		toJSON.addShapedRecipe(ModItems.ironRebreather, new Object[]{" w ","waw", "www", 'w', "ingotIron", 'a', ModItems.airFilter});
		toJSON.addShapedRecipe(ModItems.goldRebreather, new Object[]{" w ","waw", "www", 'w', "ingotGold", 'a', ModItems.airFilter});
		toJSON.addShapedRecipe(ModItems.diamondRebreather, new Object[]{" w ","waw", "www", 'w', "gemDiamond", 'a', ModItems.airFilter});
		toJSON.addShapedRecipe(ModItems.emeraldRebreather, new Object[]{" w ","waw", "www", 'w', "gemEmerald", 'a', ModItems.airFilter});
		
		toJSON.addShapedRecipe(ModItems.woodenHorseshoe, new Object[]{"b b","w w", "www", 'w', "logWood", 'b', ModItems.blueDust});
		toJSON.addShapedRecipe(ModItems.stoneHorseshoe, new Object[]{"b b","w w", "www", 'w', "stone", 'b', ModItems.blueDust});
		toJSON.addShapedRecipe(ModItems.ironHorseshoe, new Object[]{"b b","w w", "www", 'w', "ingotIron", 'b', ModItems.blueDust});
		toJSON.addShapedRecipe(ModItems.goldHorseshoe, new Object[]{"b b","w w", "www", 'w', "ingotGold", 'b', ModItems.blueDust});
		toJSON.addShapedRecipe(ModItems.diamondHorseshoe, new Object[]{"b b","w w", "www", 'w', "gemDiamond", 'b', ModItems.blueDust});
		toJSON.addShapedRecipe(ModItems.emeraldHorseshoe, new Object[]{"b b","w w", "www", 'w', "gemEmerald", 'b', ModItems.blueDust});
	}

}
