package com.black_dog20.moregadgets.container;
import com.black_dog20.moregadgets.item.gadgets.ItemShapeShiftingToolBag;
import com.black_dog20.moregadgets.storage.ShapeShiftingToolBagItemHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerShapeShifterToolBag extends Container{


	public ContainerShapeShifterToolBag(InventoryPlayer playerInventory, EntityPlayer player){
			for(int i = 0; i < 3; ++i){
				for(int j = 0; j < 9; ++j){
					this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
				}
			}

			for(int i = 0; i < 9; ++i){
				this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
			}
			
			if(ItemShapeShiftingToolBag.isTool(player.getHeldItemMainhand())){
				ShapeShiftingToolBagItemHandler tool = new ShapeShiftingToolBagItemHandler(player.getHeldItemMainhand());
				for(int i = 0; i < tool.getSlots(); ++i){
					this.addSlotToContainer(new SlotItemHandler(tool, i, 8, 8 + i * 18));
			}
		}
	}

	

	@Override
	public boolean canInteractWith(EntityPlayer playerIn){
		return true;
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            
            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

}

