package com.black_dog20.moregadgets.intergration.galacticraft;

import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.common.Loader;

public class GalacticraftIntergration {

	public static IInventory getInventory(EntityPlayer player) {
		
		if(Loader.isModLoaded("galacticraftcore")) {
			return GCPlayerStats.get(player).getExtendedInventory();
		}
		
		return null;
	}
}
