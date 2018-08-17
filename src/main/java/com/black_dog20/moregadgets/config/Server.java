package com.black_dog20.moregadgets.config;

import net.minecraftforge.common.config.Config;

public class Server {
	
	@Config.LangKey("config.moregadgets.percentageDropSoulPiece")
	@Config.RangeInt(min = 0, max = 100)
	public int percentageDropSoulPiece = 5;

}
