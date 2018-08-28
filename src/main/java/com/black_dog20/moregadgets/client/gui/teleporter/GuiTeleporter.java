package com.black_dog20.moregadgets.client.gui.teleporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.black_dog20.moregadgets.item.gadgets.Location;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTeleporter extends GuiScreen {

	private static final ResourceLocation gui = new ResourceLocation("minecraft:textures/gui/book.png");
	protected final int xSizeOfTexture = 192 , ySizeOfTexture = 192;
	private final EntityPlayer player;
	private GuiTeleportLocationList list;
	
	public GuiTeleporter(EntityPlayer player) {
		this.player = player;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int k = (this.width - xSizeOfTexture) / 2;
		int l = (this.height - ySizeOfTexture) / 2;
		List<GuiTeleportLocation> temp = new ArrayList<>();
		for(int i = 0; i < 20; i++) {
			Location t = new Location("teleport to " + i, 0, 0, 0, 0);
			temp.add(new GuiTeleportLocation(t));
		}
		
		list = new GuiTeleportLocationList(temp, this, 117, l, l+16, l+164, k+35, 25, width, height);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		list.mouseReleased(mouseX, mouseY, state);
	}
	
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		list.handleButtonClick(button.id);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.mc.getTextureManager().bindTexture(gui);
		int k = (this.width - xSizeOfTexture) / 2;
		int l = (this.height - ySizeOfTexture) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSizeOfTexture, ySizeOfTexture);
		list.setPartialTicks(partialTicks);
		list.drawScreen(mouseX, mouseY, partialTicks);
	}
}
