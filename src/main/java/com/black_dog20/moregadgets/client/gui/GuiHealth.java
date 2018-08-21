package com.black_dog20.moregadgets.client.gui;

import java.util.Random;

import com.black_dog20.moregadgets.config.ModConfig;
import com.black_dog20.moregadgets.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiHealth extends Gui {


	protected static final ResourceLocation IRON_HEART = new ResourceLocation(Reference.MOD_ID, "textures/gui/iron_heart.png");
	protected static final ResourceLocation GOLD_HEART = new ResourceLocation(Reference.MOD_ID, "textures/gui/gold_heart.png");
	protected static final ResourceLocation DIAMOND_HEART = new ResourceLocation(Reference.MOD_ID, "textures/gui/diamond_heart.png");
	protected static final ResourceLocation EMERALD_HEART = new ResourceLocation(Reference.MOD_ID, "textures/gui/emerald_heart.png");

	protected final Random rand = new Random();
	protected final Minecraft mc;
	protected int updateCounter;
	protected int playerHealth;
	protected int lastPlayerHealth;
	protected long lastSystemTime;
	protected long healthUpdateCounter;


	public GuiHealth()
	{
		this.mc = Minecraft.getMinecraft();
	}


	public void render() {
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
		GlStateManager.pushMatrix();
		this.mc.getTextureManager().bindTexture(ICONS);

		if (this.mc.playerController.shouldDrawHUD())
		{
			this.renderPlayerStats(scaledresolution);
		}
		GlStateManager.popMatrix();
	}

	protected void renderPlayerStats(ScaledResolution scaledRes)
	{
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
			int i = MathHelper.ceil(entityplayer.getHealth());
			boolean flag = this.healthUpdateCounter > (long)this.updateCounter && (this.healthUpdateCounter - (long)this.updateCounter) / 3L % 2L == 1L;

			if (i < this.playerHealth && entityplayer.hurtResistantTime > 0)
			{
				this.lastSystemTime = Minecraft.getSystemTime();
				this.healthUpdateCounter = (long)(this.updateCounter + 20);
			}
			else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0)
			{
				this.lastSystemTime = Minecraft.getSystemTime();
				this.healthUpdateCounter = (long)(this.updateCounter + 10);
			}

			if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L)
			{
				this.playerHealth = i;
				this.lastPlayerHealth = i;
				this.lastSystemTime = Minecraft.getSystemTime();
			}

			this.playerHealth = i;
			int j = this.lastPlayerHealth;
			this.rand.setSeed((long)(this.updateCounter * 312871));
			IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
			int l = scaledRes.getScaledWidth() / 2 - 91;
			int j1 = scaledRes.getScaledHeight() - 39;
			float f = (float)iattributeinstance.getAttributeValue();
			int k1 = MathHelper.ceil(entityplayer.getAbsorptionAmount());
			int l1 = MathHelper.ceil((f + (float)k1) / 2.0F / 10.0F);
			int i2 = Math.max(10 - (l1 - 2), 3);
			int l2 = k1;
			int j3 = -1;
			int j2 = j1 - (l1 - 1) - 10;
            int i3 = entityplayer.getTotalArmorValue();

			if (entityplayer.isPotionActive(MobEffects.REGENERATION))
			{
				j3 = this.updateCounter % MathHelper.ceil(f + 5.0F);
			}
			
            for (int k3 = 0; k3 < 10; ++k3)
            {
                if (i3 > 0)
                {
                    int l3 = l + k3 * 8;

                    if (k3 * 2 + 1 < i3)
                    {
                        this.drawTexturedModalRect(l3, j2, 34, 9, 9, 9);
                    }

                    if (k3 * 2 + 1 == i3)
                    {
                        this.drawTexturedModalRect(l3, j2, 25, 9, 9, 9);
                    }

                    if (k3 * 2 + 1 > i3)
                    {
                        this.drawTexturedModalRect(l3, j2, 16, 9, 9, 9);
                    }
                }
            }

			for (int j5 = 0; j5 <= MathHelper.ceil((f + (float)k1) / 2.0F) - 1; ++j5)
			{
				int j4 = MathHelper.ceil((float)(j5 + 1) / 10.0F) - 1;
				int k5 = 16;

				if (entityplayer.isPotionActive(MobEffects.POISON) && j4 == 1)
				{
					k5 += 36;
				}
				else if (entityplayer.isPotionActive(MobEffects.WITHER) && j4 == 1)
				{
					k5 += 72;
				}

				int i4 = 0;

				if (flag)
				{
					i4 = 1;
				}

				int k4 = l + j5 % 10 * 8;
				int l4 = j1 - 0 * i2;

				if (i <= 4)
				{
					l4 += this.rand.nextInt(2);
				}

				if (l2 <= 0 && j5 == j3)
				{
					l4 -= 2;
				}

				int i5 = 0;

				if (entityplayer.world.getWorldInfo().isHardcoreModeEnabled())
				{
					i5 = 5;
				}
				if(j4 == 0) {
					this.drawTexturedModalRect(k4, l4, 16 + i4 * 9, 9 * i5, 9, 9);


					if (flag)
					{
						if (j5 * 2 + 1 < j)
						{
							this.drawTexturedModalRect(k4, l4, k5 + 54, 9 * i5, 9, 9);
						}

						if (j5 * 2 + 1 == j)
						{
							this.drawTexturedModalRect(k4, l4, k5 + 63, 9 * i5, 9, 9);
						}
					}

					if (l2 > 0)
					{
						if (l2 == k1 && k1 % 2 == 1)
						{
							this.drawTexturedModalRect(k4, l4, k5 + 153, 9 * i5, 9, 9);
							--l2;
						}
						else
						{
							this.drawTexturedModalRect(k4, l4, k5 + 144, 9 * i5, 9, 9);
							l2 -= 2;
						}
					}
					else
					{
						if (j5 * 2 + 1 < i)
						{
							this.drawTexturedModalRect(k4, l4, k5 + 36, 9 * i5, 9, 9);
						}

						if (j5 * 2 + 1 == i)
						{
							this.drawTexturedModalRect(k4, l4, k5 + 45, 9 * i5, 9, 9);                        
						}
					}
				}
				else {

					GlStateManager.pushMatrix();
					if(j4 == 1)
						this.mc.getTextureManager().bindTexture(IRON_HEART);
					else if(j4 == 2)
						this.mc.getTextureManager().bindTexture(GOLD_HEART);
					else if(j4 == 3)
						this.mc.getTextureManager().bindTexture(DIAMOND_HEART);
					else if(j4 == 4)
						this.mc.getTextureManager().bindTexture(EMERALD_HEART);
					if (flag)
					{
						if (j5 * 2 + 1 < j)
						{
							this.customDrawTexturedModalRect(k4, l4, 0, 0, 9, 9);
						}

						if (j5 * 2 + 1 == j)
						{
							this.customDrawTexturedModalRect(k4, l4, 9, 0, 9, 9);
						}
					}


					if (j5 * 2 + 1 < i)
					{
						this.customDrawTexturedModalRect(k4, l4, 0, 0, 9, 9);
					}

					if (j5 * 2 + 1 == i)
					{
						this.customDrawTexturedModalRect(k4, l4, 9, 0, 5, 9);                        
					}

					this.mc.getTextureManager().bindTexture(ICONS);	
					GlStateManager.popMatrix();
				}
			}
		}
	}

	public void customDrawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
	{
		float f = 1F / (float) 16;
		float f1 = 1F / (float) 16;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
		bufferbuilder.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
		bufferbuilder.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
		bufferbuilder.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
		tessellator.draw();
	}


	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onRender(RenderGameOverlayEvent.Pre event) {
		if(!ModConfig.client.useCustomHealthBar)
			return;
		if(event.getType() == ElementType.ARMOR)
			event.setCanceled(true);
			
		if(event.getType() == ElementType.HEALTH) {
			if(((EntityPlayer)this.mc.getRenderViewEntity()).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() <= 100) {
				if (!mc.gameSettings.hideGUI || mc.currentScreen != null)
				{
					render();
				}
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void RunTicks(TickEvent.ClientTickEvent event) {
		if (!mc.isGamePaused())
		{
			updateTick();
		}

	}



	public void updateTick()
	{
		++this.updateCounter;
	}




}
