package com.sudoplay.mc.pwcustom.integration.workbench;

import com.sudoplay.mc.pwcustom.init.ModBlocks;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.workbench.gui.ContainerWorkbenchBasic;
import com.sudoplay.mc.pwcustom.workbench.tile.TileEntityWorkbenchBase;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class JEIRecipeTransferInfoWorkbenchBasic
    implements IRecipeTransferInfo<ContainerWorkbenchBasic> {

  private BlockWorkbench.EnumType type;
  private String uid;

  public JEIRecipeTransferInfoWorkbenchBasic(BlockWorkbench.EnumType type, String uid) {

    this.type = type;
    this.uid = uid;
  }

  @Override
  public Class<ContainerWorkbenchBasic> getContainerClass() {

    return ContainerWorkbenchBasic.class;
  }

  @Override
  public String getRecipeCategoryUid() {

    return this.uid;
  }

  @Override
  public boolean canHandle(ContainerWorkbenchBasic container) {

    TileEntityWorkbenchBase tile = container.getTile();
    IBlockState blockState = tile.getWorld().getBlockState(tile.getPos());

    return (blockState.getBlock() == ModBlocks.WORKBENCH_BASIC
        && blockState.getValue(BlockWorkbench.VARIANT) == this.type);
  }

  @Override
  public List<Slot> getRecipeSlots(ContainerWorkbenchBasic container) {

    return container.getRecipeSlots(new ArrayList<>());
  }

  @Override
  public List<Slot> getInventorySlots(ContainerWorkbenchBasic container) {

    return container.getInventorySlots(new ArrayList<>());
  }
}
