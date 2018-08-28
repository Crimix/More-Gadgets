package com.black_dog20.moregadgets.client.gui.teleporter;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GuiTeleportLocationList extends GuiScrollingList{

	public static List<GuiTeleportLocation> locations;
	private final GuiScreen parent;
	private float partialTicks;
	
	public GuiTeleportLocationList(List<GuiTeleportLocation> list, GuiScreen parent, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight) {
		super(Minecraft.getMinecraft(), width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
		locations = list;
		this.parent = parent;
	}

	@Override
	protected int getSize() {
		return locations.size();
	}
	
	@Override
	protected void drawGradientRect(int left, int top, int right, int bottom, int color1, int color2) {
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	@Override
	protected void drawBackground() {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseReleased(int mouseX, int mouseY, int state)
    {
		if(state == 0)
			for(GuiTeleportLocation l : locations) {
				if(l.getRemoveButton() != null)
					l.getRemoveButton().mouseReleased(mouseX, mouseY);
			}
    }
	
	public void handleButtonClick(int buttonId) {
		System.out.println(buttonId);
	}

	@Override
	protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
		parent.mc.fontRenderer.drawString(locations.get(slotIdx).getName(), left, slotTop, 0x000000);
		locations.get(slotIdx).getRemoveButton(entryRight, slotTop).drawButton(parent.mc, mouseX, mouseY, partialTicks);
	}

}
