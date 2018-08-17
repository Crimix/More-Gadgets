package com.black_dog20.moregadgets.utility;

import java.util.Random;
import java.util.stream.IntStream;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ChunkPos;

public class Helper {

	public static int getRandomBetween(Random random, int min, int max){
		return random.nextInt((max-min) +1) + min;
	}
	
	public static boolean intArrayContains(int[] array, int number){
		return IntStream.of(array).anyMatch(x -> x == number);
	}
	
	public static Random createFMLRandom(ChunkPos pos, long worldSeed){
		Random fmlRandom = new Random(worldSeed);
		long xSeed = (fmlRandom.nextLong()>>3);
		long zSeed = (fmlRandom.nextLong()>>3);
		fmlRandom.setSeed(xSeed * pos.x + zSeed * pos.z ^ worldSeed);
		return fmlRandom;
	}
	
	public static boolean doesItemHaveEnchantment(ItemStack stack, Enchantment enchantment) {
		return EnchantmentHelper.getEnchantments(stack).containsKey(enchantment);
	}
	
	public static boolean doesItemHaveEnchantment(ItemStack stack, Enchantment enchantment, int level) {
		return EnchantmentHelper.getEnchantments(stack).containsKey(enchantment) && EnchantmentHelper.getEnchantments(stack).get(enchantment) == level;
	}
	
}
