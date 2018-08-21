package com.black_dog20.moregadgets.config;

import com.black_dog20.moregadgets.reference.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("config.moregadgets.title")
public class ModConfig {
	
	@Config.LangKey("config.moregadgets.client")
	public static final Client client = new Client();

	@Config.LangKey("config.moregadgets.server")
	public static final Server server = new Server();
	
	public static class Client{
		
		@Config.LangKey("config.moregadgets.useCustomHealthBar")
		public boolean useCustomHealthBar = false;
		
	}
	
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.MOD_ID)) {
				ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}

	

}
