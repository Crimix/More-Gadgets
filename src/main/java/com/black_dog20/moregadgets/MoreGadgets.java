package com.black_dog20.moregadgets;

import org.apache.logging.log4j.Logger;

import com.black_dog20.moregadgets.init.Recipes;
import com.black_dog20.moregadgets.proxies.IProxy;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MC_VERSIONS, dependencies = Reference.DEPENDENCIES)
public class MoreGadgets {

	@Mod.Instance(Reference.MOD_ID)
	public static MoreGadgets instance = new MoreGadgets();
	public static Logger logger;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy Proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		Proxy.registerPreInit();
		
		logger.info("Pre Initialization Complete!");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		Recipes.init();

		logger.info("Initialization Complete!");
}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		logger.info("Post Initialization Complete!");
	}
}
