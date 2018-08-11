package com.black_dog20.moregadgets.proxies;

import com.black_dog20.moregadgets.client.gui.GuiHealth;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRendersPreInit() {
		MinecraftForge.EVENT_BUS.register(new GuiHealth());
	}

}
