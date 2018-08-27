package com.black_dog20.moregadgets.client.gui;

import org.lwjgl.opengl.GL11;

import com.black_dog20.moregadgets.container.ContainerShapeShifterToolBag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiShapeShifterToolBag extends GuiContainer {
	private static final ResourceLocation gui = new ResourceLocation("moregadgets:textures/gui/shape_shifter_tool_gui.png");
	protected final int xSizeOfTexture = 192 , ySizeOfTexture = 135;
	
	public GuiShapeShifterToolBag(EntityPlayer player) {
		super(new ContainerShapeShifterToolBag(player.inventory, player));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		

	}
	
	private void renderSlotFiledOverlay(int slot, int xPos, int yPos) {
		if(inventorySlots instanceof ContainerShapeShifterToolBag) {
			ContainerShapeShifterToolBag toolBag = (ContainerShapeShifterToolBag) inventorySlots;
			if(!toolBag.toolBagSlots[slot].getHasStack()) {
				return;	
			}
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.mc.getTextureManager().bindTexture(gui);
        this.drawTexturedModalRect(xPos, yPos, 176, 0, 16, 16);	
        GlStateManager.popMatrix();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(gui);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		this.renderSlotFiledOverlay(0, k+52, l+30);
		this.renderSlotFiledOverlay(1, k+70, l+30);
		this.renderSlotFiledOverlay(2, k+88, l+30);
		this.renderSlotFiledOverlay(3, k+106, l+30);		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(gui);
		EntityPlayer player = Minecraft.getMinecraft().player;
		float oldZ = this.zLevel;
		this.zLevel = 500F;
		this.drawTexturedModalRect(8 + player.inventory.currentItem * 18, 142, 192, 0, 16, 16);
		this.zLevel = oldZ;
		String s = I18n.format("item.moregadgets:shape_shifting_tool_bag.name");
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	}
}
