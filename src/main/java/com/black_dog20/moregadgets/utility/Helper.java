package com.black_dog20.moregadgets.utility;

import java.util.Random;
import java.util.stream.IntStream;

import net.minecraft.util.math.ChunkPos;

public class Helper {

	public static int GetRandonBetween(Random random, int min, int max){
		return random.nextInt((max-min) +1) + min;
	}
	
	public static boolean IntArrayContains(int[] array, int number){
		return IntStream.of(array).anyMatch(x -> x == number);
	}
	
	public static Random CreateFMLRandom(ChunkPos pos, long worldSeed){
		Random fmlRandom = new Random(worldSeed);
		long xSeed = (fmlRandom.nextLong()>>3);
		long zSeed = (fmlRandom.nextLong()>>3);
		fmlRandom.setSeed(xSeed * pos.x + zSeed * pos.z ^ worldSeed);
		return fmlRandom;
	}
}
