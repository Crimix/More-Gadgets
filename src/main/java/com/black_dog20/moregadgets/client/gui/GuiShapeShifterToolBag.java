package com.black_dog20.moregadgets.client.gui;

import com.black_dog20.moregadgets.container.ContainerShapeShifterToolBag;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiShapeShifterToolBag extends GuiContainer {
	private static final ResourceLocation gui = new ResourceLocation("minecraft:textures/gui/container/inventory.png");
	protected final int xSizeOfTexture = 192 , ySizeOfTexture = 135;
	
	public GuiShapeShifterToolBag(EntityPlayer player) {
		super(new ContainerShapeShifterToolBag(player.inventory,player));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(gui);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}	
}
