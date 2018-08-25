package com.black_dog20.moregadgets.handler;

import com.black_dog20.moregadgets.MoreGadgets;
import com.black_dog20.moregadgets.client.gui.GuiShapeShifterToolBag;
import com.black_dog20.moregadgets.container.ContainerShapeShifterToolBag;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case MoreGadgets.GUI_SHAPESHIFTER:
				return new ContainerShapeShifterToolBag(player.inventory, player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case MoreGadgets.GUI_SHAPESHIFTER:
				return new GuiShapeShifterToolBag(player);
		}
		return null;
	}
}