package com.black_dog20.moregadgets.client.gui.teleporter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiItemIconButton extends GuiButton {

	private ItemStack icon;
	private List<String> tips = new ArrayList<String>();
	private double scale;
	private int myX;
	private int myY;
	
	public GuiItemIconButton(int buttonId, Item icon, int x, int y, double scale, String... info) {
		super(buttonId, x, y, 20, 20, "");
		this.icon = new ItemStack(icon);
		this.scale = scale;
		myX = x;
		myY = y;
		for(String s : info)
			tips.add(s);
	}
	
	@Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
        	GlStateManager.pushMatrix();
    		GlStateManager.scale(scale, scale, scale);
    		super.x = (int) Math.ceil((double)myX / scale);
    		super.y = (int) Math.ceil((double)myY / scale);
    		int mouseXC = (int) Math.ceil((double)mouseX / scale);
    		int mouseYC = (int) Math.ceil((double)mouseY / scale);
    		super.drawButton(mc,mouseXC, mouseYC, partialTicks);
    		GlStateManager.popMatrix();
    		
    		GlStateManager.pushMatrix();
    		double scale = 1;
    		GlStateManager.scale(scale, scale, scale);
    		int x = (int) Math.ceil((double)myX / scale);
    		int y = (int) Math.ceil((double)myY / scale);
    		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderItem renderitem = Minecraft.getMinecraft().getRenderItem();
            renderitem.renderItemIntoGUI(icon, x+1, y);
    		GlStateManager.popMatrix();
        }
    }
	
	public List<String> getHoverText(){
		return tips;
	}
	
	
}