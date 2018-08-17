package com.black_dog20.moregadgets.network;

import com.black_dog20.moregadgets.network.message.MessageConfigSync;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


public class PacketHandler {

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

	public static void init() {
		network.registerMessage(MessageConfigSync.class, MessageConfigSync.class, 0, Side.CLIENT);
	}

}
