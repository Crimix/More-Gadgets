package com.black_dog20.moregadgets.client.gui.teleporter;

import com.black_dog20.moregadgets.item.gadgets.Location;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiTeleportLocation {

	private Location location;
	private GuiButton setHomeButton;
	private GuiButton remove;
	
	public GuiTeleportLocation(Location location) {
		this.location = location;
	}
	
	public String getName() {
		return location.getName();
	}
	
	public void clicked() {
		location.teleport();
	}
	
	public GuiButton getRemoveButton() {
		return remove;
	}
	
	public GuiButton getRemoveButton(int x, int y) {
		
		if(remove == null) {
			remove = new GuiButton(0, x-20, y-4, 20, 20, "R") {
			@Override
			public void mouseReleased(int mouseX, int mouseY) {
				if(mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)) {

				}
			}};
		} else {
			remove.x = x-20;
			remove.y = y-4;
		}
		
		return remove;
	}

}
