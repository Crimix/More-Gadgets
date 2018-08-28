package com.black_dog20.moregadgets;

import org.apache.logging.log4j.Logger;

import com.black_dog20.moregadgets.entity.EntityItemFire;
import com.black_dog20.moregadgets.handler.GuiHandler;
import com.black_dog20.moregadgets.init.Recipes;
import com.black_dog20.moregadgets.network.PacketHandler;
import com.black_dog20.moregadgets.proxies.IProxy;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MC_VERSIONS, dependencies = Reference.DEPENDENCIES)
public class MoreGadgets {

	@Mod.Instance(Reference.MOD_ID)
	public static MoreGadgets instance = new MoreGadgets();
	public static Logger logger;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	public static final int GUI_SHAPESHIFTER = 0;
	public static final int GUI_TELEPORT = 1;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		PacketHandler.init();
		proxy.registerPreInit();
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "FireImmunEntityItem"), EntityItemFire.class, "FireImmunEntityItem", 0, instance, 64, 10, true);

		
		logger.info("Pre Initialization Complete!");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		Recipes.init();

		logger.info("Initialization Complete!");
}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Recipes.postInit();
		logger.info("Post Initialization Complete!");
	}
}
